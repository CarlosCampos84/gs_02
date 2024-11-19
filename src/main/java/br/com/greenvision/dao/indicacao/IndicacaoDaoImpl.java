package br.com.greenvision.dao.indicacao;

import br.com.greenvision.config.DatabaseConnectionFactory;
import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.model.Indicacao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class IndicacaoDaoImpl implements IndicacaoDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private Connection connection;

    public IndicacaoDaoImpl() {
    }

    public IndicacaoDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Indicacao save(Indicacao indicacao, Connection connection) throws SQLException, NotSavedException {
        String sql = "BEGIN INSERT INTO t_gvs_indicacao (nome_indicado, email_indicado, relacao, data_indicacao, t_gvs_usuario_id) VALUES (?, ?, ?, ?, ?) RETURNING ID_INDICACAO INTO ?; END;";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, indicacao.getNomeIndicado());
        callableStatement.setString(2, indicacao.getEmailIndicado());
        callableStatement.setString(3, indicacao.getRelacao());
        callableStatement.setDate(4, java.sql.Date.valueOf(indicacao.getDataIndicacao()));
        callableStatement.setLong(5, indicacao.getIdUsuario());

        callableStatement.registerOutParameter(6, java.sql.Types.NUMERIC);
        callableStatement.execute();
        Long id = callableStatement.getLong(6);
        if (id == 0) {
            throw new NotSavedException();
        }
        indicacao.setId(id);
        return indicacao;
    }

    @Override
    public List<Indicacao> readById(Long idUser) {
        List<Indicacao> result = new ArrayList<>();
        String sql = "SELECT nome_indicado, email_indicado, relacao, data_indicacao " +
                "FROM t_gvs_indicacao " +
                "WHERE t_gvs_usuario_id = ? " +
                "ORDER BY data_indicacao DESC";

        try (Connection connection = DatabaseConnectionFactory.create().get();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idUser);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nomeIndicado = rs.getString("nome_indicado");
                    String emailIndicado = rs.getString("email_indicado");
                    String relacao = rs.getString("relacao");
                    LocalDate dataIndicacao = rs.getDate("data_indicacao").toLocalDate();
                    result.add(new Indicacao(nomeIndicado, emailIndicado, relacao, dataIndicacao, idUser));
                }
            }
        } catch (SQLException e) {
            logger.warning("Erro ao localizar registros de indicação: " + e.getMessage());
            throw new RuntimeException("Erro de banco de dados ao buscar indicações por ID de usuário.", e);
        }
        return result;
    }
}
