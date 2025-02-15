package br.com.greenvision.controller;

import br.com.greenvision.dto.IndicacaoDto;
import br.com.greenvision.exceptions.NotFoundException;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.exceptions.UnsupportedServiceOperationException;
import br.com.greenvision.model.Indicacao;
import br.com.greenvision.service.indicacao.IndicacaoService;
import br.com.greenvision.service.indicacao.IndicacaoServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;

@Path("/indicacao")
public class IndicacaoController {

    private final IndicacaoService service = IndicacaoServiceFactory.create();

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(IndicacaoDto input) throws UnsupportedServiceOperationException {
        if (input.getId() == null) {
            try {
                Indicacao indicacao = this.service.save(new Indicacao(null, input.getNomeIndicado(), input.getEmailIndicado(), input.getRelacao(), input.getDataIndicacao(), input.getIdUsuario()));
                return Response
                        .status(Response.Status.CREATED)
                        .entity(indicacao)
                        .build();
            } catch (SQLException | NotSavedException e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem","erro inesperado ao tentar salvar indicacao" + e)).build();
            }

        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(
                            Map.of(
                                    "mensagem",
                                    "esse método só permite a criação de novos usuarios"))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            return Response.status(Response.Status.OK)
                    .entity(this.service.readById(id)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
