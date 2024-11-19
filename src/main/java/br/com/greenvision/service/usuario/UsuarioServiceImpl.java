package br.com.greenvision.service.usuario;

import br.com.greenvision.config.DatabaseConnectionFactory;
import br.com.greenvision.dao.usuario.UsuarioDao;
import br.com.greenvision.dao.usuario.UsuarioDaoFactory;
import br.com.greenvision.dto.LoginDto;
import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.exceptions.UnsupportedServiceOperationException;
import br.com.greenvision.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioDao dao = UsuarioDaoFactory.create();


    @Override
    public Usuario create(Usuario usuario) throws UnsupportedServiceOperationException, SQLException, NotSavedException {
        if (usuario.getId() == null) {
            Connection connection = DatabaseConnectionFactory.create().get();
            try {
                usuario = this.dao.save(usuario, connection);
                connection.commit();
                return usuario;
            } catch (SQLException | NotSavedException e) {
                connection.rollback();
                throw e;
            }
        } else {
            throw new UnsupportedServiceOperationException();
        }
    }

    @Override
    public List<Usuario> findAll() {
        return this.dao.readAll();
    }

    @Override
    public Usuario findById(Long id) throws NotFoundException {
        return this.dao.readById(id);
    }

    @Override
    public Usuario update(Usuario usuario) throws NotFoundException, SQLException {
        Connection connection = DatabaseConnectionFactory.create().get();
        usuario = this.dao.update(usuario, connection);
        connection.commit();
        return usuario;
    }

    @Override
    public void deleteById(Long id) throws NotFoundException, SQLException {
        Connection connection = DatabaseConnectionFactory.create().get();
        this.dao.deleteById(id, connection);
        connection.commit();

    }

    @Override
    public LoginDto checkLogin(String login, String senha) throws NotFoundException {
        return dao.checkLogin(login, senha);
    }
}
