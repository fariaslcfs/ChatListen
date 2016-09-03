public class Main {

	public static void main(String[] args) {
		TreinamentoRna treino = new TreinamentoRna();
		
		Double[][] treinamento = new Double[][] {{1d, 1d, 0d}, {0d, 0d, 0d}, {1d, 0d, 1d}, {0d, 1d, 1d}};
		Double[][] validacao = new Double[][] {{1d, 1d, 0d}, {0d, 1d, 1d}};
		
		treino.treinar(treinamento, validacao, 3, 10000, 0.1, 0.3);
		
		Rna rna = treino.getMelhorRna();
		
		System.out.println("XOR (0, 0) = " + rna.executar(new Double[]{0d, 0d}));
		System.out.println("XOR (0, 1) = " + rna.executar(new Double[]{0d, 1d}));
		System.out.println("XOR (1, 0) = " + rna.executar(new Double[]{1d, 0d}));
		System.out.println("XOR (1, 1) = " + rna.executar(new Double[]{1d, 1d}));

	}

}
