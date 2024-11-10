package br.com.sparkcommerce.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.dao.IdInvalidoException;
import br.com.sparkcommerce.model.Categoria;
import br.com.sparkcommerce.model.Produto;

@RequestScoped
public class ProdutoDAO extends DAO<Produto> {
	
	@Deprecated public ProdutoDAO() {super(null,null);}
	
	@Inject
	public ProdutoDAO(EntityManager em) {
		super(em, Produto.class);
	}
	
	public List<Produto> buscaTodosOsProdutosOrdenadoPeloNome(){
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
		
		Root<Produto> r = q.from(Produto.class);
		q.select(r)
		.orderBy(cb.desc(r.get("nome")));
		
		TypedQuery<Produto> query = em.createQuery(q);
		List<Produto> produtos = query.getResultList();
		return produtos;
	}

	public List<Produto> buscaPorCategoriaEspecifica() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
		
		Root<Produto> r = q.from(Produto.class);
			Join<Produto, Categoria> jc = r.join("categoria", JoinType.INNER);
		
		q.select(r)
		.where(
				cb.and(
						cb.like(cb.lower(r.get("nome")), "%nome%")),
						cb.equal(jc.get("id"), 3)				
				);
				
		
		TypedQuery<Produto> query = em.createQuery(q);
		List<Produto> produtos = query.getResultList();
		return produtos;
		
	}
	
	public Long totalProdutos() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		
		Root<Produto> r = q.from(Produto.class);
		q.multiselect(cb.count(r));
		
		TypedQuery<Long> query = em.createQuery(q);
		Long total = query.getSingleResult();
		return total;
		
	}
	
	public Produto selectPorIdLong(Long id) {
	    return em.find(Produto.class, id.intValue());
	}
	
	public Produto selectPorId(Produto produto) {
	    return em.find(Produto.class, produto.getId());
	}
	
	public void delete(Produto produto) {
		if(produto!= null && produto.getId() < 1) {
			throw new IdInvalidoException("Não foi Possível deletar pois o id é 0 ou inválido!  classe do registro:"+ produto.getClass().getSimpleName() + " id do registro: "+produto.getId());
		}
		produto = selectPorId(produto.getId());
		em.remove(produto);
	}
	
	public List<Produto> buscarProdutosAleatoriosPorCategoria(Long categoriaId) {
		int limite = 4;
		int intCategoriaId = categoriaId.intValue();
	    return em.createQuery("SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId ORDER BY RANDOM()", Produto.class)
	             .setParameter("categoriaId", intCategoriaId)
	             .setMaxResults(limite)
	             .getResultList();
	}
}
