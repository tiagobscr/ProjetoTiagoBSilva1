package dao;

import java.util.List;

import entidade.Ingrediente;



public interface IngredienteDao {

	public boolean inserir(Ingrediente ingrediente);

	public void alterar(Ingrediente ingrediente);

	public void remover(Ingrediente ingrediente);

	public Ingrediente pesquisar(int id);

	public List<Ingrediente> listarTodos();

	
}
