package controller;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entity.Contato;
import http.ContatoHttp;
import repository.ContatoRepository;

@Path("/contato")
public class ContatoController {

	private final ContatoRepository repository = new ContatoRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(ContatoHttp contatoHttp) {

		Contato contato = new Contato();

		try {

			contato.setDescricao(contatoHttp.getDescricao());
			contato.setTelefone(contatoHttp.getTelefone());

			repository.Salvar(contato);

			return "Registro cadastrado com sucesso!";

		} catch (Exception e) {

			return "Erro ao cadastrar um registro " + e.getMessage();

		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String Alterar(ContatoHttp contatoHttp) {

		Contato contato = new Contato();

		try {

			ContatoHttp contatoOld = this.selecionarContatoPorId(contatoHttp.getId());
			if(contatoOld != null) {
				
				contato.setId(contatoOld.getId());
				contato.setDescricao(contatoHttp.getDescricao());
				contato.setTelefone(contatoHttp.getTelefone());

				repository.Alterar(contato);

				return "Registro alterado com sucesso!";
			}else {
				return "Erro ao alterar o registro";
			}


		} catch (Exception e) {

			return "Erro ao alterar o registro " + e.getMessage();

		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<ContatoHttp> selecionarTodos() {

		List<ContatoHttp> contatosHttp = new ArrayList<ContatoHttp>();

		List<Contato> contatos = repository.selecionarTodos();

		for (Contato contato : contatos) {

			contatosHttp.add(new ContatoHttp(contato.getId(), contato.getDescricao(), contato.getTelefone()));
		}

		return contatosHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public ContatoHttp selecionarContatoPorId(@PathParam("id") Long id) {

		Contato contato = repository.selecionarPorId(id);

		if (contato != null) {
			return new ContatoHttp(contato.getId(), contato.getDescricao(), contato.getTelefone());
		}
		return null;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String Excluir(@PathParam("id") Long id) {

		try {

			repository.Excluir(id);

			return "Registro excluido com sucesso!";

		} catch (Exception e) {

			return "Erro ao excluir o registro! " + e.getMessage();
		}

	}

}
