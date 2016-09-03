package br.fatecsjc;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains methods for RNA trainning
 * Class created and lended by Emanuel Mineda Carneiro
 */

public class TrainnerNN {
    
    private Neural melhorRna;
    private Neural neural;
    private boolean dadosAleatorios = true;
    
    public void treinar(Double[][] conjuntoTreinamento, Double[][] conjuntoValidacao, Integer quantidadeNeuroniosCamadaOculta, Integer epocas, Double taxaAprendizagem, Double momentum) {
        
        Double[] primeiraEntrada = conjuntoTreinamento[0];
        Integer quantidadeEntradas = primeiraEntrada.length - 1;
       	neural = new Neural(quantidadeEntradas, quantidadeNeuroniosCamadaOculta, true);
        // Instancia uma nova MLP com os melhores pesos
        melhorRna = neural.clone();
        // Inicializa as variáveis de controle de erro
        Double menorErroValidacao = 1.0d;
        // Treina a rede de acordo com as iterações definidas
        for(Integer iteracao = 0; iteracao < epocas; iteracao++) {
            // Taxa de treinamento e momentum configurados
            if(dadosAleatorios) {
                randomize(conjuntoTreinamento);
            }
            neural.treinar(conjuntoTreinamento, taxaAprendizagem, momentum);
            // Se o erro quadrático resultante da aplicação da rede no conjunto de validação
            // é inferior ao menor erro já registrado
            Double erroValidacao = calcularErroQuadraticoTotal(neural, conjuntoValidacao) / conjuntoValidacao.length;
            if(erroValidacao < menorErroValidacao) {
                // Este passa a ser o menor erro já registrado
                menorErroValidacao = new Double(erroValidacao);
                // Copia pesos para a MLP com melhor resultado
                melhorRna = neural.clone();
            }
        }
    }
    
    /**
     * Altera a ordem dos valores do vetor aleatoriamente
     * @param values Vetor com valores
     */
    private void randomize(Double[][] values) {
        Double[] line;
        for(int i = 0; i < values.length; i++) {
            line = values[i];
            Integer changeIndex = (int)Math.random() * values.length;
            values[i] = values[changeIndex];
            values[changeIndex] = line;
        }
    }
    
    /**
     * Método que calcula o erro quadratico total
     * @param neural MLP utilizada
     * @param set Conjunto de dados
     * @return Erro quadratico total
     * @throws Exception Exception
     */
    private Double calcularErroQuadraticoTotal(Neural neural, Double[][] set) {
        try {
            // Se o conjunto enviado não possui membros
            if(set.length == 0) {
                // Retorna 0
                return 0.0;
            }
            Double error = 0.0;
            // Calcula o erro quadrático para o conjunto de validação
            for(Integer index=0; index<set.length; index++) {
                // Executa a rede para cada entrada
                neural.executar(set[index]);
                // compara o valor esperado com o valor real
                Double expected = set[index][set[index].length - 1];
                Double real = neural.getSaida();
                // Calcula o erro quadrático e acumula
                error += (expected-real)*(expected-real);
            }
            // Divide o erro pelo total de entradas
            return error;
        }
        catch(Throwable e) {
            return 1d;
        }
    }
    
    /**
     * @return the melhorRna
     */
    public Neural getMelhorRna() {
        return melhorRna;
    }

    /**
     * @param melhorRna the melhorRna to set
     */
    public void setMelhorRna(Neural melhorRna) {
        this.melhorRna = melhorRna;
    }

    /**
	 * @return the dadosAleatorios
	 */
	public boolean isDadosAleatorios() {
		return dadosAleatorios;
	}

	/**
	 * @param dadosAleatorios the dadosAleatorios to set
	 */
	public void setDadosAleatorios(boolean dadosAleatorios) {
		this.dadosAleatorios = dadosAleatorios;
	}

	/**
	 * @return the neural
	 */
	public Neural getRna() {
		return neural;
	}

	/**
	 * @param neural the neural to set
	 */
	public void setRna(Neural neural) {
		this.neural = neural;
	}
}
