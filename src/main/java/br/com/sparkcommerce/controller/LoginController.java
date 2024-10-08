package br.com.sparkcommerce.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.sparkcommerce.dao.UsuarioDAO;
import br.com.sparkcommerce.model.Usuario;

@Controller
@Path("login")
public class LoginController {
	
	@Inject Result result;
	@Inject Validator validator;
	@Inject UsuarioDAO usuarioDao;
	@Inject HttpSession session;

	@Get("")
	public void login( ) {
			
	}
	
	@IncludeParameters
	@Post("autenticar")
	public void autenticar(
			@NotEmpty String email,
			@NotEmpty @Size(min = 6, max = 20, message = "{usuario.senha.size}") String senha) {
		
		validator.onErrorRedirectTo(this).login();
		
		Usuario usuario = usuarioDao.exiteUsuarioCom(email, senha);
		
		validator.addIf(usuario == null, new SimpleMessage("erro", "Email ou Senha invalidos!"));
		validator.onErrorRedirectTo(this).login();
		
		session.setAttribute("usuarioLogado", usuario);
		
		result.permanentlyRedirectTo(ProdutosController.class).produtos(null);
		
	}
		
}
