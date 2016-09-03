public class Rna implements Cloneable {
    
    private Integer quantidadeEntradas = 0;
    private Integer quantidadeCamadaOculta = 0;
    private Double[] biasCamadaOculta = null;
    private Double[] deltaBiasCamadaOculta = null;
    private Double biasSaida = null;
    private Double deltaBiasSaida = null;
    private Boolean usarBias = true;
    private Double[][] pesosEntradaCamadaOculta = null;
    private Double[][] deltaPesosEntradaCamadaOculta = null;
    private Double[] pesosCamadaOcultaSaida = null;
    private Double[] deltaPesosCamadaOcultaSaida = null;
    private Double[] camadaOculta = null;
    private Double saida = null;
    private Double erro = 0.0;
    
    public Rna(Integer quantidadeEntradas, Integer quantidadeCamadaOculta, Boolean usarBias) {
        this.setQuantidadeEntradas(quantidadeEntradas);
        this.setQuantidadeCamadaOculta(quantidadeCamadaOculta);
        this.setUsarBias(usarBias);
        this.setCamadaOculta(new Double[quantidadeCamadaOculta]);
        inicializarBias();
        inicializarPesos();
    }
    
    /**
     * Inicializa bias da rede
     */
    private void inicializarBias() {
        // Aloca bias para camada oculta
        biasCamadaOculta = new Double[quantidadeCamadaOculta];
        // Aloca delta para bias da camada oculta
        deltaBiasCamadaOculta = new Double[quantidadeCamadaOculta];
        // Para cada elemento da camada oculta
        for(int i=0; i < quantidadeCamadaOculta; i++) {
            // Inicializa bias
            if(usarBias) {
                biasCamadaOculta[i] = gerarBias();
            }
            else {
                biasCamadaOculta[i] = 0.0;
            }
            // Inicializa delta do bias
            deltaBiasCamadaOculta[i] = 0.0;
        }
        // Inicializa bias da saida
        if(usarBias) {
            biasSaida = gerarBias();
        }
        else {
            biasSaida = 0.0;
        }
        // Inicializa delta do bias da saida
        deltaBiasSaida = 0.0;
        
    }
    
    /**
     * Gera um valor inicial de bias
     * @return Valor inicial de bias
     */
    private Double gerarBias() {
        return Math.random() * 0.5;
    }
    
    /**
     * Inicializa os pesos da rna
     */
    private void inicializarPesos() {
        // Aloca Matriz de pesos entrada x camada oculta
        pesosEntradaCamadaOculta = new Double[quantidadeEntradas][quantidadeCamadaOculta];
        // Aloca Matriz contendo o delta dos pesos entrada x camada oculta
        deltaPesosEntradaCamadaOculta = new Double[quantidadeEntradas][quantidadeCamadaOculta];
        // Para cada entrada
        for(int i = 0; i < quantidadeEntradas; i++) {
            // Para cada neuronio da camada oculta
            for(int j = 0; j < quantidadeCamadaOculta; j++) {
                // Inicializa peso da ligaca e o delta
                pesosEntradaCamadaOculta[i][j] = gerarPeso();
                deltaPesosEntradaCamadaOculta[i][j] = 0.0;
            }
        }
        // Aloca vetor de pesos camada oculta x saida
        pesosCamadaOcultaSaida = new Double[quantidadeCamadaOculta];
        // Aloca vetor contendo o delta dos pesos camada oculta x saida
        deltaPesosCamadaOcultaSaida = new Double[quantidadeCamadaOculta];
        // Para cada neuronio da camada oculta
        for(int i = 0; i < quantidadeCamadaOculta; i++) {
            // Inicializa o peso e o delta da ligacao entre ele e a saida
            pesosCamadaOcultaSaida[i] = gerarPeso();
            deltaPesosCamadaOcultaSaida[i] = 0.0;
        }
    }
    
    /**
     * Gera um peso inicial
     * @return
     */
    private Double gerarPeso() {
        return Math.random() - 0.5;
    }
    
    /**
     * Executa a rna para uma dada entrada
     * @param entradas Entradas para a rna
     * @return Saida da rna
     */
    public Double executar(Double[] entrada) {
        // Valida entradas
        if(entrada.length < quantidadeEntradas) {
            throw new RuntimeException("Quantidade de entradas inválida. Esperado " + quantidadeEntradas + " recebido " + entrada.length);
        }
        // Calcula a saida dos neuronios da camada oculta
        for(int i = 0; i < quantidadeCamadaOculta; i++) {
            // Inicializa com bias
            camadaOculta[i] = biasCamadaOculta[i];
            // Para cada entrada
            for(int j = 0; j < quantidadeEntradas; j++) {
                // Multiplica entrada pelo valor do peso da conexao que a liga ao neuronio
                camadaOculta[i] += entrada[j] * pesosEntradaCamadaOculta[j][i];
            }
            // Normaliza
            camadaOculta[i] = sigmoide(camadaOculta[i]);
        }
        // Calcula saida
        // Inicializa com bias
        saida = biasSaida;
        // Para cada neuronio da camada oculta
        for(int i = 0; i < quantidadeCamadaOculta; i++) {
            // Multiplica saida pelo peso da conexado que o liga a saida
            saida += camadaOculta[i] * pesosCamadaOcultaSaida[i];
        }
        // Normaliza
        saida = sigmoide(saida);
        return saida;
    }
    
    /**
     * Método que calcula a função sigmoid para um dado valor, limitando-o entre 0.0 e 1.0
     * @param valor Valor de entrada da função sigmoide
     * @return Resultado da aplicação da função sigmoide
     */
    public static Double sigmoide(Double valor) {
        return(1.0 / (1.0 + Math.exp(-valor)));
    }
    
    /**
     * Treina a rna
     * @param entradas Entradas
     * @param taxaAprendizagem Taxa de aprendizagem
     * @param momentum Momentum
     */
    public void treinar(Double[][] entradas, Double taxaAprendizagem, Double momentum) {
        // Para cada entrada
        for(Double[] entrada: entradas) {
            treinar(entrada, taxaAprendizagem, momentum);
        }
        
    }
    
    /**
     * Treina a rna
     * @param entrada Entrada
     * @param taxaAprendizagem Taxa de aprendizagem
     * @param momentum Momentum
     */
    public void treinar(Double[] entrada, Double taxaAprendizagem, Double momentum) {
        // Aloca erros
        Double[] errosCamadaOculta = new Double[quantidadeCamadaOculta];
        // Valida entrada
        if(entrada.length <= quantidadeEntradas) {
            throw new RuntimeException("Quantidade de entradas invalida. Esperado " + quantidadeEntradas + " recebido " + (entrada.length - 1));
        }
        // O ultimo valor de cada entrada e a saida esperada
        Double saidaEsperada = entrada[entrada.length - 1];
        // Executa a rede
        executar(entrada);
        // Seta o erro de saida
        erro = (saidaEsperada - saida) * saida * (1 - saida);
        // Inicializa o backpropagation
        if(usarBias) {
            // Calcula o novo delta do bias de saida           
            Double novoDeltaBiasSaida = taxaAprendizagem * erro  + momentum * deltaBiasSaida;
            // Atualiza bias da saida
            biasSaida +=  novoDeltaBiasSaida;
            // Atualiza delta do bias saida
            deltaBiasSaida = novoDeltaBiasSaida;
        }
        // Para cada neuronio da camada oculta
        for(int i = 0; i < quantidadeCamadaOculta; i++) {
            // Calcula o erro do neuronio da camada oculta
            errosCamadaOculta[i] = camadaOculta[i] * (1 - camadaOculta[i]) * erro * pesosCamadaOcultaSaida[i];
            // Se usa bias
            if(usarBias) {
                // Calcula o novo delta do bias da camada oculta
                Double novoDeltaBiasCamadaOculta = taxaAprendizagem * errosCamadaOculta[i] + momentum * deltaBiasCamadaOculta[i];
                // Atualiza o bias da camada oculta
                biasCamadaOculta[i] += novoDeltaBiasCamadaOculta;
                // Atualiza delta do bias da camada oculta
                deltaBiasCamadaOculta[i] = novoDeltaBiasCamadaOculta;
            }
            // Calcula o novo delta da conexao camada oculta x saida
            Double novoDeltaCamadaOcultaSaida = taxaAprendizagem * erro * camadaOculta[i] + deltaPesosCamadaOcultaSaida[i] * momentum;
            // Atualiza peso da conexao camada oculta x saida
            pesosCamadaOcultaSaida[i] += novoDeltaCamadaOcultaSaida;
            // Atualiza delta da conexao camada oculta x saida
            deltaPesosCamadaOcultaSaida[i] = novoDeltaCamadaOcultaSaida;
            // Para cada entrada
            for(int j = 0; j < quantidadeEntradas; j++) {
                // Calcula o novo delta da conexao entrada x camada oculta
                Double novoDeltaEntradaCamadaOculta = taxaAprendizagem * errosCamadaOculta[i] * entrada[j] + deltaPesosEntradaCamadaOculta[j][i] * momentum;
                // Atualiza peso da conexao camada  entrada x camada oculta
                pesosEntradaCamadaOculta[j][i] += novoDeltaEntradaCamadaOculta;
                // Atualiza delta da conexao camada  entrada x camada oculta
                deltaPesosEntradaCamadaOculta[j][i] = novoDeltaEntradaCamadaOculta;                    
            }
        }
    }
    
    /**
     * Recupera a configuracao atual da rna
     * @return Configuracao
     */
    public String getConfiguracao() {
        String espaco = " ";
        String configuracao = "Quantidade de entradas (i): " + quantidadeEntradas + " Quantidade de neuronios na camada oculta (j): "+ quantidadeCamadaOculta + espaco;
        configuracao += "Pesos entre Entrada e Camada Oculta: {";
        // Para cada entrada
        for(int i = 0; i < quantidadeEntradas; i++) {
            // Para cada conexao com a camada oculta
            for(int j = 0; j < quantidadeCamadaOculta; j++) {
                // Grava peso da conexao
                configuracao += "Peso[" + i + "," + j + "] = " + pesosEntradaCamadaOculta[i][j] + espaco;
            }
        }
        configuracao += "} Pesos entre Camada Oculta e Saída: {";
        // Para cada neuronio da camada oculta
        for(int i = 0; i < quantidadeCamadaOculta; i++) {
            // Grava peso da conexao com saida
            configuracao += "Peso[" + i + "] = " + pesosCamadaOcultaSaida[i] + espaco;
        }
        configuracao += "} Bias da Camada Oculta: {";
        // Para cada bias da camada oculta
        for(int i = 0; i < quantidadeCamadaOculta; i++) {
            // Grava bias da camada oculta
            configuracao += "Bias[" + i + "] = " + biasCamadaOculta[i] + espaco;
        }
        configuracao += "} Bias da Saida: {";
        // Grava bias da saida
        configuracao += "Bias = " + biasSaida + "}";
        return configuracao;
    }

    /**
     * @return the quantidadeEntradas
     */
    public Integer getQuantidadeEntradas() {
        return quantidadeEntradas;
    }

    /**
     * @param quantidadeEntradas the quantidadeEntradas to set
     */
    public void setQuantidadeEntradas(Integer quantidadeEntradas) {
        this.quantidadeEntradas = quantidadeEntradas;
    }

    /**
     * @return the quantidadeCamadaOculta
     */
    public Integer getQuantidadeCamadaOculta() {
        return quantidadeCamadaOculta;
    }

    /**
     * @param quantidadeCamadaOculta the quantidadeCamadaOculta to set
     */
    public void setQuantidadeCamadaOculta(Integer quantidadeCamadaOculta) {
        this.quantidadeCamadaOculta = quantidadeCamadaOculta;
    }

    /**
     * @return the biasCamadaOculta
     */
    public Double[] getBiasCamadaOculta() {
        return biasCamadaOculta;
    }

    /**
     * @param biasCamadaOculta the biasCamadaOculta to set
     */
    public void setBiasCamadaOculta(Double[] biasCamadaOculta) {
        this.biasCamadaOculta = biasCamadaOculta;
    }

    /**
     * @return the deltaBiasCamadaOculta
     */
    public Double[] getDeltaBiasCamadaOculta() {
        return deltaBiasCamadaOculta;
    }

    /**
     * @param deltaBiasCamadaOculta the deltaBiasCamadaOculta to set
     */
    public void setDeltaBiasCamadaOculta(Double[] deltaBiasCamadaOculta) {
        this.deltaBiasCamadaOculta = deltaBiasCamadaOculta;
    }

    /**
     * @return the biasSaida
     */
    public Double getBiasSaida() {
        return biasSaida;
    }

    /**
     * @param biasSaida the biasSaida to set
     */
    public void setBiasSaida(Double biasSaida) {
        this.biasSaida = biasSaida;
    }

    /**
     * @return the deltaBiasSaida
     */
    public Double getDeltaBiasSaida() {
        return deltaBiasSaida;
    }

    /**
     * @param deltaBiasSaida the deltaBiasSaida to set
     */
    public void setDeltaBiasSaida(Double deltaBiasSaida) {
        this.deltaBiasSaida = deltaBiasSaida;
    }

    /**
     * @return the usarBias
     */
    public Boolean getUsarBias() {
        return usarBias;
    }

    /**
     * @param usarBias the usarBias to set
     */
    public void setUsarBias(Boolean usarBias) {
        this.usarBias = usarBias;
    }

    /**
     * @return the pesosEntradaCamadaOculta
     */
    public Double[][] getPesosEntradaCamadaOculta() {
        return pesosEntradaCamadaOculta;
    }

    /**
     * @param pesosEntradaCamadaOculta the pesosEntradaCamadaOculta to set
     */
    public void setPesosEntradaCamadaOculta(Double[][] pesosEntradaCamadaOculta) {
        this.pesosEntradaCamadaOculta = pesosEntradaCamadaOculta;
    }

    /**
     * @return the deltaPesosEntradaCamadaOculta
     */
    public Double[][] getDeltaPesosEntradaCamadaOculta() {
        return deltaPesosEntradaCamadaOculta;
    }

    /**
     * @param deltaPesosEntradaCamadaOculta the deltaPesosEntradaCamadaOculta to set
     */
    public void setDeltaPesosEntradaCamadaOculta(
            Double[][] deltaPesosEntradaCamadaOculta) {
        this.deltaPesosEntradaCamadaOculta = deltaPesosEntradaCamadaOculta;
    }

    /**
     * @return the pesosCamadaOcultaSaida
     */
    public Double[] getPesosCamadaOcultaSaida() {
        return pesosCamadaOcultaSaida;
    }

    /**
     * @param pesosCamadaOcultaSaida the pesosCamadaOcultaSaida to set
     */
    public void setPesosCamadaOcultaSaida(Double[] pesosCamadaOcultaSaida) {
        this.pesosCamadaOcultaSaida = pesosCamadaOcultaSaida;
    }

    /**
     * @return the deltaPesosCamadaOcultaSaida
     */
    public Double[] getDeltaPesosCamadaOcultaSaida() {
        return deltaPesosCamadaOcultaSaida;
    }

    /**
     * @param deltaPesosCamadaOcultaSaida the deltaPesosCamadaOcultaSaida to set
     */
    public void setDeltaPesosCamadaOcultaSaida(
            Double[] deltaPesosCamadaOcultaSaida) {
        this.deltaPesosCamadaOcultaSaida = deltaPesosCamadaOcultaSaida;
    }

    /**
     * @return the camadaOculta
     */
    public Double[] getCamadaOculta() {
        return camadaOculta;
    }

    /**
     * @param camadaOculta the camadaOculta to set
     */
    public void setCamadaOculta(Double[] camadaOculta) {
        this.camadaOculta = camadaOculta;
    }

    /**
     * @return the saida
     */
    public Double getSaida() {
        return saida;
    }

    /**
     * @param saida the saida to set
     */
    public void setSaida(Double saida) {
        this.saida = saida;
    }

    /**
     * @return the erro
     */
    public Double getErro() {
        return erro;
    }

    /**
     * @param erro the erro to set
     */
    public void setErro(Double erro) {
        this.erro = erro;
    }

    /**
     * Clona a rna
     */
    public Rna clone() {
        // Instancia rna
        Rna rna = new Rna(quantidadeEntradas, quantidadeCamadaOculta, usarBias);
        // Copia pesos entrada x camada oculta
        Double[][] pesosEntradaCamadaOcultaCopia = rna.getPesosEntradaCamadaOculta();
        for(int i = 0; i < quantidadeEntradas; i++) {
            for(int j = 0; j < quantidadeCamadaOculta; j++) {
                pesosEntradaCamadaOcultaCopia[i][j] = pesosEntradaCamadaOculta[i][j];
            }
        }
        // Copia pesos camada oculta x saida e bias da camada oculta
        Double[] pesosCamadaOcultaSaidaCopia = rna.getPesosCamadaOcultaSaida();
        Double[] biasCamadaOcultaCopia = rna.getBiasCamadaOculta();
        Double[] deltaBiasCamadaOcultaCopia = rna.getDeltaBiasCamadaOculta();
        for(int i = 0; i < quantidadeCamadaOculta; i++) {
            pesosCamadaOcultaSaidaCopia[i] = pesosCamadaOcultaSaida[i];
            biasCamadaOcultaCopia[i] = biasCamadaOculta[i];
            deltaBiasCamadaOcultaCopia[i] = deltaBiasCamadaOculta[i];
        }
        // Copia bias da saida
        rna.setBiasSaida(biasSaida);
        rna.setDeltaBiasSaida(deltaBiasSaida);
        return rna;
    }

}
