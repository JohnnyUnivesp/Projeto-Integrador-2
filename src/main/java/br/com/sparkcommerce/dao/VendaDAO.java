package br.com.sparkcommerce.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.sparkcommerce.model.Venda;

	@RequestScoped
	public class VendaDAO extends DAO<Venda> {
    
	    @Deprecated 
	    public VendaDAO() {
	        super(null, null);
	    }
	
	    @Inject
	    public VendaDAO(EntityManager em) {
	        super(em, Venda.class);
	    }
	    
	    public boolean existsByPaymentId(Long paymentId) {
	        try {
	            em.createQuery("SELECT v FROM Venda v WHERE v.paymentId = :paymentId", Venda.class)
	              .setParameter("paymentId", paymentId)
	              .getSingleResult();
	            return true;
	        } catch (NoResultException e) {
	            return false;
	        }
	    }
	    
	    public void save(Venda venda) {
	    	if (!existsByPaymentId(venda.getPaymentId())) {
	            em.persist(venda);
	        } else {
	            return;
	        }
	}
	    
	    public Venda buscarPorPaymentId(Long paymentId) {
	        String jpql = "SELECT v FROM Venda v WHERE v.paymentId = :paymentId";
	        TypedQuery<Venda> query = em.createQuery(jpql, Venda.class);
	        query.setParameter("paymentId", paymentId);
	        try {
	            return query.getSingleResult();
	        } catch (NoResultException e) {
	            return null; // Retorna null se não encontrar nenhuma venda com o paymentId
	        }
	    }
	    
	    public List<Venda> buscarPorNomeOuPagamento(String filtro, String status, int page, int limit) {
	    	if (page < 1) {
	    		page = 1; // Ajuste a página para ser pelo menos 1
	    	}
	    	StringBuilder jpql = new StringBuilder("SELECT v FROM Venda v WHERE 1=1");
	        
	        if (filtro != null && !filtro.isEmpty()) {
	        	jpql.append(" AND (v.compradorNome LIKE :filtro OR CAST(v.paymentId AS text) LIKE :filtro)");
	        }
	        
	        if (status != null && !status.equalsIgnoreCase("todos")) {
	            jpql.append(" AND v.status = :status");
	        }

	        TypedQuery<Venda> query = em.createQuery(jpql.toString(), Venda.class)
	                      .setFirstResult((page - 1) * limit)
	                      .setMaxResults(limit);
	        
	        if (filtro != null && !filtro.isEmpty()) {
	            query.setParameter("filtro", "%" + filtro + "%");
	        }
	        
	        if (status != null && !status.equalsIgnoreCase("todos")) {
	            query.setParameter("status", status);
	        }
	        
	        return query.getResultList();
	    }
	    
	 // Contar todas as vendas com um filtro por nome/ID e status
	    public int totalVendasComFiltro(String filtro, String status) {
	        String jpql = "SELECT COUNT(v) FROM Venda v WHERE (LOWER(v.compradorNome) LIKE LOWER(:filtro) OR CAST(v.paymentId AS string) LIKE :filtro)";
	        
	        // Adiciona a condição de status se for diferente de "todos"
	        if (status != null && !status.equalsIgnoreCase("todos")) {
	            jpql += " AND v.status = :status";
	        }
	        
	        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
	        query.setParameter("filtro", "%" + filtro + "%");
	        
	        // Define o parâmetro de status apenas se ele tiver sido especificado
	        if (status != null && !status.equalsIgnoreCase("todos")) {
	            query.setParameter("status", status);
	        }
	        
	        return query.getSingleResult().intValue();
	    }

	    // Buscar todas as vendas, com filtro por nome ou ID de pagamento, paginadas
	    public List<Venda> buscarTodas(String filtro, int page, int limite) {
	    	if (page < 1) {
	    		page = 1; // Ajuste a página para ser pelo menos 1
	    	}
	    	String jpql = "SELECT v FROM Venda v ORDER BY v.dataPagamento DESC";
	        TypedQuery<Venda> query = em.createQuery(jpql, Venda.class)
								        .setFirstResult((page - 1) * limite)
								        .setMaxResults(limite);
	        
	        return query.getResultList();
	    }

	    // Contar o total de vendas sem filtro
	    public int totalVendas() {
	        String jpql = "SELECT COUNT(v) FROM Venda v";
	        return ((Long) em.createQuery(jpql).getSingleResult()).intValue();
	    }
}