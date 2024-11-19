package br.com.greenvision.service.indicacao;

import br.com.greenvision.service.usuario.UsuarioService;

public final class IndicacaoServiceFactory {

    private IndicacaoServiceFactory() {
    }

    public static IndicacaoService create(){
        return new IndicacaoServiceImpl();
    }
}
