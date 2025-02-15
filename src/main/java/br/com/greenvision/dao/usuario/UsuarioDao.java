package br.com.greenvision.dao.usuario;

import br.com.greenvision.dto.LoginDto;
import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDao {
    Usuario save(Usuario usuario, Connection connection) throws SQLException, NotSavedException;

    List<Usuario> readAll();

    Usuario readById(Long id) throws NotFoundException;

    Usuario update(Usuario usuario, Connection connection) throws NotFoundException, SQLException;

    void deleteById(Long id, Connection connection) throws SQLException, NotFoundException;

    LoginDto checkLogin(String login, String senha) throws NotFoundException;
}
