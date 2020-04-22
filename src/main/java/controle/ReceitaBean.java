package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.IngredienteDao;
import dao.IngredienteDaoImpl;
import dao.ReceitaDao;
import dao.ReceitaDaoImpl;
import dao.UsuarioDao;
import dao.UsuarioDaoImpl;

import entidade.Ingrediente;
import entidade.Receita;

import entidade.Usuario;
import util.JpaUtil;

@ManagedBean(name = "ReceitaBean")
@ViewScoped
public class ReceitaBean {

	private Usuario usuario;
	private Receita receita;
	private Ingrediente ingrediente;
	private List<Receita> receitas;
	private UsuarioDao usuarioDao;
	private ReceitaDao receitaDao;
	private String nomePesquisa;
	private int quantPesquisada;
	private String receitaSelecionada;
	private List<Ingrediente> ingredientes;
	private IngredienteDao ingredienteDao;
	private List<Usuario> usuarios;
	private List<Ingrediente> novoIngredientes;
	private String emailPesquisa;

	private static final String CRIAR = "criarReceita.xhtml";
	private static final String RECEITA = "pesquisarReceita.xhtml";
	private static final String Login = "index.xhtml";

	public ReceitaBean() {

		this.usuario = new Usuario();
		this.receita = new Receita();
		this.ingrediente = new Ingrediente();

		this.receitas = new ArrayList<Receita>();
		// Instanciando a interface com a implementação, passando a conexão
		this.receitaDao = new ReceitaDaoImpl(JpaUtil.getEntityManager());
		this.usuarioDao = new UsuarioDaoImpl(JpaUtil.getEntityManager());
		this.ingredienteDao = new IngredienteDaoImpl(JpaUtil.getEntityManager());
		// Recupera a lista de todos as receitas e ingredientes
		this.receitas = this.receitaDao.listarTodos();
		this.usuarios = this.usuarioDao.listarTodos();
		this.receita.setUsuario(this.usuario);
		this.ingredientes = this.ingredienteDao.listarTodos();
		this.novoIngredientes = new ArrayList<Ingrediente>();

	}

	public void pesquisar() {
		this.receitas = this.receitaDao.listarTodos();

		System.out.println("Teste");

	}

	public void pesquisarIngrediente() {
		System.out.println(this.nomePesquisa);
		this.receitas = this.receitaDao.listarPorIngrediente(nomePesquisa);
		this.quantPesquisada = this.receitas.size();
	}
	
	

	public void pesquisarUsuario() {
		
		if (this.usuarioDao.pesquisar(this.emailPesquisa) != null) {
			Usuario usuarioAtual = this.usuarioDao.pesquisar(this.emailPesquisa);
			this.receitas.clear();
			this.receitas = this.receitaDao.listarPorUsuario(usuarioAtual);
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", " email inválido "));
		}

		
		

	}

	public void salvar() throws IOException {
        
		if (this.usuarioDao.pesquisar(this.receita.getUsuario().getEmail()) != null) {
			
		
		this.receita.setUsuario(this.usuario);
		System.out.println(this.receita.getUsuario());
		if (receitaDao.inserir(this.receita)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.receita.getNome() + " salvo com sucesso."));
			System.out.println("=======salvou ======");
			this.receita = new Receita();
			this.ingrediente = new Ingrediente();
			abrirPesquisarReceita();

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao inserir."));
		}
		}else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Email inválido."));
		}
	}

	public void exibirReceita() {

		Receita receitaAtual = this.receitaDao.pesquisar(this.receita.getId());
		this.receita = receitaAtual;
		receitaDao.alterar(receitaAtual);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", receitaAtual.getNome() + " alterado com sucesso."));
		pesquisar();
		System.out.println("alterado");

	}

	public void removerReceita() {

		System.out.println(usuario);
		Receita receitaAtual = this.receitaDao.pesquisar(this.receita.getId());
		this.receita = receitaAtual;

		receitaDao.remover(receitaAtual);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", receitaAtual.getNome() + " Excluido com sucesso."));
		System.out.println("removido");
		pesquisar();

	}

	public void novoIngrediente() {
		Ingrediente ingredienteNovo = new Ingrediente();
		System.out.println(this.ingrediente);
		ingredienteNovo.setNome(this.ingrediente.getNome());
		ingredienteNovo.setTipo(this.ingrediente.getTipo());
		this.ingredientes.add(this.ingrediente);
	}

	public void adicionarIngrediente() {
		Ingrediente ingredienteNovo = new Ingrediente();
		ingredienteNovo.setNome(this.ingrediente.getNome());
		ingredienteNovo.setTipo(this.ingrediente.getTipo());
		this.receita.setNome("teste");
		this.receita.setIngredientes(novoIngredientes);
		this.receita.setUsuario(this.usuario);
		System.out.println(ingredienteNovo);
		System.out.println(this.ingrediente);
		ingredienteNovo.setReceita(this.receita);
		System.out.println(this.receitas);
		System.out.println(this.receita);
		System.out.println(this.receita.getIngredientes());

		if (!this.existeIngrediente(ingredienteNovo)) {
			this.receita.getIngredientes().add(ingredienteNovo);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", " Ingrediente já cadastrado para "));

		}

		this.ingrediente = new Ingrediente();
	}

	private boolean existeIngrediente(Ingrediente ingrediente) {
		boolean resultado = false;

		for (Ingrediente ingredienteAtual : this.receita.getIngredientes()) {
			if (ingredienteAtual.getNome().equals(ingrediente.getNome())
					&& ingredienteAtual.getTipo().equals(ingrediente.getTipo())) {
				resultado = true;
			}
		}

		return resultado;
	}

	public void removerIngrediente() {

		int i = 0;
		while (i < this.receita.getIngredientes().size()) {

			if (this.receita.getIngredientes().get(i).getId() == this.ingrediente.getId()) {
				this.receita.getIngredientes().remove(i);
			}
			i = i + 1;
		}

		System.out.println(this.receita.getIngredientes());

	}

	public void abrirCriarReceita() throws IOException {
		System.out.println(this.usuario);
		FacesContext.getCurrentInstance().getExternalContext().redirect(CRIAR);

	}

	public void abrirPesquisarReceita() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(RECEITA);
	}

	public void abrirLogin() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(Login);

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	public String getNomePesquisa() {
		return nomePesquisa;
	}

	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}

	public int getQuantPesquisada() {
		return quantPesquisada;
	}

	public void setQuantPesquisada(int quantPesquisada) {
		this.quantPesquisada = quantPesquisada;
	}

	public String getReceitaSelecionada() {
		return receitaSelecionada;
	}

	public void setReceitaSelecionada(String receitaSelecionada) {
		this.receitaSelecionada = receitaSelecionada;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Ingrediente> getNovoIngredientes() {
		return novoIngredientes;
	}

	public void setNovoIngredientes(List<Ingrediente> novoIngredientes) {
		this.novoIngredientes = novoIngredientes;
	}

	public String getEmailPesquisa() {
		return emailPesquisa;
	}

	public void setEmailPesquisa(String emailPesquisa) {
		this.emailPesquisa = emailPesquisa;
	}

}
