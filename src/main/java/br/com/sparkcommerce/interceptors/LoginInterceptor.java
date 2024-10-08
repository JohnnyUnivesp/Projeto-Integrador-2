package br.com.sparkcommerce.interceptors;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.olimposistema.aipa.service.Util;
import br.com.sparkcommerce.controller.LoginController;
import br.com.sparkcommerce.model.Usuario;

@Intercepts
@AcceptsWithAnnotations(SomenteLogado.class)
public class LoginInterceptor {

	
	@Inject HttpSession session;
	@Inject Result result;
	
	
	@AroundCall
	public void continuaSeOUsuarioEstaLogado(SimpleInterceptorStack stack) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(Util.isNull(usuario)) result.redirectTo(LoginController.class).login();
		else stack.next();
		
	}
}
