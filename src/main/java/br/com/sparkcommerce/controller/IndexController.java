package br.com.sparkcommerce.controller;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.Validator;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.imagem.Imagem;
import br.com.olimposistema.aipa.service.Util;
import br.com.sparkcommerce.dao.BannerDAO;
import br.com.sparkcommerce.dao.ConfiguracaoDAO;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.interceptors.SomenteAdmin;
import br.com.sparkcommerce.model.Banner;
import br.com.sparkcommerce.model.Categoria;
import br.com.sparkcommerce.model.Configuracao;
import br.com.sparkcommerce.model.Produto;
import br.com.sparkcommerce.model.Usuario;

@Controller
@Path("")
public class IndexController {
	
	@Inject private Result result;
    @Inject private HttpSession session;
    @Inject private BannerDAO bannerDao;
    @Inject private ProdutoDAO produtoDao;
    @Inject private EntityManager em;
    @Inject private HttpServletRequest request;
    @Inject private ConfiguracaoDAO configuracaoDao;
    @Inject DAO<Imagem> imagemDAO;
    @Inject Validator validator;
	
	@Get("")
	public void index(Banner banner) {
		// Carregar banners
        List<Banner> banners = bannerDao.buscarTodos();
            result.include("banners", banners);
        
        if(Util.isNotNull(banner) && Util.isPositivo(banner.getId())) {
			Banner bannerDoBanco = bannerDao.selectPorId(banner);
			result.include("banner",bannerDoBanco);
			Imagem imagemDoBanco = imagemDAO.selectPorId(bannerDoBanco.getImagem().getId());
			result.include("imagem",imagemDoBanco);
		}
        
        // Carregar configuração de tempo de exibição dos banners
        Configuracao configuracaoTempoExibicao = configuracaoDao.buscarPorNome("tempoExibicaoBanner");
        if (configuracaoTempoExibicao != null) {
            int tempoExibicao = configuracaoTempoExibicao.getTempoExibicaoBanner();
            result.include("tempoExibicaoBanner", tempoExibicao);
        } else {
            result.include("tempoExibicaoBanner", 5); // Valor padrão de 5 segundos
        }

        // Verificar se o usuário é ADMIN
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        boolean isAdmin = usuarioLogado != null && "ADMIN".equals(usuarioLogado.getTipoUsuario());
        result.include("isAdmin", isAdmin);
        
     // Exemplo para buscar e definir 4 categorias no controlador
        List<Categoria> categorias = this.buscarCategoriasAleatorias();

        for (int i = 0; i < categorias.size(); i++) {
            Categoria categoria = categorias.get(i);
            List<Produto> produtos = produtoDao.buscarProdutosAleatoriosPorCategoria(Long.valueOf(categoria.getId()));
            
            // Definindo cada lista de produtos no escopo da requisição com uma chave fixa
            request.setAttribute("categoria_" + i, categoria);
            request.setAttribute("produtosCategoria_" + i, produtos);
        }

        // Encaminhar a requisição para o JSP com as listas configuradas
        result.forwardTo("/WEB-INF/jsp/index/index.jsp");

	}
	public List<Categoria> buscarCategoriasAleatorias() {
		int limite = 4;
		return em.createQuery("SELECT c FROM Categoria c ORDER BY RANDOM()", Categoria.class)
				.setMaxResults(limite)
				.getResultList();
	}
	
	@Get("editarBanners") @SomenteAdmin
    public void editarBanners(Banner banner) {
        // Carregar todos os banners para exibição na página
        result.include("banners", bannerDao.buscarTodos());
        
     // Carregar configuração de tempo de exibição dos banners
        Configuracao configuracaoTempoExibicao = configuracaoDao.buscarPorNome("tempoExibicaoBanner");
        if (configuracaoTempoExibicao != null) {
            int tempoExibicao = configuracaoTempoExibicao.getTempoExibicaoBanner();
            result.include("tempoExibicaoBanner", tempoExibicao);
        } else {
            result.include("tempoExibicaoBanner", 5); // Valor padrão de 5 segundos
        }

        // Verificar se estamos editando um banner existente
        if (Util.isNotNull(banner) && Util.isPositivo(banner.getId())) {
            Banner bannerDoBanco = bannerDao.selectPorId(banner);
            result.include("banner", bannerDoBanco);

            // Se o banner já possui uma imagem, carrega e inclui no JSP
            if (bannerDoBanco.getImagem() != null) {
                Imagem imagemDoBanco = imagemDAO.selectPorId(bannerDoBanco.getImagem().getId());
                result.include("imagem", imagemDoBanco);
            }
        }
    }

//    @IncludeParameters
    @Post("adicionarBanner")
    public void adicionarBanner(@Valid Banner banner, UploadedFile imagemFile) {
        validator.onErrorRedirectTo(this).editarBanners(banner);

        if (Util.isNotNull(banner.getId()) && banner.getId() > 0) {
            Banner bannerDoBanco = bannerDao.selectPorId(banner);
            
            if (imagemFile != null && !imagemFile.getFileName().isEmpty()) {
                Imagem novaImagem = new Imagem(imagemFile);
                banner.setImagem(novaImagem);
            } else {
                banner.setImagem(bannerDoBanco.getImagem());
            }
        } else {
            if (imagemFile != null && !imagemFile.getFileName().isEmpty()) {
                Imagem novaImagem = new Imagem(imagemFile);
                banner.setImagem(novaImagem);
            }
        }

        bannerDao.insertOrUpdate(banner);
        result.redirectTo(this).editarBanners(null);
    }

    @Post("removerBanner/{id}")
    public void removerBanner(int id) {
        bannerDao.removerPorId(id);
        result.redirectTo(this).editarBanners(null);
    }
    
    @Post("configurarTempoExibicao")
    public void configurarTempoExibicao(int tempoExibicao) {
        // Salvar o tempo de exibição, por exemplo, em uma configuração global
        Configuracao configuracao = configuracaoDao.buscarPorNome("tempoExibicaoBanner");
        configuracao.setTempoExibicaoBanner(tempoExibicao);
        configuracaoDao.update(configuracao);
        result.redirectTo(this).editarBanners(null);
    }
}