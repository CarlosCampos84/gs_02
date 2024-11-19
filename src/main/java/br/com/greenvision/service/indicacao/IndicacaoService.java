package br.com.greenvision.service.indicacao;

import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.exceptions.UnsupportedServiceOperationException;
import br.com.greenvision.model.Indicacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IndicacaoService {
    Indicacao save (Indicacao indicacao) throws UnsupportedServiceOperationException, SQLException, NotSavedException;

    List<Indicacao> readById(Long idUser) throws NotFoundException;

}
