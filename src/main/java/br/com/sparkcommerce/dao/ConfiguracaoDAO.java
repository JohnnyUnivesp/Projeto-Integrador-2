package br.com.sparkcommerce.dao;

import br.com.sparkcommerce.model.Configuracao;
import br.com.olimposistema.aipa.dao.DAO;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class ConfiguracaoDAO extends DAO<Configuracao> {

    @Inject
    public ConfiguracaoDAO(EntityManager em) {
        super(em, Configuracao.class);
    }

    @Deprecated
    public ConfiguracaoDAO() {
        super(null, null);
    }

    // Método para buscar uma configuração pelo nome
    public Configuracao buscarPorNome(String nome) {
        try {
            return em.createQuery("SELECT c FROM Configuracao c WHERE c.nome = :nome", Configuracao.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Retorna null se a configuração não existir
        }
    }

    // Método para salvar ou atualizar uma configuração
    public void salvarOuAtualizar(Configuracao configuracao) {
        Configuracao existente = buscarPorNome(configuracao.getNome());
        if (existente == null) {
            em.persist(configuracao);
        } else {
            existente.setValor(configuracao.getValor());
            em.merge(existente);
        }
    }
}
