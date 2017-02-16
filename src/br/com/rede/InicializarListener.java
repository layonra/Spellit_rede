package br.com.rede;

import java.util.List;
import java.util.ArrayList;

public class InicializarListener {

	private List<Recebedor> listener;
	private List<AguardarServidor> listener2;
	private List<DatagramPacketReceiver> listener3;

	public InicializarListener() {
		this.listener = new ArrayList<Recebedor>();
		this.listener2 = new ArrayList<AguardarServidor>();
		this.listener3 = new ArrayList<DatagramPacketReceiver>();
	}

	public void adicionarListener(Recebedor addTo) {
		this.listener.add(addTo);
	}

	public void adicionarListener(AguardarServidor addTo) {
		this.listener2.add(addTo);
	}

	public void adicionarListener(DatagramPacketReceiver addTo) {
		this.listener3.add(addTo);
	}

	public void receiveMessage(String message, String ip, Recebedor addTo) {
		this.adicionarListener(addTo);
		for (Recebedor recebedor : listener) {
			recebedor.receiveMessage(message, ip);
		}
	}

	public void receiveOk(String ip, AguardarServidor addTo) {
		this.adicionarListener(addTo);
		for (AguardarServidor aguardarServidor : listener2)
			aguardarServidor.receberOk(ip);
	}

	public void receiveDatagramPacket(Object object, DatagramPacketReceiver addTo) {
		this.adicionarListener(addTo);
		for (DatagramPacketReceiver receiver : listener3)
			receiver.datagramReceiver(object);
	}
}
