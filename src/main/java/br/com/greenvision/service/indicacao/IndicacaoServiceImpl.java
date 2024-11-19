package br.com.greenvision.service.indicacao;

import br.com.greenvision.config.DatabaseConnectionFactory;
import br.com.greenvision.dao.indicacao.IndicacaoDao;
import br.com.greenvision.dao.indicacao.IndicacaoDaoFactory;
import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.exceptions.UnsupportedServiceOperationException;
import br.com.greenvision.model.Indicacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class IndicacaoServiceImpl implements IndicacaoService{

    private final IndicacaoDao dao = IndicacaoDaoFactory.create();


    @Override
    public Indicacao save(Indicacao indicacao) throws UnsupportedServiceOperationException, SQLException, NotSavedException {
        if (indicacao.getId() == null) {
            Connection connection = DatabaseConnectionFactory.create().get();
            try {
                indicacao = this.dao.save(indicacao, connection);
                connection.commit();
                return indicacao;
            } catch (SQLException | NotSavedException e) {
                connection.rollback();
                throw e;
            }
        } else {
            throw new UnsupportedServiceOperationException();
        }
    }

    @Override
    public List<Indicacao> readById(Long idUser) throws NotFoundException {
        return this.dao.readById(idUser);
    }
}
