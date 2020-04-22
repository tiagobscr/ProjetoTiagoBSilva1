package dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Receita;
import entidade.Usuario;

public class ReceitaDaoImpl implements ReceitaDao {

	private EntityManager ent;

	// Construtor vai receber a conex�o para executar
	public ReceitaDaoImpl(EntityManager ent) {
		this.ent = ent;
	}

	@Override
	public boolean inserir(Receita receita) {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();
        
		ent.persist(receita);
		tx.commit();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Salvo com sucesso");
		facesContext.addMessage(null, facesMessage);

		return true;

	}

	@Override
	public void alterar(Receita receita) {
		System.out.println(receita);

		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.merge(receita);
		tx.commit();
		

	}

	@Override
	public void remover(Receita receita) {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.remove(receita);
		tx.commit();
		
		

	}

	@Override
	public Receita pesquisar(int id) {
		Receita receita = ent.find(Receita.class, id);

		return receita;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Receita> listarTodos() {
		Query query = ent.createQuery("from Receita r order by nome asc");

		List<Receita> receitas = query.getResultList();

		return receitas;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Receita> listarPorIngrediente(String nome) {
		Query query = ent.createQuery(
				"Select r FROM Receita r,Ingrediente i WHERE r.id = i.receita AND i.nome like '%" + nome + "%'   ORDER BY r.nome ASC");
		
		List<Receita> receitas = query.getResultList();

		return receitas;

	}

	@SuppressWarnings("unchecked")
	public List<Receita> listarPorUsuario(Usuario usuario) {
		Query query = ent.createQuery("from Receita Where email_usuario = '" + usuario.getEmail() +"'");

		List<Receita> receitas = query.getResultList();

		return receitas;
	}
}
