package br.com.sparkcommerce.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.sparkcommerce.dao.UsuarioDAO;
import br.com.sparkcommerce.model.Usuario;
import br.com.sparkcommerce.interceptors.SomenteAdmin;

@Controller
@Path("usuarios") 
public class AdminController {

    @Inject private UsuarioDAO usuarioDao;
    @Inject private Result result;
    @Inject HttpSession session;

    @Get("") @SomenteAdmin
    public void listarUsuarios(int page, String filtro) {
        if (page == 0) {page = 1;}
        int limite = 20;
        List<Usuario> usuarios;
        int totalUsuarios;

        if(filtro != null && !filtro.isEmpty()) {
            usuarios = usuarioDao.buscarPorNomeOuEmail(filtro, page, limite);
            totalUsuarios = usuarioDao.totalUsuariosComFiltro(filtro);
        } else {
            usuarios = usuarioDao.buscarTodos(page, limite);
            totalUsuarios = usuarioDao.totalUsuarios();
        }
        result.include("usuarios", usuarios);
        result.include("paginaAtual", page);
        result.include("totalPaginas", (int) Math.ceil((double) totalUsuarios / limite));
        result.include("filtro", filtro);
    }

    @Post("editarNivel")
    public void editarNivel(int usuarioId, String nivel, int paginaAtual) {
        // Criar um objeto Usuario temporário com o ID
        Usuario usuarioTemp = new Usuario();
        usuarioTemp.setId(usuarioId);
    	
    	// Busca o usuário do banco de dados
        Usuario usuarioDoBanco = usuarioDao.selectPorId(usuarioId);

        // Impede a alteração do próprio nível
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null && usuarioLogado.getId() == usuarioDoBanco.getId()) {
            result.include("mensagemErro", "Você não pode alterar seu próprio nível.");
            result.redirectTo(this).listarUsuarios(paginaAtual, null);
            return;
        }

        // Atualiza o nível de acesso do usuário
        usuarioDoBanco.setTipoUsuario(nivel);
        usuarioDao.update(usuarioDoBanco);

        result.include("mensagemSucesso", "Nível de usuário atualizado com sucesso!");
        result.redirectTo(this).listarUsuarios(paginaAtual, null);
    }

    @Post("excluirUsuario")
    public void excluirUsuario(int usuarioId, int paginaAtual) {
        // Criar um objeto Usuario temporário com o ID
        Usuario usuarioTemp = new Usuario();
        usuarioTemp.setId(usuarioId);
    	
    	// Busca o usuário do banco de dados
        Usuario usuarioDoBanco = usuarioDao.selectPorId(usuarioId);

        // Impede a exclusão do próprio usuário
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null && usuarioLogado.getId() == usuarioDoBanco.getId()) {
            result.include("mensagemErro", "Você não pode excluir sua própria conta.");
            result.redirectTo(this).listarUsuarios(paginaAtual, null);
            return;
        }

        // Exclui o usuário
        usuarioDao.delete(usuarioDoBanco);
        result.include("mensagemSucesso", "Usuário excluído com sucesso!");
        result.redirectTo(this).listarUsuarios(paginaAtual, null);
    }
}
