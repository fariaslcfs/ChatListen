package br.fatecsjc.tests;

public class Main {	

	public static void main(String[] args) {
		TreinamentoRna treino = new TreinamentoRna();

		Double[][] treinamento = new Double[][] { { 1d, 1d, 0d }, { 0d, 0d, 0d }, { 1d, 0d, 1d }, { 0d, 1d, 1d } };
		Double[][] validacao = new Double[][] { { 1d, 1d, 0d }, { 0d, 1d, 1d } };

		treino.treinar(treinamento, validacao, 3, 100000, 0.2, 0.2);

		Rna rna = treino.getMelhorRna();
		
		// XOR --> comparador de desigualdade
		/*
		   in   out 
		  0  0   0
		  0  1   1
		  1  0   1
		  1  1   0
		  */
		
		// XNOR --> comparador de igualdade
		/*
		   in   out
		  0  0   1
		  0  1   0
		  1  0   0
		  1  1   1 
		  */
		System.out.println("XOR (0, 0) = " + Math.round(rna.executar(new Double[] { 0d, 0d })));
		System.out.println("XOR (0, 1) = " + Math.round(rna.executar(new Double[] { 0d, 1d })));
		System.out.println("XOR (1, 0) = " + Math.round(rna.executar(new Double[] { 1d, 0d })));
		System.out.println("XOR (1, 1) = " + Math.round(rna.executar(new Double[] { 1d, 1d })));
	}

}
