package br.com.greenvision.dao.indicacao;

public final class IndicacaoDaoFactory {
    private IndicacaoDaoFactory() {
    }

    public static IndicacaoDao create() {
        return new IndicacaoDaoImpl();
    }
}
