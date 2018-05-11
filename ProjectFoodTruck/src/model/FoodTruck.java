package model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="foodTruck")
public class FoodTruck extends BaseEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	private String nome;
	private String descricao;
	private Double latitude;
	private Double longitude;
	private Collection<Prato> pratos;
	private Collection<Local> locais;
	private Session session;
	private int avaliacaoPositiva;
	private int avaliacaoNegativa;
	private String localImagem;
	private String contaConfirmada;
	private String codConfirmacao;
	
	public FoodTruck() {
		super();
	}
	
	public FoodTruck(String email, String nome, String senha, String descricao, Double latitude, Double longitude, Collection<Prato> pratos, Collection<Local> locais) {
		super();
		this.email = email;
		this.senha = senha;
		this.nome = nome; 
		this.descricao = descricao;
		this.latitude = latitude;
		this.longitude = longitude;
		this.pratos = pratos;
		this.locais = locais;
	}


	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}
	public FoodTruck setEmail(String email) {
		this.email = email;
		return this;
	}
	
	@Column(length = 100, nullable = false)
	public String getNome() {
		return nome;
	}

	public FoodTruck setNome(String nome) {
		this.nome = nome;
		return this;
	}

	
	@Column(length = 100, nullable = false)
	public String getSenha() {
		return senha;
	}
	public FoodTruck setSenha(String senha) {
		this.senha = senha;
		return this;
	}

	
	@Column(length = 500, nullable = false)
	public String getDescricao() {
		return descricao;
	}
	public FoodTruck setDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	
	@Column(length = 100, nullable = false)
	public Double getLatitude() {
		return latitude;
	}
	public FoodTruck setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}
	@Column(length = 100, nullable = false)
	public Double getLongitude() {
		return longitude;
	}
	public FoodTruck setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}	
	
	
	@OneToMany(mappedBy = "foodTruck")
	public Collection<Prato> getPratos() {
		return pratos;
	}
	public FoodTruck setPratos(Collection<Prato> pratos) {
		this.pratos = pratos;
		return this;
	}
	
	@OneToMany(mappedBy = "foodTruck")
	private Collection<Local> getLocais() {
		return locais;
	}
	public void setLocais(Collection<Local> locais) {
		this.locais = locais;
	}
	

	@Column(nullable = true)
	public int getAvaliacaoPositiva() {
		return avaliacaoPositiva;
	}
	public FoodTruck setAvaliacaoPositiva(int avaliacaoPositiva) {
		this.avaliacaoPositiva = avaliacaoPositiva;
		return this;
	}

	@Column(nullable = true)
	public int getAvaliacaoNegativa() {
		return avaliacaoNegativa;
	}
	public FoodTruck setAvaliacaoNegativa(int avaliacaoNegativa) {
		this.avaliacaoNegativa = avaliacaoNegativa;
		return this;
	}

	@Column(nullable = true)	
	public String getLocalImagem() {
		return localImagem;
	}
	public FoodTruck setLocalImagem(String localImagem) {
		this.localImagem = localImagem;
		return this;
	}
	
	
	@Column(length = 3, nullable = false)
	public String getContaConfirmada() {
		return contaConfirmada;
	}
	public FoodTruck setContaConfirmada(String contaConfirmada) {
		this.contaConfirmada = contaConfirmada;
		return this;
	}

	@Column(nullable = false)
	public String getCodConfirmacao() {
		return codConfirmacao;
	}
	public FoodTruck setCodConfirmacao(String codConfirmacao) {
		this.codConfirmacao = codConfirmacao;
		return this;
	}
	

    @OneToOne(mappedBy="foodTruck")
	public Session getSession() {
		return session;
	}

	public FoodTruck setSession(Session session) {
		this.session = session;
		return this;
	}


	@Override
	public FoodTruck clone() {
		try {
			return (FoodTruck) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}
	
	
	@Override
	public String toString() {
		return "Food Truck [nome=" + nome + ", descricao=" + descricao + ", email=" + email + ", lat=" + latitude + ", lng=" + longitude + "]";
	}		
}