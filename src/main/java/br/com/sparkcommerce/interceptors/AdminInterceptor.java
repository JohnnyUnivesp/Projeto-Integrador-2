package br.com.sparkcommerce.interceptors;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.sparkcommerce.controller.LoginController;
import br.com.sparkcommerce.model.Usuario;

@Intercepts
@AcceptsWithAnnotations(SomenteAdmin.class)
public class AdminInterceptor {

    @Inject HttpSession session;
    @Inject Result result;

    @AroundCall
    public void continuaSeOUsuarioEhAdmin(SimpleInterceptorStack stack) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if(usuario == null || !"ADMIN".equals(usuario.getTipoUsuario())) {
            result.redirectTo(LoginController.class).login();
        } else {
            stack.next(); // Se for admin, continua a execução.
        }
    }
}