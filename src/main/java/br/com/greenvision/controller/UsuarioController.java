package br.com.greenvision.controller;

import br.com.greenvision.dto.LoginDto;
import br.com.greenvision.dto.LoginInfos;
import br.com.greenvision.dto.UsuarioDto;
import br.com.greenvision.exceptions.NotSavedException;
import br.com.greenvision.exceptions.UnsupportedServiceOperationException;
import br.com.greenvision.model.Usuario;
import br.com.greenvision.service.usuario.UsuarioService;
import br.com.greenvision.service.usuario.UsuarioServiceFactory;
import br.com.greenvision.exceptions.NotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;


@Path("/usuario")
public class UsuarioController {

    private final UsuarioService service = UsuarioServiceFactory.create();

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(UsuarioDto input) throws UnsupportedServiceOperationException {
        if (input.getId() == null) {
            try {
                Usuario usuario = this.service.create(new Usuario(null, input.getNomeCompleto(), input.getNomeUsuario(), input.getEmail(), input.getSenha()));
                return Response
                        .status(Response.Status.CREATED)
                        .entity(usuario)
                        .build();
            } catch (SQLException | NotSavedException e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem","erro inesperado ao tentar salvar pessoa" + e)).build();
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
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.status(Response.Status.OK)
                .entity(this.service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            return Response.status(Response.Status.OK)
                    .entity(this.service.findById(id)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UsuarioDto input){
        try {
            Usuario updated = this.service.update(new Usuario(id, input.getNomeCompleto(), input.getNomeUsuario(), input.getEmail(), input.getSenha()));
            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem","erro inesperado ao tentar atualizar pessoa")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id")Long id){
        try {
            this.service.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem","erro inesperado ao tentar deletar pessoa")).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginInfos loginInfos) {

        LoginDto usuario = null;
        try {
            usuario = service.checkLogin(loginInfos.getLogin(), loginInfos.getSenha());
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }

        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciais inválidas").build();
        }
    }
}
