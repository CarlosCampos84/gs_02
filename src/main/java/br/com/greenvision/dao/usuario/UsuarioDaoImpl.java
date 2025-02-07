package br.com.greenvision.dao.usuario;

import br.com.greenvision.config.DatabaseConnectionFactory;
import br.com.greenvision.dto.LoginDto;
import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UsuarioDaoImpl implements UsuarioDao {

    private Connection connection;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public UsuarioDaoImpl() {
    }

    public UsuarioDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Usuario save(Usuario usuario, Connection connection) throws SQLException, NotSavedException {
        final String sql = "BEGIN INSERT INTO t_gvs_usuario (nome, nome_user, email, senha) VALUES (?, ?, ?, ?) RETURNING ID_USUARIO INTO ?; END;";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, usuario.getNomeCompleto());
        callableStatement.setString(2, usuario.getNomeUsuario());
        callableStatement.setString(3, usuario.getEmail());
        callableStatement.setString(4, usuario.getSenha());


        callableStatement.registerOutParameter(5, java.sql.Types.NUMERIC);
        callableStatement.execute();
        Long id = callableStatement.getLong(5);
        if (id == 0) {
            throw new NotSavedException();
        }
        usuario.setId(id);

        return usuario;
    }

    @Override
    public List<Usuario> readAll() {
        List<Usuario> result = new ArrayList<>();
        final String sql = "SELECT * FROM t_gvs_usuario";
        try (Connection connection = DatabaseConnectionFactory.create().get()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("ID_USUARIO");
                String nomeCompleto = rs.getString("NOME");
                String nomeUsuario = rs.getString("NOME_USER");
                String email = rs.getString("EMAIL");
                String senha = rs.getString("SENHA");
                result.add(new Usuario(id, nomeCompleto, nomeUsuario, email, senha));
            }
        } catch (SQLException e) {
            logger.warning("não foi possível localizar nenhum registro de usuario: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Usuario readById(Long id) throws NotFoundException {
        final String sql = "SELECT * FROM t_gvs_usuario WHERE ID_USUARIO = ?";
        try (Connection connection = DatabaseConnectionFactory.create().get();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nomeCompleto = rs.getString("NOME");
                    String nomeUsuario = rs.getString("NOME_USER");
                    String email = rs.getString("EMAIL");
                    String senha = rs.getString("SENHA");
                    return new Usuario(id, nomeCompleto, nomeUsuario, email, senha);
                } else {
                    throw new NotFoundException();
                }
            }
        } catch (SQLException e) {
            logger.warning("Erro ao localizar usuário: " + e.getMessage());
            throw new RuntimeException("Erro de banco de dados ao buscar usuário por ID.", e);
        }
    }

    @Override
    public Usuario update(Usuario usuario, Connection connection) throws NotFoundException, SQLException {
        final String sql = "UPDATE t_gvs_usuario SET NOME = ?, NOME_USER = ?, EMAIL = ?, SENHA = ? WHERE ID_USUARIO = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, usuario.getNomeCompleto());
        stmt.setString(2, usuario.getNomeUsuario());
        stmt.setString(3, usuario.getEmail());
        stmt.setString(4, usuario.getSenha());
        stmt.setLong(5, usuario.getId());
        int linhasAlteradas = stmt.executeUpdate();
        if (linhasAlteradas == 0) {
            throw new NotFoundException();
        }
        return usuario;
    }

    @Override
    public void deleteById(Long id, Connection connection) throws SQLException, NotFoundException {
        final String sql = "DELETE FROM t_gvs_usuario WHERE ID_USUARIO = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        int linhasAlteradas = stmt.executeUpdate();
        if (linhasAlteradas == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public LoginDto checkLogin(String login, String senha) throws NotFoundException {
        final String sql = "SELECT * FROM t_gvs_usuario WHERE nome_user = ? AND senha = ?";
        try (Connection connection = DatabaseConnectionFactory.create().get();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    LoginDto loginDto = new LoginDto();
                    loginDto.setIdUsuario(resultSet.getLong("ID_USUARIO"));
                    loginDto.setNomeUser(resultSet.getString("NOME_USER"));
                    return loginDto;
                } else {
                    throw new NotFoundException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar login", e);
        }
    }
}

