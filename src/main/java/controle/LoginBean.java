package controle;

import java.io.IOException;
import java.util.List;

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

	private static final String PESQUISAR = "pesquisarUsuario.xhtml";

	private static final String Manter = "manterUsuario.xhtml";
	
	private static final String RECEITA = "pesquisarReceita.xhtml";

	public LoginBean() {

		this.usuarioDao = new UsuarioDaoImpl(JpaUtil.getEntityManager());
		this.receitaDao = new ReceitaDaoImpl(JpaUtil.getEntityManager());

	}

	public void entrar() throws IOException {

		if (this.usuarioTXT.equals("admin") && this.senhaTXT.equals("admin")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
		} else {
			if (this.usuarioDao.pesquisar(this.usuarioTXT) != null) {
				if (this.usuarioDao.pesquisar(this.usuarioTXT).getSenha().equals(this.senhaTXT)) {
					this.usuarioLogado=usuarioDao.pesquisar(this.usuarioTXT);
					this.receitas=this.receitaDao.listarPorUsuario(this.usuarioLogado);
					this.usuario = this.usuarioLogado;
					this.receita=new Receita();
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
	
	
	
	

}
