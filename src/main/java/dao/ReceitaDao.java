package dao;

import java.util.List;

import entidade.Receita;
import entidade.Usuario;

public interface ReceitaDao {
	
	public boolean inserir(Receita receita);

	public void alterar(Receita receita);

	public void remover(Receita receita);

	public Receita pesquisar(int id);

	public List<Receita> listarTodos();

	public List<Receita> listarPorIngrediente(String nome);
	
	public List<Receita> listarPorUsuario(Usuario usuario);

}
