package br.fatecsjc;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains methods for CRUD operations
 */

import java.text.DecimalFormat;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.fatecsjc.controller.MessagesJpaController;
import br.fatecsjc.controller.VectorJpaController;
import br.fatecsjc.model.Messages;
import br.fatecsjc.model.Vector;

public class Crud {

	EntityManagerFactory emfMessages = Persistence.createEntityManagerFactory("CrudPU");
	MessagesJpaController daoMessages = new MessagesJpaController(emfMessages);
	
	EntityManagerFactory emfVector = Persistence.createEntityManagerFactory("CrudPUVector");
	VectorJpaController daoVector = new VectorJpaController(emfVector);

	DecimalFormat df = new DecimalFormat("0.00");
	
	public void insertMessages(String str) throws Exception {
		Messages mensagem = new Messages();
		mensagem.setLinha(str);
		daoMessages.create(mensagem);
	}
	
	public void insertVector(Double[] d) {
		Vector vector = new Vector();
		vector.setPosit(d[0]);vector.setJoyw(d[3]);vector.setSadw(d[4]);
		vector.setAngw(d[5]);vector.setSurw(d[6]);vector.setDisw(d[7]);
		vector.setFeaw(d[8]);vector.setAppw(d[9]);vector.setRelw(d[10]);
		vector.setFamw(d[11]);vector.setCdew(d[12]);vector.setInfw(d[13]);
		vector.setPrpw(d[14]);vector.setRefw(d[15]);vector.setOblv(d[16]);
		vector.setEmot(d[17]);vector.setImps(d[18]);vector.setCatg(d[19]);
		daoVector.create(vector);
	}

	public EntityManagerFactory getEmfMessages() {
		return emfMessages;
	}

	public void setEmfMessages(EntityManagerFactory emfMessages) {
		this.emfMessages = emfMessages;
	}

	public MessagesJpaController getDaoMessages() {
		return daoMessages;
	}

	public void setDaoMessages(MessagesJpaController daoMessages) {
		this.daoMessages = daoMessages;
	}
	
	
	
	
}
