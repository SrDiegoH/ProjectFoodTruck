package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="local")
public class Local extends BaseEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Double latitude;
	private Double longitude;
	private FoodTruck foodTruck;
	
	public Local() {
		super();
	}

	public Local(String nome, Double latitude, Double longitude, FoodTruck foodTruck) {
		super();
		this.nome = nome;
		this.latitude = latitude;
		this.longitude = longitude;
		this.foodTruck = foodTruck;
	}

	@Column(length = 100, nullable = false)
	public String getNome() {
		return nome;
	}
	public Local setNome(String nome) {
		this.nome = nome;
		return this;
	}

	@Column(length = 100, nullable = false)
	public Double getLatitude() {
		return latitude;
	}
	public Local setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	@Column(length = 100, nullable = false)
	public Double getLongitude() {
		return longitude;
	}
	public Local setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	@ManyToOne
	@JoinColumn(name="foodTruck_id", nullable = false)
	public FoodTruck getFoodTruck() {
		return foodTruck;
	}
	public Local setFoodTruck(FoodTruck foodTruck) {
		this.foodTruck = foodTruck;
		return this;
	}
	
	
	@Override
	public Local clone() {
		try {
			return (Local) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Local [nome=" + nome + ", Lat=" + latitude + ", long=" + longitude + ", foodTruck=" + foodTruck + "]";
	}
}