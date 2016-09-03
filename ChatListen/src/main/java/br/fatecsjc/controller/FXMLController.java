package br.fatecsjc.controller;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains Form and methods associated
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import br.fatecsjc.ChatListeningTasks;
import br.fatecsjc.Crud;

/**
 * @author theone
 *
 */
public class FXMLController {
    
	private static ChatListeningTasks clt;
	private static Crud crud;

    private Thread tInicia, tLimpaTexto, tLimpaEstilo, tAlarme, tAlarmeScore, tScore, tAlarmePontuacaoTexto;

    @FXML
    private Label lab_alarme, rot_chat, lbl_usuario, lbl_senha;
    
    @FXML
    private Label lab_score;

    @FXML
    private TextArea txt_chat;

    @FXML
    private TextField txt_usuario;

    @FXML
    private PasswordField txt_senha;

    @FXML
    private Button bot_sair, bot_limpar, bot_iniciar, bot_parar, bot_score, bot_espiar;

    @FXML
    public void bot_iniciar_Action(ActionEvent event) throws Exception {
  
    	if(bot_iniciar.getText().equals("Start")){
    		bot_iniciar.setText("Stop");
    		bot_score.setDisable(false);
        	clt = new ChatListeningTasks();
        	crud = new Crud();
        	clt.login(txt_usuario.getText(), txt_senha.getText()); 	
        	crud.getDaoMessages().truncateTable("messages");
        	crud.getDaoMessages().getEntityManager().close();
        	
            txt_chat.textProperty().bind(clt.taskInicia.messageProperty());
            tInicia = new Thread(clt.taskInicia);
            tInicia.start();
            
            lab_alarme.textProperty().bind(clt.taskAlarme.messageProperty());
            tAlarme = new Thread(clt.taskAlarme);
            tAlarme.start();  
    	}
    	else{
			bot_iniciar.setText("Start");
			bot_score.setDisable(true);
			clt = new ChatListeningTasks();
			clt.disconnect();
			tInicia.interrupt();
			tAlarme.interrupt();
			tAlarmePontuacaoTexto.interrupt();
			tAlarmeScore.interrupt();
    	}
    }

    @FXML
    public void bot_score_Action(ActionEvent event) {
    	clt = new ChatListeningTasks();

     	lab_score.textProperty().bind(clt.taskScore.messageProperty());
       	tScore = new Thread(clt.taskScore);
       	tScore.start();
    	
       	lab_score.styleProperty().bind(clt.taskAlarmeScore.messageProperty());       	
        tAlarmeScore = new Thread(clt.taskAlarmeScore);
        tAlarmeScore.start();
        
        lab_alarme.textProperty().bind(clt.taskAlarmePontuacaoTexto.messageProperty());
        tAlarmePontuacaoTexto = new Thread(clt.taskAlarmePontuacaoTexto);
        tAlarmePontuacaoTexto.start();
        
    }

    @FXML
    public void bot_limpar_Action(ActionEvent event) {
        clt = new ChatListeningTasks();
        txt_usuario.setText("");
        txt_senha.setText("");
        lab_alarme.textProperty().bind(clt.taskLimpaTexto.messageProperty());
        lab_score.textProperty().bind(clt.taskLimpaTexto.messageProperty());
        txt_chat.textProperty().bind(clt.taskLimpaTexto.messageProperty());
        tLimpaTexto = new Thread(clt.taskLimpaTexto);
        tLimpaTexto.start();
        
        lab_alarme.styleProperty().bind(clt.taskLimpaEstilo.messageProperty());
        lab_score.styleProperty().bind(clt.taskLimpaEstilo.messageProperty());
        tLimpaEstilo = new Thread(clt.taskLimpaEstilo);
        tLimpaEstilo.start();
    }
    
    @FXML
    public void bot_sair_Action(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    public void bot_espiar_Action(ActionEvent event){
    	if(txt_chat.isVisible()){
    		txt_chat.setVisible(false);
    		bot_espiar.setText("Spy");
    	}
    	else{
    	 	txt_chat.setVisible(true);
    	 	bot_espiar.setText("Hide");
    	}
    }
   
}
