package br.fatecsjc;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains tasks triggered by form events
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import br.fatecsjc.model.Messages;

public class ChatListeningTasks {

	private XMPPConnection xmppConnection;
	private Mail mail;
	private volatile Double score;
	private String msg, log;
	private volatile boolean alarme = false;
	private volatile boolean alarmeScoreTexto = false;
	private volatile boolean alarmeScoreEstilo = false;
	private boolean parar = false;
	private boolean pararPontuacaoTexto = false;
	private boolean pararPontuacaoEstilo = false;
	private int i;

	public void connect(String servidor, int porta, String s) throws Exception {
		xmppConnection = new XMPPConnection(new ConnectionConfiguration(servidor, porta, s));
		xmppConnection.connect();
	}

	public void disconnect() {
		if (xmppConnection != null) {
			xmppConnection.disconnect();
		}
	}

	public void login(String username, String password) throws Exception {
		try {
			this.connect("chat.facebook.com", 5222, "ChatListen");
			xmppConnection.login(username, password);
		} catch (Exception e) {
		}
	}

	public Task<Void> taskInicia = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
			PacketCollector collector = xmppConnection.createPacketCollector(filter);
			log = "";
			updateMessage("Listening...");
			while (!isCancelled()) {
					Packet packet = collector.nextResult();
				if (packet instanceof Message) {
					Message message = (Message) packet;
					String msg = message.getBody();
					if (msg != null) {
						i = msg.toLowerCase().indexOf("fatecsjc");
						if (i != -1) {
							System.out.println("PALAVRA IMPRÓPRIA DETECTADA!!! "+ 
											   "CHAT ARQUIVADO PARA ANÃLISE. " + 
											   "E-MAIL ENVIADO AO ADMINISTRADOR");
							Crud crud = new Crud();
							crud.insertMessages(msg);
							log = log + msg + "\n";
							updateMessage(log);
							alarme = true;
							cancelled();
							crud.emfMessages.close();
							break;
						}
						Crud crud = new Crud();
						crud.insertMessages(msg);
						System.out.println(msg);
						log = log + msg + "\n";
						updateMessage(log);
						crud.emfMessages.close();
					}
				}
			}
			Thread.currentThread().interrupt();
			return null;
		}
	};

	public Task<Void> taskScore = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			DecimalFormat df = new DecimalFormat("0.000");
			while (!isCancelled()) {
				Facade facade = new Facade();Crud crud = new Crud();int tamanho = 1;
				List<Messages> listMsg = crud.daoMessages.findMessagesEntities();
				for(int i = 0; i < listMsg.size(); i++){
					String line = listMsg.get(i).getLinha();
					String[] words = line.split(" "); tamanho += words.length;
				}
				if(tamanho < 30){
					updateMessage("Poucas palavras - " + tamanho);
					cancelled(); break;
				}
				ArrayList<Object>weightsNBiases = facade.getWeightsNBiasFiles("Output", 17, 5);
				score = facade.checkNeuralMessages(facade.applyWeightsNBias(weightsNBiases, 17, 5));
				updateMessage("");
				updateMessage(df.format(score));
				if(score > 0.4){
					alarmeScoreEstilo = true; alarmeScoreTexto = true;
					cancelled();
					break;
				}
				Integer minuto = 60000;
				Thread.currentThread();		
				Thread.sleep(10*minuto);
			}
			Thread.currentThread().interrupt();
			return null;
		}
	};

	public Task<Void> taskLimpaTexto = new Task<Void>() {
		
		@Override
		protected Void call() throws Exception {
			updateMessage("");
			Thread.currentThread().interrupt();
			return null;
		}
	};
	
	public Task<Void> taskLimpaEstilo= new Task<Void>() {
		
		@Override
		protected Void call() throws Exception {
			updateMessage("-fx-background-color: white; -fx-border-width: 2; -fx-border-color: gray;");
			Thread.currentThread().interrupt();
			return null;
		}
	};

	public Task<Void> taskAlarme = new Task<Void>() {

		@Override
		protected Void call() throws Exception {
				while (!parar) {
				if (alarme) {
					Thread.currentThread();
					Thread.sleep(2500);
					updateMessage("");
					updateMessage(" ALARME PFE\nemail enviado!");
					Mail mail = new Mail();
					mail.generateAndSendEmail(log);
					parar = true;
				}
			}
			alarme = false;
			Thread.currentThread().interrupt();
			return null;
		}
	};

	public Task<Void> taskAlarmeScore = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			while (!pararPontuacaoEstilo) {
				if (alarmeScoreEstilo) {
					Mail mail = new Mail();
					if(score >=0.5 && score < 0.55){
						updateMessage("-fx-background-color: #ffff33; -fx-border-width: 2; -fx-border-color: gray;");
						mail.generateAndSendEmail("Pontuação = " + score.toString());
					}
					if(score >= 0.55 && score < 0.7 ){
						updateMessage("-fx-background-color: #ffff00; -fx-border-width: 2; -fx-border-color: gray;");
						mail.generateAndSendEmail("Pontuação = " + score.toString());
					}
					if(score >= 0.7 && score < 0.8){
						updateMessage("-fx-background-color: #ff3333; -fx-border-width: 2; -fx-border-color: gray;");
						mail.generateAndSendEmail("Pontuação = " + score.toString());
					}
					if(score >= 0.8 && score < 0.93){
						updateMessage("-fx-background-color: #cc0000; -fx-border-width: 2; -fx-border-color: gray;");
						mail.generateAndSendEmail("Pontuação = " + score.toString());
					}
					if(score >= 0.93){
						updateMessage("-fx-background-color: #ff0000; -fx-border-width: 2; -fx-border-color: gray;");
						mail.generateAndSendEmail("Pontuação = " + score.toString());
					}
					pararPontuacaoEstilo = true;
				}																												
			}
			alarmeScoreEstilo = false;
			Thread.currentThread().interrupt();
			return null;
		}
	};
	
	public Task<Void> taskAlarmePontuacaoTexto = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			while (!pararPontuacaoTexto) {
				if (alarmeScoreTexto) {
					updateMessage("ALARME SCORE\n email enviado!");
					pararPontuacaoTexto = true;
				}																												
			}
			alarmeScoreTexto = false;
			Thread.currentThread().interrupt();
			return null;
		}
	};
	
}
