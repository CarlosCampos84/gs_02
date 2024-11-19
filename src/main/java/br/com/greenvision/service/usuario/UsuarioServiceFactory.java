package br.com.greenvision.service.usuario;

public class UsuarioServiceFactory {
    private UsuarioServiceFactory() {
    }

    public static UsuarioService create() {
        return new UsuarioServiceImpl();
    }

}
