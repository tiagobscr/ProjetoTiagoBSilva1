package dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Ingrediente;

public class IngredienteDaoImpl implements IngredienteDao {

	private EntityManager ent;

	// Construtor vai receber a conex�o para executar
	public IngredienteDaoImpl(EntityManager ent) {
		this.ent = ent;
	}

	@Override
	public boolean inserir(Ingrediente ingrediente) {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.persist(ingrediente);
		tx.commit();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Salvo com sucesso");
		facesContext.addMessage(null, facesMessage);

		return true;

	}

	@Override
	public void alterar(Ingrediente ingrediente) {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.merge(ingrediente);
		tx.commit();

	}

	@Override
	public void remover(Ingrediente ingrediente) {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.remove(ingrediente);
		tx.commit();

	}

	@Override
	public Ingrediente pesquisar(int id) {
		Ingrediente ingrediente = ent.find(Ingrediente.class, id);

		return ingrediente;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ingrediente> listarTodos() {
		Query query = ent.createQuery("from Ingrediente i order by nome asc");

		List<Ingrediente> ingredientes = query.getResultList();

		return ingredientes;

	}

}
