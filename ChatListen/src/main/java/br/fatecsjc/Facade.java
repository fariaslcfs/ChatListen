package br.fatecsjc;

/**
 * @author Luiz Carlos Farias da Silva
 * Facade class gathers commonly used methods
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import br.fatecsjc.model.Messages;
import br.fatecsjc.enumerations.*;

@SuppressWarnings("unused")
public class Facade {
	// Shortly, a facade is an object that provides a simplified interface to a
	// larger body of code.
	private DecimalFormat df = new DecimalFormat("0.0000");

	// method reads files, fills vectors and fills List with vectors
	public ArrayList<Double[]> vectorsReaderFromFiles(Integer vectorType)
			throws IOException {
		ArrayList<Double[]> matTotalPred = new ArrayList<Double[]>();
		ArrayList<Double[]> matTotalNps = new ArrayList<Double[]>();
		ArrayList<Double[]> matTotalCyber = new ArrayList<Double[]>();
		ArrayList<Double[]> matTotalTeste = new ArrayList<Double[]>();
		ArrayList<Double[]> matTotalFinalTestePred = new ArrayList<Double[]>();
		ArrayList<Double[]> matTotalFinalTesteNps = new ArrayList<Double[]>();
		ArrayList<Double[]> matTotalFinalTesteCyber = new ArrayList<Double[]>();

		// reads Pred files
		BufferedReader brPred = new BufferedReader(new FileReader(
				"Output/vectorsPred.txt"));
		String line;
		while ((line = brPred.readLine()) != null) {
			String[] numbers = line.split(" ");
			Double[] vec = new Double[numbers.length];
			int i = 0;
			for (String num : numbers) {
				vec[i] = Double.valueOf(num);
				i++;
			}
			// adds to List
			matTotalPred.add(vec);
		}
		brPred.close();

		// reads Nps files
		BufferedReader brNps = new BufferedReader(new FileReader(
				"Output/vectorsNps.txt"));
		while ((line = brNps.readLine()) != null) {
			String[] numbers = line.split(" ");
			Double[] vec = new Double[numbers.length];
			int i = 0;
			for (String num : numbers) {
				vec[i] = Double.valueOf(num);
				i++;
			}
			// adds to List
			matTotalNps.add(vec);
		}
		brNps.close();

		// reads CyberSex files
		BufferedReader brCyber = new BufferedReader(new FileReader(
				"Output/vectorsCyber.txt"));
		while ((line = brCyber.readLine()) != null) {
			String[] numbers = line.split(" ");
			Double[] vec = new Double[numbers.length];
			int i = 0;
			for (String num : numbers) {
				vec[i] = Double.valueOf(num);
				i++;
			}
			// adds to List
			matTotalCyber.add(vec);
		}
		brCyber.close();

		// reads Conjunto de Teste files
		BufferedReader brTeste = new BufferedReader(new FileReader(
				"ConjuntoTESTE/teste.txt"));
		while ((line = brTeste.readLine()) != null) {
			String[] numbers = line.split(" ");
			Double[] vec = new Double[numbers.length];
			int i = 0;
			for (String num : numbers) {
				// System.out.print(num + " ");
				vec[i] = Double.valueOf(num);
				i++;
			}
			// adds to List
			matTotalTeste.add(vec);
		}
		brTeste.close();

		// reads Conjunto de Teste Final Pred Vectors
		BufferedReader brFinalTesteVectorsPred = new BufferedReader(
				new FileReader("TestesFinaisVectors/vetoresTesteFinalPred.txt"));
		while ((line = brFinalTesteVectorsPred.readLine()) != null) {
			String[] numbers = line.split(" ");
			Double[] vec = new Double[numbers.length];
			int i = 0;
			for (String num : numbers) {
				// System.out.print(num + " ");
				vec[i] = Double.valueOf(num);
				i++;
			}
			// adds to List
			matTotalFinalTestePred.add(vec);
		}
		brFinalTesteVectorsPred.close();

		// reads Conjunto de Teste Final Nps Vectors
		BufferedReader brFinalTesteVectorsNps = new BufferedReader(
				new FileReader("TestesFinaisVectors/vetoresTesteFinalNps.txt"));
		while ((line = brFinalTesteVectorsNps.readLine()) != null) {
			String[] numbers = line.split(" ");
			Double[] vec = new Double[numbers.length];
			int i = 0;
			for (String num : numbers) {
				// System.out.print(num + " ");
				vec[i] = Double.valueOf(num);
				i++;
			}
			// adds to List
			matTotalFinalTesteNps.add(vec);
		}
		brFinalTesteVectorsNps.close();

		// reads Conjunto de Teste Final Cyber Vectors
		BufferedReader brFinalTesteVectorsCyber = new BufferedReader(
				new FileReader("TestesFinaisVectors/vetoresTesteFinalCyber.txt"));
		while ((line = brFinalTesteVectorsCyber.readLine()) != null) {
			String[] numbers = line.split(" ");
			Double[] vec = new Double[numbers.length];
			int i = 0;
			for (String num : numbers) {
				// System.out.print(num + " ");
				vec[i] = Double.valueOf(num);
				i++;
			}
			// adds to List
			matTotalFinalTesteCyber.add(vec);
		}
		brFinalTesteVectorsCyber.close();

		switch (vectorType) {
		case 1:
			return matTotalPred;
		case 2:
			return matTotalNps;
		case 3:
			return matTotalCyber;
		case 4:
			return matTotalTeste;
		case 5:
			return matTotalFinalTestePred;
		case 6:
			return matTotalFinalTesteNps;
		case 7:
			return matTotalFinalTesteCyber;
		default:
			break;
		}
		return null;
	}

	// method for generating vectors in Output from files in PosTagPred and
	// PosTagNps
	public void generateVectorsFilesFromFiles() throws IOException {
		PreClassification preClassifier = new PreClassification();

		System.out.println("Generating Vector Files From Files in PosTagPred");
		// Generates vectors from Files and fills matTotalPred
		// Inside the classifier a file with all the vectors of type Pred is
		// created
		//ArrayList<Double[]> matTotalPred = preClassifier.classifier("PosTagPred/", "Output/vectorsPred.txt", 0, 518, 1d);

		System.out.println("Generating Vector Files From Files in PosTagNps");
		// Generates vectors from Files and fills matTotalNps
		// Inside the classifier a file with all the vectors of type NPS is
		// created
		//ArrayList<Double[]> matTotalNps = preClassifier.classifier("PosTagNPS/", "Output/vectorsNps.txt", 0, 15, 0d);

		System.out.println("Generating Vector Files From Files in PosTagCyberSex");
		// Generates vectors from Files and fills matTotalCyber
		// Inside the classifier a file with all the vectors of type CyberSex is
		// created
		ArrayList<Double[]> matTotalCyber = preClassifier.classifier("PosTagCyberSex/", "Output/vectorsCyber.txt", 0, 28, 0d);

		/*
		 * // It may generated inside this method BufferedWriter bwPred = new
		 * BufferedWriter(new FileWriter("Output/vectorsPred.txt"));
		 * for(Double[] d : matTotalPred) { for (Double e : d) {
		 * bwPred.write(e.toString() + " "); } bwPred.write("\n"); }
		 * bwPred.close();
		 */

		/*
		 * //It may be generated inside this method BufferedWriter bwNps = new
		 * BufferedWriter(new FileWriter("Output/vectorsNps.txt")); for
		 * (Double[] d : matTotalNps) { for (Double e : d) {
		 * bwNps.write(e.toString() + " "); } bwNps.write("\n"); }
		 * bwNps.close();
		 */

		/*
		 * //It may be generated inside this method BufferedWriter bwCyber = new
		 * BufferedWriter(new FileWriter("Output/vectorsCyber.txt")); for
		 * (Double[] d : matTotalCyber) { for (Double e : d) {
		 * bwCyber.write(e.toString() + " "); } bwCyber.write("\n"); }
		 * bwCyber.close();
		 */

	}

	// method trains, and backup weights, biases
	public ArrayList<Object> trainningGenerator(Integer hidden, Integer epocs,
			Double learning, Double momentum) throws IOException {
		ArrayList<Double[]> matTotalPred = vectorsReaderFromFiles(1);
		ArrayList<Double[]> matTotalNps = vectorsReaderFromFiles(2);
		ArrayList<Double[]> matTotalCyber = vectorsReaderFromFiles(3);
		TrainnerNN trainning = new TrainnerNN();

		// Trainning Set
		Double[][] trainningSet = new Double[][] { matTotalPred.get(20),
				matTotalPred.get(21), matTotalPred.get(22),
				matTotalPred.get(23), matTotalPred.get(24),
				matTotalPred.get(25), matTotalPred.get(26),
				matTotalPred.get(27), matTotalPred.get(28),
				matTotalPred.get(29), matTotalPred.get(30),
				matTotalPred.get(31), matTotalPred.get(32),
				matTotalPred.get(33), matTotalPred.get(34),
				matTotalPred.get(35), matTotalPred.get(36),
				matTotalPred.get(37), matTotalPred.get(38),
				matTotalPred.get(39), matTotalNps.get(0), matTotalNps.get(1),
				matTotalNps.get(2), matTotalNps.get(3), matTotalNps.get(4),
				matTotalNps.get(5), matTotalNps.get(6), matTotalNps.get(7),
				matTotalNps.get(8), matTotalNps.get(9), matTotalCyber.get(0),
				matTotalCyber.get(1), matTotalCyber.get(2),
				matTotalCyber.get(3), matTotalCyber.get(4),
				matTotalCyber.get(5), matTotalCyber.get(6),
				matTotalCyber.get(7), matTotalCyber.get(8),
				matTotalCyber.get(9) };

		// Validation Set
		Double[][] validationSet = new Double[][] { matTotalPred.get(10),
				matTotalPred.get(11), matTotalPred.get(12),
				matTotalPred.get(13), matTotalPred.get(14),
				matTotalPred.get(15), matTotalPred.get(16),
				matTotalPred.get(17), matTotalPred.get(18),
				matTotalPred.get(19), matTotalNps.get(10), matTotalNps.get(11),
				matTotalNps.get(12), matTotalNps.get(13), matTotalNps.get(14),
				matTotalCyber.get(10), matTotalCyber.get(11),
				matTotalCyber.get(12), matTotalCyber.get(13),
				matTotalCyber.get(14) };

		trainning.treinar(trainningSet, validationSet, hidden, epocs, learning,
				momentum);
		Neural neural = trainning.getMelhorRna();

		// vectors for weights and biases declarations
		Double[][] inhidweights = neural.getPesosEntradaCamadaOculta();
		Double[] hidoutweights = neural.getPesosCamadaOcultaSaida();
		Double[] biashid = neural.getBiasCamadaOculta();
		Double biasout = neural.getBiasSaida();

		// list declaration and filling
		ArrayList<Object> col = new ArrayList<Object>();
		col.add(inhidweights);
		col.add(hidoutweights);
		col.add(biashid);
		col.add(biasout);

		// backup weights into files in HD
		BufferedWriter bwWeightsInhid = new BufferedWriter(new FileWriter(
				"Output/weightsInHid.txt"));
		for (Double[] d : inhidweights) {
			for (Double d1 : d) {
				bwWeightsInhid.write(d1.toString() + " ");
			}
			bwWeightsInhid.write("\n");
		}
		bwWeightsInhid.close();

		// backup weights
		BufferedWriter bwWeightsHidOut = new BufferedWriter(new FileWriter(
				"Output/weightsHidOut.txt"));
		for (int i = 0; i < hidoutweights.length; i++) {
			bwWeightsHidOut.write(hidoutweights[i].toString() + " ");
		}
		bwWeightsHidOut.close();

		// backup bias
		BufferedWriter bwBiasInhid = new BufferedWriter(new FileWriter(
				"Output/biasInHid.txt"));
		for (int i = 0; i < biashid.length; i++) {
			bwBiasInhid.write(biashid[i].toString() + " ");
		}
		bwBiasInhid.close();

		// backup bias
		BufferedWriter bwBiasOut = new BufferedWriter(new FileWriter(
				"Output/biasOut.txt"));
		bwBiasOut.write(biasout.toString() + "\n");
		bwBiasOut.close();
		return col;
	}

	// method reads weights and bias from backup files and returns List filled
	// with vectors
	public ArrayList<Object> getWeightsNBiasFiles(String path, Integer In,
			Integer Hid) throws NumberFormatException, IOException {
		ArrayList<Object> listWeightsNbias = new ArrayList<Object>();
		Double[][] weightsInHid = new Double[In][Hid];
		// reads weights from file
		BufferedReader brWeightsInHid = new BufferedReader(new FileReader(path
				+ "/weightsInHid.txt"));
		String line;
		String[] numbers;
		int i = 0;
		while ((line = brWeightsInHid.readLine()) != null) {
			int j = 0;
			numbers = line.split(" ");
			for (String num : numbers) {
				weightsInHid[i][j] = Double.valueOf(num);
				j++;
			}
			i++;
		}
		brWeightsInHid.close();
		listWeightsNbias.add(weightsInHid);
		// reads weights from file
		BufferedReader brWeightsHidOut = new BufferedReader(new FileReader(path
				+ "/weightsHidOut.txt"));
		line = brWeightsHidOut.readLine();
		i = 0;
		Double[] v = new Double[Hid];
		for (String num : line.split(" ")) {
			v[i] = Double.valueOf(num);
			i++;
		}
		brWeightsHidOut.close();
		listWeightsNbias.add(v);

		// reads bias from file
		BufferedReader brBiasInHid = new BufferedReader(new FileReader(path
				+ "/biasInHid.txt"));
		line = brBiasInHid.readLine();
		i = 0;
		v = new Double[Hid];
		for (String num : line.split(" ")) {
			v[i] = Double.valueOf(num);
			i++;
		}
		brBiasInHid.close();
		listWeightsNbias.add(v);

		// reads bias from file
		BufferedReader brBiasOut = new BufferedReader(new FileReader(path
				+ "/biasOut.txt"));
		String bias;
		bias = brBiasOut.readLine();
		Double biasout = Double.valueOf(bias);
		brBiasOut.close();
		listWeightsNbias.add(biasout);

		// returns list filled
		return listWeightsNbias;
	}

	// creates neural and set weights and biases
	public Neural applyWeightsNBias(ArrayList<Object> weightsNbias,
			Integer entradas, Integer ocultas) {
		Neural neural = new Neural(entradas, ocultas, true);
		neural.setPesosEntradaCamadaOculta((Double[][]) weightsNbias.get(0));
		neural.setPesosCamadaOcultaSaida((Double[]) weightsNbias.get(1));
		neural.setBiasCamadaOculta((Double[]) weightsNbias.get(2));
		neural.setBiasSaida((Double) weightsNbias.get(3));
		// returns adjusted neural
		return neural;
	}

	// applies vectors to the neural and returns the error
	public Double checkNeural(Neural neural, Integer startVet,Integer finalVet, Integer vectorsType) throws IOException {
		// startVet=first vector to apply the RNA
		// finalVet=last vector to apply the RNA
		ArrayList<Double[]> matTotalPred = vectorsReaderFromFiles(1);
		ArrayList<Double[]> matTotalNps = vectorsReaderFromFiles(2);
		ArrayList<Double[]> matTotalCyber = vectorsReaderFromFiles(3);
		ArrayList<Double[]> matTotalTeste = vectorsReaderFromFiles(4);
		ArrayList<Double[]> matTotalFinalTestePred = vectorsReaderFromFiles(5);
		ArrayList<Double[]> matTotalFinalTesteNps = vectorsReaderFromFiles(6);
		ArrayList<Double[]> matTotalFinalTesteCyber = vectorsReaderFromFiles(7);

		Double result = 0d;
		for (int i = startVet; i <= finalVet; i++) {
			switch (vectorsType) {
			case 1:
				Double[] logVectorPred = matTotalPred.get(i);
				result = neural.executar(logVectorPred);
				break;
			case 2:
				Double[] logVectorNps = matTotalNps.get(i);
				result = neural.executar(logVectorNps);
				break;
			case 3:
				Double[] logVectorCyber = matTotalCyber.get(i);
				result = neural.executar(logVectorCyber);
				break;
			case 4:
				Double[] logVectorTeste = matTotalTeste.get(i);
				result = neural.executar(logVectorTeste);
				break;
			case 5:
				Double[] logVectorFinaTestePred = matTotalFinalTestePred.get(i);
				result = neural.executar(logVectorFinaTestePred);
				break;
			case 6:
				Double[] logVectorFinalTesteNps = matTotalFinalTesteNps.get(i);
				result = neural.executar(logVectorFinalTesteNps);
				break;
			case 7:
				Double[] logVectorFinalTesteCyber = matTotalFinalTesteCyber
						.get(i);
				result = neural.executar(logVectorFinalTesteCyber);
				break;
			default:
				break;
			}
		}

		/*
		 * Double acc = 0d; // accumulator Double pow = 0d; // power of var
		 * Double error = 0d; // error var int aux = 0; // counter var
		 * 
		 * for (Double r : result) { // Mean Squared -- it avoids negative
		 * values as well pow = Math.pow(r, 2); acc += pow; aux++; }
		 */
		/*
		 * if (vectorsType == 1) { // if predator then 1 minus ther error. i.e:
		 * 1 - // 0.99 = 0.01 return 1d - acc / aux; } return acc / aux; // if
		 * nps or cybersex then error is directly passed
		 */
		return result;
	}

	// calls crud methods to get files, applies postClassification method
	// classifier
	// and returns the error
	public Double checkNeuralMessages(Neural neural) throws Exception {
		Crud crud = new Crud();
		PostClassification postClassifier = new PostClassification();
		List<Messages> listMsg = crud.daoMessages.findMessagesEntities();
		return neural.executar(postClassifier.classifier(listMsg));
	}

	// calls crud method to get files inserted into messages table for tests
	// purposes
	public void fileIntoTable(String pathNfile) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(pathNfile));
		Crud crud = new Crud();
		crud.daoMessages.truncateTable("messages");
		String line = null;
		while ((line = br.readLine()) != null) {
			crud.insertMessages(line);
		}
		// crud.daoMessages.createIndex();
		crud.emfMessages.close();
		br.close();
	}

	// classifies with POStagging (Part Of Speech Tag)
	public void applyPOSTagging(String pathToSave, String pathToReadFiles)
			throws IOException {
		PreClassification preClassifier = new PreClassification();
		PostTagger ptag = new PostTagger();
		ArrayList<File> files = new ArrayList<File>();
		files = preClassifier.listf(pathToReadFiles, files);
		for (int i = 0; i < files.size(); i++) {
			File fileIn = files.get(i);
			System.out.println(fileIn.getName());
			ptag.tagging(fileIn, pathToSave);
		}
	}

	// finds words according to WordNet Affect
	public String findInEnumerations(String word) {
		// Anger Check
		for (AngerAdjective anger : AngerAdjective.values()) {
			if (anger.toString().toLowerCase().equals(word)) {
				return "anger";
			}
		}
		for (AngerAdverb anger : AngerAdverb.values()) {
			if (anger.toString().toLowerCase().equals(word)) {
				return "anger";
			}
		}
		for (AngerNoun anger : AngerNoun.values()) {
			if (anger.toString().toLowerCase().equals(word)) {
				return "anger";
			}
		}
		for (AngerVerb anger : AngerVerb.values()) {
			if (anger.toString().toLowerCase().equals(word)) {
				return "anger";
			}
		}

		// Disgust Check
		for (DisgustAdjective disgust : DisgustAdjective.values()) {
			if (disgust.toString().toLowerCase().equals(word)) {
				return "disgust";
			}
		}
		for (DisgustAdverb disgust : DisgustAdverb.values()) {
			if (disgust.toString().toLowerCase().equals(word)) {
				return "disgust";
			}
		}
		for (DisgustNoun disgust : DisgustNoun.values()) {
			if (disgust.toString().toLowerCase().equals(word)) {
				return "disgust";
			}
		}
		for (DisgustVerb disgust : DisgustVerb.values()) {
			if (disgust.toString().toLowerCase().equals(word)) {
				return "disgust";
			}
		}

		// Fear Check
		for (FearAdjective fear : FearAdjective.values()) {
			if (fear.toString().toLowerCase().equals(word)) {
				return "fear";
			}
		}
		for (FearAdverb fear : FearAdverb.values()) {
			if (fear.toString().toLowerCase().equals(word)) {
				return "fear";
			}
		}
		for (FearNoun fear : FearNoun.values()) {
			if (fear.toString().toLowerCase().equals(word)) {
				return "fear";
			}
		}
		for (FearVerb fear : FearVerb.values()) {
			if (fear.toString().toLowerCase().equals(word)) {
				return "fear";
			}
		}

		// Joy Check
		for (JoyAdjective joy : JoyAdjective.values()) {
			if (joy.toString().toLowerCase().equals(word)) {
				return "joy";
			}
		}
		for (JoyAdverb joy : JoyAdverb.values()) {
			if (joy.toString().toLowerCase().equals(word)) {
				return "joy";
			}
		}
		for (JoyNoun joy : JoyNoun.values()) {
			if (joy.toString().toLowerCase().equals(word)) {
				return "joy";
			}
		}
		for (JoyVerb joy : JoyVerb.values()) {
			if (joy.toString().toLowerCase().equals(word)) {
				return "joy";
			}
		}

		// Sadness Check
		for (SadnessAdjective sadness : SadnessAdjective.values()) {
			if (sadness.toString().toLowerCase().equals(word)) {
				return "sadness";
			}
		}
		for (SadnessAdverb sadness : SadnessAdverb.values()) {
			if (sadness.toString().toLowerCase().equals(word)) {
				return "sadness";
			}
		}
		for (SadnessNoun sadness : SadnessNoun.values()) {
			if (sadness.toString().toLowerCase().equals(word)) {
				return "sadness";
			}
		}
		for (SadnessVerb sadness : SadnessVerb.values()) {
			if (sadness.toString().toLowerCase().equals(word)) {
				return "sadness";
			}
		}

		// Surprise Check
		for (SurpriseAdjective surprise : SurpriseAdjective.values()) {
			if (surprise.toString().toLowerCase().equals(word)) {
				return "surprise";
			}
		}
		for (SurpriseAdverb surprise : SurpriseAdverb.values()) {
			if (surprise.toString().toLowerCase().equals(word)) {
				return "surprise";
			}
		}
		for (SurpriseNoun surprise : SurpriseNoun.values()) {
			if (surprise.toString().toLowerCase().equals(word)) {
				return "surprise";
			}
		}
		for (SurpriseVerb surprise : SurpriseVerb.values()) {
			if (surprise.toString().toLowerCase().equals(word)) {
				return "surprise";
			}
		}
		return "NEAW"; // NOT EVEN A WORD
	}

	public void finalTestingWithFiles(String filesFolder,ArrayList<File> files, String nameOutputFile, boolean type) throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter("Output/"+ nameOutputFile));
		ArrayList<Object> weightsBiasChecking = getWeightsNBiasFiles("Output/", 17, 5);
		Neural neural = applyWeightsNBias(weightsBiasChecking, 17, 5);
		listf(filesFolder, files);
		Double esp = 0d; int cont = 0;
		for (File f : files) {
			cont++;
			fileIntoTable(filesFolder + f.getName());
			Double saida = checkNeuralMessages(neural);
			System.out.println("Arquivo " + cont + "  Saída=" + saida.toString()
					+ "  Esperado=" + (esp = type ? 1.0 : 0.0) + "  Erro="
					+ (esp - saida) + "\n");
			bw.write("Arquivo " + cont + "  Saída=" + saida.toString()
					+ "  Esperado=" + (esp = type ? 1.0 : 0.0) + "  Erro="
					+ (esp - saida) + "\n");
		}
		bw.close();
	}

	public void finalTestingWithVectors(String folderNfile2ReadFrom,String nameOutputFile, boolean typeOrigFile, int type, int size) throws Exception {
		ArrayList<Object> weightsBiasChecking = getWeightsNBiasFiles("Output/",	17, 5);
		Neural neural = applyWeightsNBias(weightsBiasChecking, 17, 5);
		BufferedWriter bw = new BufferedWriter(new FileWriter("Output/"+nameOutputFile));
		int cont = 0; Double esp;
		for(int i = 0; i < size; i++){
			Double saida = checkNeural(neural, i, i, type);	
			System.out.println("Vetor "+(i+1)+"  Saída="+saida.toString()+"  Esperado="+(esp=typeOrigFile?1.0:0.0)+"  Delta="+(esp-saida)+"\n");
			bw.write("Vetor "+(i+1)+"  Saída="+saida.toString()+"  Esperado="+(esp=typeOrigFile?1.0:0.0)+"  Erro="+(esp-saida)+"\n");
		}
		bw.close();
	}

	public ArrayList<File> listf(String folderName, ArrayList<File> files)
			throws IOException {
		File directory = new File(folderName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files);
			}
		}
		return files;
	}

}
