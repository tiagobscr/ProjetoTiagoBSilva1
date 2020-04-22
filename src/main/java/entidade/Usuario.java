package entidade;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {

	@Id
	@Column(name = "email")
	private String email;
	@Column(name = "nome")
	private String nome;
	@Column(name = "senha")
	private String senha;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Telefone> telefones;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco> enderecos;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Receita> receitas;
	@Column(name = "funcao")
	private String funcao;
	@Column(name = "dataNasc")
	private LocalDate dataNascimento;
	@Column(name = "idade")
	private int idade;
	@Column(name = "sexo")
	private String sexo;

	public Usuario() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Override
	public String toString() {
		return nome;
	}

}
