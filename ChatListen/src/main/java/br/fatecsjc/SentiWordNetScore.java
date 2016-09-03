/**
 * @author borrowed and modified by Luiz Carlos Farias da Silva
 * from Petter Turnberg 2013 pettert@chalmers.se
 * 
*/

package br.fatecsjc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SentiWordNetScore {

	private Map<String, Double> dicionario;

	public SentiWordNetScore(String pathToSWN) throws IOException {
		// REPRESENTAÇÃO PRINCIPAL DO DICIONÁRIO
		dicionario = new HashMap<String, Double>();

		// PARA CADA TERMO, UM Nº DE SEQ E UM SCORE
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader fileSWN= null;
		try {
			fileSWN = new BufferedReader(new FileReader(pathToSWN));
			int lineNumber = 0;

			String line;
			while ((line = fileSWN.readLine()) != null) {
				lineNumber++;

				
				if (!line.trim().startsWith("#")) {// PULA COMENTÁRIOS (#) COM SEPARAÇÃO USANDO tab \t
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					// LINHA DO SWN
					// tipoPalavra      id posição   PosS   NegS            termo Synset#sensenumber                          Desc
					//     a             00009618     0.5   0.25   spartan#4 austere#3 ascetical#2 ascetic#2     practicing great self-denial;...etc
					
					// VERIFICA SE A LINHA É VÁLIDA 
					if (data.length != 6) {
						throw new IllegalArgumentException("TABULAÇÃO ERRADA NO ARQUIVO, LINHA: " + lineNumber);
					}

					// CALCULA O SCORE ASSIM: SYNSET(SET DE SINÔNIMOS) synset = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);

					// PEGA TODOS OS TERMOS
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) {
						// PEGA O TERMO E O RANK DO TERMO
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#" + wordTypeMarker;

						int synTermRank = Integer.parseInt(synTermAndRank[1]);
						
						// TEM-SE AQUI  SCORE OS SYNSET#1, SYNSET#2, SYNSET#3 ...
						// SE O TERMO NÃO EXISTIR ADICIONA-O AO MAPA TEMPORÁRIO
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm, new HashMap<Integer, Double>());
						}

						// ADICIONA O LINK DO SYNSET AO TERMO
						tempDictionary.get(synTerm).put(synTermRank, synsetScore);
					}
				}
			}

			// VARRE TODOS OS TERMOS
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary.entrySet()) {
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// CALCULA A MÉDIA PONDERA. PESO DOS TERMOS DE ACORDO COM SEUS RANKS.
				// score = (1/2 * first)  + (1/3*second) + (1/4*third) + ... + (1/n*(n-1))
				// soma = 1/1 + 1/2 + 1/3 
				double score = 0.0;
				double soma= 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap.entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					soma += 1.0 / (double) setScore.getKey();
				}
				score = score / soma;
				
				dicionario.put(word, score);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileSWN != null) {
				fileSWN.close();
			}
		}
	}

	public double extract(String word, String pos) {
		try {
			return dicionario.get(word + "#" + pos);
		} catch (Exception e) {
			return 0;
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("Uso: java SentiWordNetScore <pathToSentiWordNetFile>");
			return;
		}
		String pathToSWN = args[0];
		
		SentiWordNetScore sentiwordnet = new SentiWordNetScore(pathToSWN);

		System.out.println("hello#a " + sentiwordnet.extract("car", "a"));
		System.out.println("there#a " + sentiwordnet.extract("bad", "a"));
		System.out.println("blue#a " + sentiwordnet.extract("blue", "a"));
		System.out.println("blue#n " + sentiwordnet.extract("manny", "n"));
		
/*		Tagger tagger = new Tagger();*/
	}
	
	


}