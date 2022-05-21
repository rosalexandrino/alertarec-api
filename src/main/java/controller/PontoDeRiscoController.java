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

import entity.PontoDeRisco;
import http.PontoDeRiscoHttp;
import repository.PontoDeRiscoRepository;
import repository.TipoPontoDeRiscoRepository;
import repository.UsuarioRepository;

@Path("/pontoDeRisco")
public class PontoDeRiscoController {
	
	private final PontoDeRiscoRepository repository = new PontoDeRiscoRepository ();

	private final TipoPontoDeRiscoRepository tipoDeRiscoRepository = new TipoPontoDeRiscoRepository();
	
	private final UsuarioRepository usuarioRepository = new UsuarioRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(PontoDeRiscoHttp pontoHttp) {
		
		String message = "";
		PontoDeRisco ponto = new PontoDeRisco();

		try {

			ponto.setLatitude(pontoHttp.getLatitude());
			ponto.setLongitude(pontoHttp.getLongitude());
			ponto.setTipoDeRisco(tipoDeRiscoRepository.selecionarPorId(pontoHttp.getTipoDeRisco()));
			ponto.setUsuario(usuarioRepository.selecionarPorEmail(pontoHttp.getUsuarioEmail()));

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<PontoDeRisco>> violations = validator.validate(ponto);
			for (ConstraintViolation<PontoDeRisco> violation : violations) {
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
	public Response Alterar(PontoDeRiscoHttp pontoHttp) {
		
		String message = "";
		PontoDeRisco ponto = new PontoDeRisco();

		try {
			
			PontoDeRisco pontoOld = repository.selecionarPorId(pontoHttp.getId());
			if(pontoOld != null) {
				
				ponto.setId(pontoOld.getId());
				ponto.setLatitude(pontoHttp.getLatitude());
				ponto.setLongitude(pontoHttp.getLongitude());
				ponto.setTipoDeRisco(tipoDeRiscoRepository.selecionarPorId(pontoHttp.getTipoDeRisco()));
				ponto.setUsuario(usuarioRepository.selecionarPorEmail(pontoOld.getUsuario().getEmail()));
				ponto.setDataCriacao(pontoOld.getDataCriacao());

				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<PontoDeRisco>> violations = validator.validate(ponto);
				for (ConstraintViolation<PontoDeRisco> violation : violations) {
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
	public List<PontoDeRiscoHttp> selecionarTodos() {

		List<PontoDeRiscoHttp> pontosHttp = new ArrayList<PontoDeRiscoHttp>();
		List<PontoDeRisco> pontos = repository.selecionarTodos();
		for (PontoDeRisco ponto : pontos) {
			pontosHttp.add(new PontoDeRiscoHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeRisco().getId(),
					ponto.getUsuario().getEmail()));
		}
		return pontosHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/usuario/{usuario}")
	public List<PontoDeRiscoHttp> selecionarPorUsuario(@PathParam("usuario") String usuarioEmail) {

		List<PontoDeRiscoHttp> pontosHttp = new ArrayList<PontoDeRiscoHttp>();
		List<PontoDeRisco> pontos = repository.selecionarPorUsuario(usuarioEmail);
		for (PontoDeRisco ponto : pontos) {
			pontosHttp.add(new PontoDeRiscoHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeRisco().getId(),
					ponto.getUsuario().getEmail()));
		}
		return pontosHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tipo/{tipo}")
	public List<PontoDeRiscoHttp> selecionarPorTipo(@PathParam("tipo") Long tipo) {

		List<PontoDeRiscoHttp> pontosHttp = new ArrayList<PontoDeRiscoHttp>();
		List<PontoDeRisco> pontos = repository.selecionarPorTipo(tipo);
		for (PontoDeRisco ponto : pontos) {
			pontosHttp.add(new PontoDeRiscoHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeRisco().getId(),
					ponto.getUsuario().getEmail()));
		}
		return pontosHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public PontoDeRiscoHttp selecionarPontoDeRiscoPorId(@PathParam("id") Long id) {

		PontoDeRisco ponto = repository.selecionarPorId(id);
		if (ponto != null) {
			return new PontoDeRiscoHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeRisco().getId(),
					ponto.getUsuario().getEmail());
		}
		return null;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			PontoDeRisco ponto = repository.selecionarPorId(id);
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
