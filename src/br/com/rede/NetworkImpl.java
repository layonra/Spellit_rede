package br.com.rede;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe que implementa a interface Network Reponsavel por todas as operacoes
 * essenciais de rede.
 * 
 * @author layon
 *
 */
public class NetworkImpl implements Network {
	private InicializarListener listener = new InicializarListener();

	@Override
	public InetAddress getAddress() throws SocketException {
		InetAddress inetAddress = null;
		Enumeration<InetAddress> enumeration = this.getInterface().getInetAddresses();
		while (enumeration.hasMoreElements()) {
			inetAddress = enumeration.nextElement();
			if (inetAddress.getHostAddress().length() < 15) {
				break;
			}
		}
		return inetAddress;
	}

	public InetAddress getBroadcastAddress() throws SocketException {
		InetAddress inetAddress = null;
		for (InterfaceAddress address : this.getInterface().getInterfaceAddresses()) {
			if (address.getBroadcast() == null) {
				continue;
			} else {
				inetAddress = address.getBroadcast();
			}
			break;
		}
		return inetAddress;
	}

	@Override
	public InetAddress getAddress(DatagramPacket p) {
		InetAddress inetAddress = p.getAddress();
		return inetAddress;
	}

	@Override
	public NetworkInterface getInterface() throws SocketException {
		NetworkInterface interface1 = null;
		Enumeration<NetworkInterface> interfacesEnumeration = NetworkInterface.getNetworkInterfaces();
		while (interfacesEnumeration.hasMoreElements()) {
			interface1 = interfacesEnumeration.nextElement();
			if (interface1.isUp() && (!interface1.isLoopback()) && (!interface1.isVirtual())) {
				break;
			}
		}
		return interface1;
	}

	@Override
	public void sendIPPacket(String texto, String ip, int port) throws IOException {
		Socket socket = new Socket(ip, port);
		ObjectOutput out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(texto);
		socket.close();
	}

	@Override
	public void serverSocketReceive(int port, Recebedor addTo) throws IOException, ClassNotFoundException {
		String receive = "";
		Socket socket = new Socket();
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			socket = serverSocket.accept();
			ObjectInput input = new ObjectInputStream(socket.getInputStream());
			receive = (String) input.readObject();
			this.listener.receiveMessage(receive, socket.getInetAddress().getHostAddress(), addTo);
		}
	}

	@Override
	public void sendDatagramPacket(byte[] data, int length, InetAddress address, int port) throws IOException {
		DatagramPacket datagramPacket = new DatagramPacket(data, length, address, port);
		DatagramSocket datagramSocket = new DatagramSocket();
		datagramSocket.send(datagramPacket);
		datagramSocket.close();
	}

	@Override
	public DatagramPacket receiveDatagramPacket(int port) throws IOException {
		byte[] data = new byte[1500];
		DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
		DatagramSocket datagramSocket = new DatagramSocket(port);
		datagramSocket.receive(datagramPacket);
		datagramSocket.close();
		return datagramPacket;
	}

	@Override
	public TimerTask sendDatagramPacketTask(byte[] data, int length, int delay, int period, InetAddress address,
			int port) {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				try {
					sendDatagramPacket(data, length, address, port);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, delay, period);
		return timerTask;
	}

	@Override
	public void aguardarServidor(int clientPort, AguardarServidor addTo) throws IOException {
		DatagramPacket p = null;
		String ip = "";
		p = this.receiveDatagramPacket(clientPort);
		boolean respostaDoServidor = new String(p.getData(), 0, p.getLength()).equals("OK");
		if (respostaDoServidor) {
			ip = p.getAddress().getHostAddress();
			this.listener.receiveOk(ip, addTo);
		}
	}

	@Override
	public void serverSocketReceive(int port) throws IOException, ClassNotFoundException {

	}
}
