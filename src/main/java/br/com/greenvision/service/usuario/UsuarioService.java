package br.com.greenvision.service.usuario;

import br.com.greenvision.dto.LoginDto;
import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.exceptions.UnsupportedServiceOperationException;
import br.com.greenvision.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioService {

    Usuario create(Usuario usuario) throws UnsupportedServiceOperationException, SQLException, NotSavedException;

    List<Usuario> findAll();

    Usuario findById(Long id) throws NotFoundException;

    Usuario update(Usuario usuario) throws NotFoundException, SQLException;

    void deleteById(Long id) throws NotFoundException, SQLException;

    LoginDto checkLogin(String login, String senha) throws NotFoundException;
}
