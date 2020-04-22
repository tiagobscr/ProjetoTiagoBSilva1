package entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RECEITA")
public class Receita {

	@Id
	@Column(name = "id_receita")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "nome")
	private String nome;
	@OneToMany(mappedBy = "receita", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Ingrediente> ingredientes;
	@ManyToOne
	@JoinColumn(name = "email_usuario", referencedColumnName = "email", nullable = false)
	private Usuario usuario;
	@Column(name = "modopreparo")
	private String modoPreparo;
	@Column(name = "tempopreparo")
	private String tempoPreparo;

	public Receita() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getModoPreparo() {
		return modoPreparo;
	}

	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(String tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	@Override
	public String toString() {
		return nome;
	}

}
