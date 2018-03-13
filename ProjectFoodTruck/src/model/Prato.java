package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="prato")
public class Prato extends BaseEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;
	private Double preco;
	private FoodTruck foodTruck;
	private int avaliacaoPositiva;
	private int avaliacaoNegativa;
	private String localImagem;
	
	public String getLocalImagem() {
		return localImagem;
	}

	public void setLocalImagem(String localImagem) {
		this.localImagem = localImagem;
	}

	public Prato() {
		super();
	}
		
	public Prato(String nome, String descricao, Double preco, FoodTruck foodTruck) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.foodTruck = foodTruck;
	}
	
	@Column(length = 100, nullable = false)
	public String getNome() {
		return nome;
	}
	public Prato setNome(String nome) {
		this.nome = nome;
		return this;
	}


	@Column(length = 500, nullable = false)
	public String getDescricao() {
		return descricao;
	}
	public Prato setDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}
	

	@Column(length = 100, nullable = false)
	public Double getPreco() {
		return preco;
	}
	public Prato setPreco(Double preco) {
		this.preco = preco;
		return this;
	}	

	
	@ManyToOne
	@JoinColumn(name="foodTruck_id", nullable = false)
	public FoodTruck getFoodTruck() {
		return foodTruck;
	}
	public Prato setFoodTruck(FoodTruck foodTruck) {
		this.foodTruck = foodTruck;
		return this;
	}
	
	
	@Column(nullable = true)
	public Integer getAvaliacaoPositiva() {
		return avaliacaoPositiva;
	}
	public void setAvaliacaoPositiva(Integer avaliacaoPositiva) {
		this.avaliacaoPositiva = avaliacaoPositiva;
	}

	@Column(nullable = true)
	public Integer getAvaliacaoNegativa() {
		return avaliacaoNegativa;
	}
	public void setAvaliacaoNegativa(Integer avaliacaoNegativa) {
		this.avaliacaoNegativa = avaliacaoNegativa;
	}

	
	@Override
	public Prato clone() {
		try {
			return (Prato) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Prato [nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + ", foodTruck=" + foodTruck + "]";
	}	
}
