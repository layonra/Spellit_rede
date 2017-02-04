package br.com.rede;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.TimerTask;

/**
 * Interface com os metodos necesários para realizar as tarefas relacionadas a
 * rede.
 * 
 * @author layon
 */
public interface Network {
	/**
	 * 
	 * @return InetAddress
	 */
	public InetAddress getAddress() throws SocketException;

	/**
	 * Este metódo retorna o endereço de broadcast
	 * 
	 * @return
	 * @throws SocketException
	 */
	public InetAddress getBroadcastAddress() throws SocketException;

	/**
	 * 
	 * @param interfaces
	 * @return
	 */
	public InetAddress getAddress(DatagramPacket p);

	/**
	 * 
	 * @return
	 */
	public NetworkInterface getInterface() throws SocketException;

	/**
	 * 
	 * @param texto
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	public void sendIPPacket(String texto,String ip, int port) throws IOException;
	
	/**
	 * Abre um servidor socket para receber dados. ServerSocket nunca fecha
	 * @param port
	 * @return
	 * @throws IOException
	 */
	public void serverSocketReceive(int port, Recebedor addTo) throws IOException, ClassNotFoundException;
	
	/**
	 * Abre um servidor socket para receber dados. ServerSocket nunca fecha
	 * @param port
	 * @return
	 * @throws IOException
	 */
	public void serverSocketReceive(int port) throws IOException, ClassNotFoundException;
	
	/**
	 * 
	 * @param data
	 * @param length
	 * @param address
	 * @param port
	 */
	public void sendDatagramPacket(byte[] data, int length, InetAddress address, int port) throws IOException;

	/**
	 * 
	 * @param data
	 * @param length
	 * @param socket
	 * @param port
	 * @return
	 */
	public DatagramPacket receiveDatagramPacket(int port) throws IOException;

	/**
	 * 
	 * @param data
	 * @param length
	 * @param delay
	 * @param period
	 * @param address
	 * @param port
	 * @return
	 */
	public TimerTask sendDatagramPacketTask(byte[] data, int length, int delay, int period, InetAddress address,
			int port);
	
	/**
	 * 
	 * @param data
	 * @param serverport
	 * @param clientPort
	 * @return
	 * @throws IOException
	 */
	public String aguardarServidor(int clientPort) throws IOException;

}
