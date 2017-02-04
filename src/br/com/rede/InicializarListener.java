package br.com.rede;
import java.util.List;
import java.util.ArrayList;

public class InicializarListener {
	
	private List<Recebedor> listener = new ArrayList<Recebedor>(); 
	
	public void adicionarlistener(Recebedor addTo) {
		this.listener.add(addTo);
	}
	
	public void receiveMessage(String message,String ip, Recebedor addTo) {
		this.adicionarlistener(addTo);
		for(Recebedor recebedor : listener) {
			recebedor.receiveMessage(message,ip);
		}
	}	
}
