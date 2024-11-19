package br.com.greenvision.dao.indicacao;

import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.model.Indicacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IndicacaoDao {

    Indicacao save (Indicacao indicacao, Connection connection) throws SQLException, NotSavedException;

    List<Indicacao> readById(Long idUser) throws NotFoundException;
}
