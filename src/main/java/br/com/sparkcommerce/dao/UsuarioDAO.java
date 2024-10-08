package br.com.sparkcommerce.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.sparkcommerce.model.Usuario;

@RequestScoped
public class UsuarioDAO extends DAO<Usuario> {
	
	@Deprecated public UsuarioDAO() {super(null,null);}
	
	@Inject
	public UsuarioDAO(EntityManager em) {
		super(em, Usuario.class);
	}

	public Usuario exiteUsuarioCom(String email, String senha) {

		String jpql = "select u from Usuario as u where u.email = :pEmail and u.senha = :pSenha";
		Query query = em.createQuery(jpql);
		
		query.setParameter("pEmail", email);
		query.setParameter("pSenha", senha);
		
		try {
			Usuario usuario = (Usuario) query.getSingleResult();
			
			  // Verifica o tipo de usuário (ADMIN ou USER) após a autenticação
            System.out.println("Tipo de Usuário: " + usuario.getTipoUsuario());  // Para debug
			
			return usuario;
		}catch (NoResultException e) {
			return null;
		}
			}

}
