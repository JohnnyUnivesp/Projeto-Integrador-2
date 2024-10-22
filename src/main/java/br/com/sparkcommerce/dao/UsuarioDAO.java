package br.com.sparkcommerce.dao;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.dao.IdInvalidoException;
import br.com.sparkcommerce.model.Usuario;

@RequestScoped
public class UsuarioDAO extends DAO<Usuario> {
    
    @Deprecated 
    public UsuarioDAO() {
        super(null, null);
    }

    @Inject
    public UsuarioDAO(EntityManager em) {
        super(em, Usuario.class);
    }

    // Método para autenticação de usuário com email e senha
    public Usuario exiteUsuarioCom(String email, String senha) {
        String jpql = "select u from Usuario as u where u.email = :pEmail and u.senha = :pSenha";
        Query query = em.createQuery(jpql);
        query.setParameter("pEmail", email);
        query.setParameter("pSenha", senha);
        
        try {
            Usuario usuario = (Usuario) query.getSingleResult();
            // Verifica o tipo de usuário (ADMIN ou USER) após a autenticação
            System.out.println("Tipo de Usuário: " + usuario.getTipoUsuario()); // Para debug
            return usuario;
        } catch (NoResultException e) {
            return null;
        }
    }

    // Método para verificar se um e-mail já existe
    public boolean emailExiste(String email) {
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email";
        Long count = em.createQuery(jpql, Long.class)
                       .setParameter("email", email)
                       .getSingleResult();
        return count > 0; // Retorna verdadeiro se já existir um e-mail com esse endereço
    }

    // Método para buscar todos os usuários com paginação
    public List<Usuario> buscarTodos(int page, int limit) {
    	if (page < 1) {
            page = 1; // Ajuste a página para ser pelo menos 1
        }
        String jpql = "SELECT u FROM Usuario u ORDER BY u.nome";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class)
                                      .setFirstResult((page - 1) * limit)
                                      .setMaxResults(limit);
        return query.getResultList();
    }

    // Método para contar o total de usuários no banco de dados
    public int totalUsuarios() {
        String jpql = "SELECT COUNT(u) FROM Usuario u";
        return ((Long) em.createQuery(jpql).getSingleResult()).intValue();
    }

    // Método para buscar usuários por nome ou email com filtro e paginação
    public List<Usuario> buscarPorNomeOuEmail(String filtro, int page, int limit) {
    	if (page < 1) {
    		page = 1; // Ajuste a página para ser pelo menos 1
    	}
        String jpql = "SELECT u FROM Usuario u WHERE u.nome LIKE :filtro OR u.email LIKE :filtro ORDER BY u.nome";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class)
                                      .setParameter("filtro", "%" + filtro + "%")
                                      .setFirstResult((page - 1) * limit)
                                      .setMaxResults(limit);
        return query.getResultList();
    }

    // Método para contar o total de usuários que correspondem ao filtro
    public int totalUsuariosComFiltro(String filtro) {
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.nome LIKE :filtro OR u.email LIKE :filtro";
        return ((Long) em.createQuery(jpql).setParameter("filtro", "%" + filtro + "%").getSingleResult()).intValue();
    }

    // Método para buscar um usuário por ID
    public Usuario selectPorId(Usuario usuario) {
        return em.find(Usuario.class, usuario.getId());
    }


    // Método para deletar um usuário
    public void delete(Usuario usuario) {
    	if(usuario!= null && usuario.getId() < 1) {
			throw new IdInvalidoException("Não foi Possível deletar pois o id é 0 ou inválido!  classe do registro:"+ usuario.getClass().getSimpleName() + " id do registro: "+usuario.getId());
		}
		usuario = selectPorId(usuario.getId());
		em.remove(usuario);
    }
}
