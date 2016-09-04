package br.fatecsjc;

/**
 * @author Luiz Carlos Farias da Silva
 * Main Class - Here everything starts
 */

import java.awt.Toolkit;
import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class MainApp extends Application {
	Parent root;
	Scene scene;
	static Neural neural;
	static Facade facade;

	@Override
	public void start(Stage stage) throws Exception {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("Scene.fxml"));
		scene = new Scene(root);
		scene.getStylesheets().add("Styles.css");
		stage.setTitle("ChatListen");
		stage.setScene(scene);
		Image icon = new Image("file:Images/icon.jpg");
		stage.getIcons().add(icon);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00000");
		Toolkit.getDefaultToolkit().beep();
		Facade facade = new Facade();
		//facade.applyPOSTagging("PosTagPred", "LogsPred");
		//facade.applyPOSTagging("PosTagNps", "LogsNps");
		//facade.applyPOSTagging("PosTagCyber", "LogsCyberSex");
		
		//facade.generateVectorsFilesFromFiles();
	
		//facade.fileIntoTable("LogsPred/arqPred-300.txt");
		//launch();

/*			
		Integer esperado = 1, entradas = 17, co = 5, epocas = 10000, cont = 0, iter = 0;
		Double ta = 0.1, fm = 0.1;
		Double saida = 0d, saidaArbitrada=0.5;
		System.out.println("Teste RNA\nLimiar atribuido="+saidaArbitrada);
		ArrayList<Object> weightsBiasChecking = facade.getWeightsNBiasFiles("Output", entradas, co);
		neural = facade.applyWeightsNBias(weightsBiasChecking, entradas,co);
		System.out.println("VETOR\tESPERADO\tSAÍDA\t\tCLASSIFICADO\tREAL\t\tRESULTADO");
		for (int i = 0; i < 31; i++){
			cont++;
			esperado = i<18?0:1;
			saida = facade.checkNeural(neural,i,i,4);
			String real = null, esp = null;
			System.out.println(
					cont+"\t"+esperado+"  "+"\t\t"+df.format(saida)+
					"\t\t"+(esp=saida>saidaArbitrada?"Pred":"NãoPred")+ 
					"\t\t"+(i<18?real="NãoPred":(real="Pred"))+
					(esp.equals(real)?"\t\tAcertou":"\t\tErrou"));
		}
		*/	
	
		//ArrayList<File> filesPred = new ArrayList<File>();
		//facade.finalTestingWithFiles("TestesFinaisPredFiles/", filesPred, "FinalTestePredFiles.txt", true);
		//ArrayList<File> filesNps = new ArrayList<File>();
		//facade.finalTestingWithFiles("TestesFinaisNpsFiles/", filesNps, "FinalTesteNpsFiles.txt", false);
		//ArrayList<File> filesCyber = new ArrayList<File>();
		//facade.finalTestingWithFiles("TestesFinaisCyberFiles/", filesCyber, "FinalTesteCyberFiles.txt", false);	
		
		//facade.finalTestingWithVectors("TestesFinaisVectors/vetoresTesteFinalPred.txt", "FinalTestePredVectors.txt", true, 5, 10);
		//facade.finalTestingWithVectors("TestesFinaisVectors/vetoresTesteFinalNps.txt", "FinalTesteNpsVectors.txt", false, 6, 5);
	    //facade.finalTestingWithVectors("TestesFinaisVectors/vetoresTesteFinalCyber.txt", "FinalTesteCyberVectors.txt", false, 7, 10);
		
		Toolkit.getDefaultToolkit().beep();	System.exit(0);
	}
}


