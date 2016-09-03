package br.fatecsjc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains methods for file to file Part-Of-Speech tagging
 * Contains one method that receives one file and then applies
 * tags to it and returns it modified
 */

public class PostTagger {
	// Aplicação de tags - POSTag classification
	public File tagging(File file, String pathToSave) throws IOException {
		File fileOut = new File(pathToSave + "/FilePOStag_" + file.getName());
		POSModel model = new POSModelLoader().load(new File("Models/en-pos-maxent.bin"));
		POSTaggerME tagger = new POSTaggerME(model);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut));
		String line, tags[];
		while ((line = br.readLine()) != null) {
			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
			tags = tagger.tag(whitespaceTokenizerLine);
			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			bw.write(sample.toString().toLowerCase() + "\n");
		}
		bw.close();
		return fileOut;
	}

	@SuppressWarnings("deprecation")
	public String taggingStr(String str) {

		POSModel model = new POSModelLoader().load(new File("Models/en-pos-maxent.bin"));
		//PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(model);
		//perfMon.start();
		String tag = tagger.tag(str);
		//perfMon.incrementCounter();
		//perfMon.stopAndPrintFinalResult();
		return tag;
	}
}
