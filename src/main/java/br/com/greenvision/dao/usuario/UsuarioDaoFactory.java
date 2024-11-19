package br.com.greenvision.dao.usuario;

public final class UsuarioDaoFactory {
    private UsuarioDaoFactory() {
    }

    public static UsuarioDao create (){
        return new UsuarioDaoImpl();
    }
}
