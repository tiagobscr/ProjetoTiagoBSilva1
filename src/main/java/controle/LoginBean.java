package controle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;

import dao.ReceitaDao;
import dao.ReceitaDaoImpl;
import dao.UsuarioDao;
import dao.UsuarioDaoImpl;
import entidade.Receita;
import entidade.Usuario;
import util.CalculoIdade;
import util.JpaUtil;

/**
 * 
 * @author Tiago Batista
 *
 *         LoginBean, classe responsavel pela logica de logar no sistema
 *
 */

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {

	private String usuarioTXT;
	private String senhaTXT;
	UsuarioDao usuarioDao;
	ReceitaDao receitaDao;
	Usuario usuario;
	private String mensagem;
	EntityManagerFactory factory;
	private Usuario usuarioLogado;
	private List<Receita> receitas;
	private Receita receita;
	private LocalDate dataNascimento;
	private String data;
	private String confirmaSenha;

	private static final String PESQUISAR = "pesquisarUsuario.xhtml";

	private static final String Manter = "manterUsuario.xhtml";

	private static final String RECEITA = "pesquisarReceita.xhtml";

	private static final String Login = "index.xhtml";

	private static final String Senha = "esqueciSenha.xhtml";

	public LoginBean() {
		this.usuario = new Usuario();
		this.usuarioDao = new UsuarioDaoImpl(JpaUtil.getEntityManager());
		this.receitaDao = new ReceitaDaoImpl(JpaUtil.getEntityManager());

	}

	public void entrar() throws IOException {

		if (this.usuarioTXT.equals("admin") && this.senhaTXT.equals("admin")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
		} else {
			if (this.usuarioDao.pesquisar(this.usuarioTXT) != null) {
				if (this.usuarioDao.pesquisar(this.usuarioTXT).getSenha().equals(this.senhaTXT)) {
					this.usuarioLogado = usuarioDao.pesquisar(this.usuarioTXT);
					this.receitas = this.receitaDao.listarPorUsuario(this.usuarioLogado);
					this.usuario = this.usuarioLogado;
					this.receita = new Receita();
					this.receita.setUsuario(this.usuario);
					System.out.println(this.receita.getUsuario());

					FacesContext.getCurrentInstance().getExternalContext().redirect(RECEITA);
				} else {
					this.mensagem = " Senha inválida";
				}
			} else {

				this.mensagem = " Usuario não cadastrado";
			}

		}
	}

	public void abrirManterUsuario() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext().redirect(Manter);
	}

	public void abrirEsqueciSenha() throws IOException {

		if (this.usuarioDao.pesquisar(this.usuarioTXT) != null) {
			this.usuario=new Usuario();
			this.usuario.setEmail(this.usuarioTXT);

		} else {
			this.usuario.setEmail("");

		}

		FacesContext.getCurrentInstance().getExternalContext().redirect(Senha);
	}

	public void novaSenha() throws IOException {

		if (this.usuarioDao.pesquisar(this.usuario.getEmail()) != null) {

			Usuario usuarioAtual = this.usuarioDao.pesquisar(this.usuario.getEmail());
            System.out.println(usuarioAtual.getNome());
            System.out.println(usuarioAtual.getDataNascimento());
            System.out.println(this.usuario.getNome());
            System.out.println(this.usuario.getDataNascimento());
			if (this.usuario.getNome().equals(usuarioAtual.getNome())
					&& this.usuario.getDataNascimento().equals(usuarioAtual.getDataNascimento())) {
				usuarioAtual.setSenha(this.usuario.getSenha());
				usuarioDao.alterar(usuarioAtual);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						usuarioAtual.getNome() + " senha alterada com sucesso."));
				this.mensagem = " Senha alterada com sucesso.";
				usuarioAtual = new Usuario();
				System.out.println("alterado");
				abrirLogin();
			} else {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", " dados inválidos."));
				usuarioAtual = new Usuario();
				this.usuario = new Usuario();

			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", " Usuário inválido."));

			this.mensagem = " Usuário inválido.";

		}

	}

	public void convertDate() {

		System.out.println(data);
		dataNascimento = CalculoIdade.format(this.data);
		this.usuario.setIdade(CalculoIdade.idade(dataNascimento));
		System.out.println(this.usuario.getIdade());
		this.usuario.setDataNascimento(dataNascimento);

	}

	public void abrirLogin() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(Login);

	}

	public String getUsuarioTXT() {
		return usuarioTXT;
	}

	public void setUsuarioTXT(String usuarioTXT) {
		this.usuarioTXT = usuarioTXT;
	}

	public String getSenhaTXT() {
		return senhaTXT;
	}

	public void setSenhaTXT(String senhaTXT) {
		this.senhaTXT = senhaTXT;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

}
