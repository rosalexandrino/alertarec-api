package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.PontoDeApoio;
import http.PontoDeApoioHttp;
import repository.PontoDeApoioRepository;
import repository.TipoPontoDeApoioRepository;
import repository.UsuarioRepository;

@Path("/pontoDeApoio")
public class PontoDeApoioController {
	
	private final PontoDeApoioRepository repository = new PontoDeApoioRepository ();

	private final TipoPontoDeApoioRepository tipoDeApoioRepository = new TipoPontoDeApoioRepository();
	
	private final UsuarioRepository usuarioRepository = new UsuarioRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(PontoDeApoioHttp pontoHttp) {
		
		String message = "";
		PontoDeApoio ponto = new PontoDeApoio();

		try {

			ponto.setLatitude(pontoHttp.getLatitude());
			ponto.setLongitude(pontoHttp.getLongitude());
			ponto.setTipoDeApoio(tipoDeApoioRepository.selecionarPorId(pontoHttp.getTipoDeApoio()));
			ponto.setUsuario(usuarioRepository.selecionarPorEmail(pontoHttp.getUsuarioEmail()));

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<PontoDeApoio>> violations = validator.validate(ponto);
			for (ConstraintViolation<PontoDeApoio> violation : violations) {
			    message += violation.getMessage() + "\n"; 
			}
			if(message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
			}
			
			repository.Salvar(ponto);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso" ).build();			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro: " + e.getMessage() ).build();
		}	
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Alterar(PontoDeApoioHttp pontoHttp) {
		
		String message = "";
		PontoDeApoio ponto = new PontoDeApoio();

		try {
			
			PontoDeApoioHttp pontoOld = this.selecionarPontoDeApoioPorId(pontoHttp.getId());
			if(pontoOld != null) {
				
				ponto.setId(pontoOld.getId());
				ponto.setLatitude(pontoHttp.getLatitude());
				ponto.setLongitude(pontoHttp.getLongitude());
				ponto.setTipoDeApoio(tipoDeApoioRepository.selecionarPorId(pontoHttp.getTipoDeApoio()));
				ponto.setUsuario(usuarioRepository.selecionarPorEmail(pontoOld.getUsuarioEmail()));
				ponto.setDataCriacao(pontoOld.getDataCriacao());

				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<PontoDeApoio>> violations = validator.validate(ponto);
				for (ConstraintViolation<PontoDeApoio> violation : violations) {
				    message += violation.getMessage() + "\n"; 
				}
				if(message != "") {
					return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
				}
				repository.Alterar(ponto);
				return Response.status(Response.Status.OK).entity("Registro alterado com sucesso" ).build();
			}else {
				return Response.status(Response.Status.NOT_FOUND).entity("Erro ao alterar o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao alterar o registro: \n" + e.getMessage()).build();
		}	
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<PontoDeApoioHttp> selecionarTodos() {

		List<PontoDeApoioHttp> pontosHttp = new ArrayList<PontoDeApoioHttp>();
		List<PontoDeApoio> pontos = repository.selecionarTodos();
		for (PontoDeApoio ponto : pontos) {
			pontosHttp.add(new PontoDeApoioHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), 
					ponto.getTipoDeApoio().getId(), ponto.getUsuario().getEmail(), ponto.getDataCriacao(), ponto.getDataAtualizacao()));
		}
		return pontosHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/usuario/{usuario}")
	public List<PontoDeApoioHttp> selecionarPorUsuario(@PathParam("usuario") String usuarioEmail) {

		List<PontoDeApoioHttp> pontosHttp = new ArrayList<PontoDeApoioHttp>();
		List<PontoDeApoio> pontos = repository.selecionarPorUsuario(usuarioEmail);
		for (PontoDeApoio ponto : pontos) {
			pontosHttp.add(new PontoDeApoioHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeApoio().getId(),
					ponto.getUsuario().getEmail(), ponto.getDataCriacao(), ponto.getDataAtualizacao()));
		}
		return pontosHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tipo/{tipo}")
	public List<PontoDeApoioHttp> selecionarPorTipo(@PathParam("tipo") Long tipo) {

		List<PontoDeApoioHttp> pontosHttp = new ArrayList<PontoDeApoioHttp>();
		List<PontoDeApoio> pontos = repository.selecionarPorTipo(tipo);
		for (PontoDeApoio ponto : pontos) {
			pontosHttp.add(new PontoDeApoioHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeApoio().getId(),
					ponto.getUsuario().getEmail(), ponto.getDataCriacao(), ponto.getDataAtualizacao()));
		}
		return pontosHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public PontoDeApoioHttp selecionarPontoDeApoioPorId(@PathParam("id") Long id) {

		PontoDeApoio ponto = repository.selecionarPorId(id);
		if (ponto != null) {
			return new PontoDeApoioHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeApoio().getId(),
					ponto.getUsuario().getEmail(), ponto.getDataCriacao(), ponto.getDataAtualizacao());
		}
		return null;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			PontoDeApoio ponto = repository.selecionarPorId(id);
			if(ponto != null) {
				repository.Excluir(ponto.getId());
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso" ).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}
}