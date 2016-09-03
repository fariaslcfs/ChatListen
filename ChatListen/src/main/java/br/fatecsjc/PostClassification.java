package br.fatecsjc;

/**
 * @author Luiz Carlos Farias da Silva
 * Class coantais methods for post classification - access
 * vectors directly
 */

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import br.fatecsjc.model.Messages;
public class PostClassification {

	public Double[] classifier(List<Messages> listMsg) throws Exception {
		Facade facade = new Facade();
		String strPattern = "^.*_vb*";
		Pattern pattern = Pattern.compile(strPattern);
		pattern.matcher(strPattern);
		POSModel model = new POSModelLoader().load(new File("Models/en-pos-maxent.bin"));
		POSTaggerME tagger = new POSTaggerME(model);
		SentiWordNetScore swns = new SentiWordNetScore("Models/SentiWordNetTab.txt");
		Double[] vector = new Double[17];
		
		for(int i = 0; i < vector.length; i++){
			vector[i] = 0d;
		};
		
		int counter = 0;
		Double sumA = 0d ,sumN = 0d, sumV = 0d, sumR = 0d,avg = 0d; 
		Double avgA = 0d, avgN = 0d, avgV = 0d, avgR = 0d;

		int totalEspWords = 0;
		
		for (int i = 0; i < listMsg.size(); i++) {
			// reads whole line
			String line = listMsg.get(i).getLinha();
			// applies line to Tokenizer methods
			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
			// returns line tokenized into vector tags type String os words tagged
			String[] tags = tagger.tag(whitespaceTokenizerLine);
			// Sample method
			POSSample posSample = new POSSample(whitespaceTokenizerLine, tags);
			// POS tags splitted into posTags String vector
			String[] posTags = posSample.toString().toLowerCase().split(" ");

			for (int j = 0; j < posTags.length; j++) {
				
				if (pattern.matcher(posTags.toString()) != null) {
					vector[16] += 1d;
					totalEspWords++;
				}
				
				// Checks according to WordNet Affect 
				String type = facade.findInEnumerations(posTags[j]);
				//System.out.println(posTags[j]);
				
				switch(type){
					case "joy":
						vector[1]+=1d;
						totalEspWords++;
						break;
					case "sadness":
						vector[2]+=1d;
						totalEspWords++;
						break;
					case "anger":
						vector[3]+=1d;
						totalEspWords++;
						break;
					case "surprise":
						vector[4]+=1d;
						totalEspWords++;
						break;
					case "disgust":
						vector[5]+=1d;
						totalEspWords++;
						break;
					case "fear":
						vector[6]+=1d;
						totalEspWords++;
						break;
					default:
						break;
				}
				
				// Checks according to other works
				switch(posTags[j]){
				    // ------RelationShip Words------
					case "boyfriend_nn":
						vector[7]+=1d;
						totalEspWords++;
						break;
					case "date_nn":
						vector[7]+=1d;
						totalEspWords++;
						break;
					// --------Approach Words--------
					case "meet_vbp":
						vector[8]+=1d;
						totalEspWords++;
						break;
					case "car_nn":
						vector[8]+=1d;
						totalEspWords++;
						break;
					// --------Family Words--------
					case "mum_nn":
						vector[9]+=1d;
						totalEspWords++;
						break;
					case "dad_nn":
						vector[9]+=1d;
						totalEspWords++;
						break;
					// --------Communicative desensitization Words--------
					case "sex_nn":
						vector[10]+=1d;
						totalEspWords++;
						break;
					case "penis_nn":
						vector[10]+=1d;
						totalEspWords++;
						break;
					// --------Information Words--------
					case "asl_jj":
						vector[11]+=1d;
						totalEspWords++;
						break;
					case "home_nn":
						vector[11]+=1d;
						totalEspWords++;
						break;
					// --------Personal Pronouns--------
					case "I_prp":
						vector[12]+=1d;
						totalEspWords++;
						break;
					case "you_prp":
						vector[12]+=1d;
						totalEspWords++;
						break;
					// --------Reflexive Pronouns--------
					case "myself_prp":
						vector[13]+=1d;
						totalEspWords++;
						break;
					case "yourself_prp":
						vector[13]+=1d;
						totalEspWords++;
						break;
					// --------Obligation Verbs--------
					case "must_md":
						vector[14]+=1d;
						totalEspWords++;
						break;
					case "have_vb to_to":
						vector[14]+=1d;
						totalEspWords++;
						break;
					// --------Emoticons--------
					case "8)_jj":
						vector[15]+=1d;
						totalEspWords++;
						break;
					case ":(_nn":
						vector[15]+=1d;
						totalEspWords++;
						break;
					// --------Imperative Sentences--------
					case "do_vb it_prp":
						vector[16]+=1d;
						totalEspWords++;
						break;
					default:
						continue;
				}

				sumA = sumA + swns.extract(posTags[j].split("_", 2)[0], "a");
				sumN = sumN + swns.extract(posTags[j].split("_", 2)[0], "n");
				sumV = sumV + swns.extract(posTags[j].split("_", 2)[0], "v");
				sumR = sumR + swns.extract(posTags[j].split("_", 2)[0], "r");
				counter++;
			}
			
			//System.out.println("");
		}
		
		for (int i = 1; i < vector.length; i++) {
			Double num = vector[i] / totalEspWords;
			if(num.isNaN() || num.isInfinite()){
				vector[i] = 0d;
			}
			else{
				vector[i] = num;
			}
		}
		
		avgA = sumA / counter;
		avgN = sumN / counter;
		avgV = sumV / counter;
		avgR = sumR / counter;
		avg = (avgA + avgN + avgV + avgR) / 4;
		if(avg.isNaN() || avg.isInfinite()){
			vector[0] = 0d;
		}
		else{
			vector[0] = avg;
		}
		// for testing purposes
		for(int i = 0; i < vector.length; i++){
			//System.out.print(vector[i] + " ");
		}
		return vector;
	}
	
	public Double[] classifierRawFiles(List<Messages> listMsg) throws IOException {
		String strPattern = "^.*_VB*";
		Pattern pattern = Pattern.compile(strPattern);
		pattern.matcher(strPattern);
		SentiWordNetScore swns = new SentiWordNetScore("Models/SentiWordNetTab.txt");
		Double[] vector = new Double[17];
		
		int counter = 0;
		double sumA = 0d; double sumN = 0d; double sumV = 0d; double sumR = 0d;
		double avg = 0d; double avgA = 0d; double avgN = 0d; double avgV = 0d;
		double avgR = 0d;

		int totalEspWords = 0;

		for (int i = 0; i < listMsg.size(); i++) {
			String line = listMsg.get(i).getLinha();
			String[] words = line.split(" ");

			if (pattern.matcher(words[i].toString()) != null) {
				vector[16] += 1d;
			}

			for (int j = 0; j < words.length; j++) {
				switch(words[i].toLowerCase()){
					// Joy Words
					case "happy_jj":
						vector[1]+=1d;
						totalEspWords++;
						break;
					case "cheer_nn":
						vector[1]+=1d;
						totalEspWords++;
						break;
					// Sadness Words
					case "bored_vbd":
						vector[2]+=1d;
						totalEspWords++;
						break;
					case "sad_jj":
						vector[2]+=1d;
						totalEspWords++;
						break;
					// --------Anger Words--------
					case "annoying_vbg":
						vector[3]+=1d;
						totalEspWords++;
						break;
					case "furious_jj":
						vector[3]+=1d;
						totalEspWords++;
						break;
					// --------Surprise Words--------
					case "astonished_jj":
						vector[4]+=1d;
						totalEspWords++;
						break;
					case "wonder_nn":
						vector[5]+=1d;
						totalEspWords++;
						break;
					// --------Disgust Words--------
					case "yuchy_prp":
						vector[5]+=1d;
						totalEspWords++;
						break;
					case "nausea_nn":
						vector[5]+=1d;
						totalEspWords++;
						break;
					// --------Fear Words--------
					case "scared_vbn":
						vector[6]+=1d;
						totalEspWords++;
						break;
					case "panic_nn":
						vector[6]+=1d;
						totalEspWords++;
						break;
					// --------Relationship Nouns--------
					case "boyfriend_nn":
						vector[7]+=1d;
						totalEspWords++;
						break;
					case "date_nn":
						vector[7]+=1d;
						totalEspWords++;
						break;
					// --------Approach Words--------
					case "meet_vbp":
						vector[8]+=1d;
						totalEspWords++;
						break;
					case "car_nn":
						vector[8]+=1d;
						totalEspWords++;
						break;
					// --------Family Words--------
					case "mum_nn":
						vector[9]+=1d;
						totalEspWords++;
						break;
					case "dad_nn":
						vector[9]+=1d;
						totalEspWords++;
						break;
					// --------Communicative desensitization Words--------
					case "sex_nn":
						vector[10]+=1d;
						totalEspWords++;
						break;
					case "penis_nn":
						vector[10]+=1d;
						totalEspWords++;
						break;
					// --------Information Words--------
					case "asl_jj":
						vector[11]+=1d;
						totalEspWords++;
						break;
					case "home_nn":
						vector[11]+=1d;
						totalEspWords++;
						break;
					// --------Personal Pronouns--------
					case "I_prp":
						vector[12]+=1d;
						totalEspWords++;
						break;
					case "you_prp":
						vector[12]+=1d;
						totalEspWords++;
						break;
					// --------Reflexive Pronouns--------
					case "myself_prp":
						vector[13]+=1d;
						totalEspWords++;
						break;
					case "yourself_prp":
						vector[13]+=1d;
						totalEspWords++;
						break;
					// --------Obligation Verbs--------
					case "must_md":
						vector[14]+=1d;
						totalEspWords++;
						break;
					case "have_vb to_to":
						vector[14]+=1d;
						totalEspWords++;
						break;
					// --------Emoticons--------
					case "8)_jj":
						vector[15]+=1d;
						totalEspWords++;
						break;
					case ":(_nn":
						vector[15]+=1d;
						totalEspWords++;
						break;
					// --------Imperative Sentences--------
					case "do_vb it_prp":
						vector[16]+=1d;
						totalEspWords++;
						break;
					default:
						break;
				}

				sumA = sumA + swns.extract(words[j], "a");
				sumN = sumN + swns.extract(words[j], "n");
				sumV = sumV + swns.extract(words[j], "v");
				sumR = sumR + swns.extract(words[j], "r");
				counter++;
			}
		}
		
		for (int i = 1; i < vector.length; i++) {
			Double num = vector[i] / totalEspWords;
			//Validation
			if(num.isNaN() || num.isInfinite()){
				vector[i] = 0d;
			}
			else{
				vector[i] = num;
			}
		}
		
		avgA = sumA / counter;
		avgN = sumN / counter;
		avgV = sumV / counter;
		avgR = sumR / counter;
		avg = (avgA + avgN + avgV + avgR) / 4;

		vector[0] = avg;

		return vector;
	}
}
