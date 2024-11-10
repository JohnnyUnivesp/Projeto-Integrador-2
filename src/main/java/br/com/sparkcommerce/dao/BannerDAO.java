package br.com.sparkcommerce.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.sparkcommerce.model.Banner;

@RequestScoped
public class BannerDAO extends DAO<Banner> {
	
	@Deprecated public BannerDAO() {super(null,null);}
	
	@Inject
	public BannerDAO(EntityManager em) {
		super(em, Banner.class);
	}
	
	// Método para buscar todos os banners
    public List<Banner> buscarTodos() {
        String jpql = "SELECT b FROM Banner b";
        return em.createQuery(jpql, Banner.class).getResultList();
    }

    // Método para buscar um banner específico pelo ID
    public Banner buscarPorId(int id) {
        return em.find(Banner.class, id);
    }

    // Método para remover um banner específico pelo ID
    public void removerPorId(int id) {
        Banner banner = buscarPorId(id);
        if (banner != null) {
            em.remove(banner);
        }
    }

    // Método para salvar ou atualizar um banner
    public void salvar(Banner banner) {
        if (Long.valueOf(banner.getId()) == null) {
            em.persist(banner);
        } else {
            em.merge(banner);
        }
    }
}