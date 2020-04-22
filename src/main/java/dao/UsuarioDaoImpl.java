package dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Usuario;

/**
 * 
 * @author Tiago Batista
 *
 *         Essa classe implementa a interface, todos os metodos mostrados na
 *         interface. Lembrando de uma coisa, a implementa��o ela recebe no
 *         construtor o entityManager, a conex�o com o banco de dados, deixando
 *         assim essa classe totalemnte independente
 *
 */
public class UsuarioDaoImpl implements UsuarioDao {

	private EntityManager ent;

	// Construtor vai receber a conex�o para executar
	public UsuarioDaoImpl(EntityManager ent) {
		this.ent = ent;
	}

	@Override
	public boolean inserir(Usuario usuario) {
		// TODO Auto-generated method stub

		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.persist(usuario);
		tx.commit();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Salvo com sucesso");
		facesContext.addMessage(null, facesMessage);

		return true;
	}

	@Override
	public void alterar(Usuario usuario) {
		// TODO Auto-generated method stub
		System.out.println(usuario);
		System.out.println(usuario.getEmail());
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.merge(usuario);
		tx.commit();

	}

	@Override
	public void remover(Usuario usuario) {
		// TODO Auto-generated method stub
		System.out.println(usuario);
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.remove(usuario);
		tx.commit();

	}

	/**
	 * Pesquisar, pesquisar pela chave primaria que � o email
	 */

	@Override
	public Usuario pesquisar(String email) {

		Usuario usuario = ent.find(Usuario.class, email);

		return usuario;
	}

	/**
	 * O metodo listar todos, faz um select * from, por�m com o JPA, vc faz a
	 * consulta pelo objeto direto assim from Usuario, que isso � o objeto usuario e
	 * n�o a tabela
	 */

	@SuppressWarnings("unchecked")
	public List<Usuario> listarTodos() {

		Query query = ent.createQuery("from Usuario u order by nome asc");

		List<Usuario> usuarios = query.getResultList();

		return usuarios;
	}

	/**
	 * O metodo listar nome, faz um select por nome usando Where e LIKE para procura
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarNome(String nome) {
		// TODO Auto-generated method stub
		Query query = ent.createQuery("From  Usuario Where nome LIKE '%" + nome + "%' ORDER BY nome asc");

		List<Usuario> usuarios = query.getResultList();

		return usuarios;

	}

}
