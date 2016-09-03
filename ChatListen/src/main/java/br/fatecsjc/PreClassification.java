package br.fatecsjc;

/**
 * @author Luiz Carlos Farias da Silva
 * Class coantais methods fo pre classification and file handling
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PreClassification {
	private Double vector[];
	private String[] words;
	private int totalEspWords;
	private int counter;

	@SuppressWarnings({ "resource" })
	public ArrayList<Double[]> classifier(String filesFolder,String pathAndFileName, int numIniFiles, int numEndFiles, double cat)
			throws IOException {
		Facade facade = new Facade();
		ArrayList<Double[]> vectorRet = new ArrayList<Double[]>();
		String strPattern = "^.*_vb*";
		Pattern pattern = Pattern.compile(strPattern);
		pattern.matcher(strPattern);
		//PostTagger ptag = new PostTagger();
		ArrayList<File> files = new ArrayList<File>();
		File fileVector = new File(pathAndFileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileVector));
		SentiWordNetScore swns = new SentiWordNetScore("Models/SentiWordNetTab.txt");

		if (!fileVector.exists()) {
			fileVector.createNewFile();
		}

		files = listf(filesFolder, files);

		for (int j = numIniFiles; j < numEndFiles; j++) {
			counter = 0;
			Double sumA = 0d, sumN = 0d, sumV = 0d, sumR = 0d, avg = 0d;
			Double avgA = 0d, avgN = 0d, avgV = 0d, avgR = 0d;

			// When files are raw or not postagged
			// fileTagOut = ptag.tagging(files.get(j));

			File fileTagOut = files.get(j);
			System.out.println(fileTagOut.getName());
			BufferedReader bfTag = new BufferedReader(new FileReader(fileTagOut));
			String postag = null;
			totalEspWords = 0;
			vector = new Double[18];
			for (int c = 0; c < vector.length; c++) {
				vector[c] = 0d;
			}

			while ((postag = bfTag.readLine()) != null) {
				words = postag.split(" ");
				for (int i = 0; i < words.length; i++) {
					// Checks verbs at the beggining - Imperative
					if (pattern.matcher(words[i].toLowerCase()) != null) {
						vector[16] += 1d;
						totalEspWords++;
					}
					// Checks according to WordNet Affect
					String type = facade.findInEnumerations(words[i].toLowerCase());
					switch (type) {
					case "joy":
						vector[1] += 1d;
						totalEspWords++;
						break;
					case "sadness":
						vector[2] += 1d;
						totalEspWords++;
						break;
					case "anger":
						vector[3] += 1d;
						totalEspWords++;
						break;
					case "surprise":
						vector[4] += 1d;
						totalEspWords++;
						break;
					case "disgust":
						vector[5] += 1d;
						totalEspWords++;
						break;
					case "fear":
						vector[6] += 1d;
						totalEspWords++;
						break;
					default:
						break;
					}

					// Checks according to other works
					switch (words[i].toLowerCase()) {
					// --------Relationship Nouns--------
					case "boyfriend_nn":
						vector[7] += 1d;
						totalEspWords++;
						break;
					case "date_nn":
						vector[7] += 1d;
						totalEspWords++;
						break;
					// --------Approach Words--------
					case "meet_vbp":
						vector[8] += 1d;
						totalEspWords++;
						break;
					case "car_nn":
						vector[8] += 1d;
						totalEspWords++;
						break;
					// --------Family Words--------
					case "mum_nn":
						vector[9] += 1d;
						totalEspWords++;
						break;
					case "dad_nn":
						vector[9] += 1d;
						totalEspWords++;
						break;
					// --------Communicative desensitization Words--------
					case "sex_nn":
						vector[10] += 1d;
						totalEspWords++;
						break;
					case "penis_nn":
						vector[10] += 1d;
						totalEspWords++;
						break;
					// --------Information Words--------
					case "asl_jj":
						vector[11] += 1d;
						totalEspWords++;
						break;
					case "home_nn":
						vector[11] += 1d;
						totalEspWords++;
						break;
					// --------Personal Pronouns--------
					case "I_prp":
						vector[12] += 1d;
						totalEspWords++;
						break;
					case "you_prp":
						vector[12] += 1d;
						totalEspWords++;
						break;
					// --------Reflexive Pronouns--------
					case "myself_prp":
						vector[13] += 1d;
						totalEspWords++;
						break;
					case "yourself_prp":
						vector[13] += 1d;
						totalEspWords++;
						break;
					// --------Obligation Verbs--------
					case "must_md":
						vector[14] += 1d;
						totalEspWords++;
						break;
					case "have_vb to_to":
						vector[14] += 1d;
						totalEspWords++;
						break;
					// --------Emoticons--------
					case "8)_jj":
						vector[15] += 1d;
						totalEspWords++;
						break;
					case ":(_nn":
						vector[15] += 1d;
						totalEspWords++;
						break;
					// --------Imperative Sentences--------
					case "do_vb it_prp":
						vector[16] += 1d;
						totalEspWords++;
						break;
					default:
						continue;
					}
					sumA = sumA + swns.extract(words[i].split("_", 2)[0], "a"); // split used to remove	
					sumN = sumN + swns.extract(words[i].split("_", 2)[0], "n"); // the _Abbreviattion
					sumV = sumV + swns.extract(words[i].split("_", 2)[0], "v"); // from PostTagger
					sumR = sumR + swns.extract(words[i].split("_", 2)[0], "r");
					counter++;
				}
			}

			for (int i = 1; i < vector.length - 1; i++) {
				Double num = vector[i] / totalEspWords;
				// Validation
				if (num.isNaN() || num.isInfinite()) {
					vector[i] = 0d;
				} else {
					vector[i] = num;
				}
			}

			avgA = sumA / counter;
			avgN = sumN / counter;
			avgV = sumV / counter;
			avgR = sumR / counter;
			avg = (avgA + avgN + avgV + avgR) / 4;
			vector[0] = avg; // primeiro do vetor = avg
			vector[17] = cat; // último do vetor = categoria
			vectorRet.add(vector);

			for (int i = 0; i < vector.length; i++) {
				bw.write(vector[i] + " ");
			}
			bw.write("\n");
		}
		bw.close();
		return vectorRet;
	}

	public ArrayList<File> listf(String folderName, ArrayList<File> files) throws IOException {
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
