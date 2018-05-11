package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="session")
public class Session extends BaseEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private FoodTruck foodTruck;
	private String hashValor;
	private Date prazo;
	private boolean isAtivo;
	
	public Session() {
		super();
	}
	
	public Session(String hashValor, FoodTruck foodTruck, Date prazo, boolean isAtivo) {
		super();
		this.hashValor = hashValor;
		this.foodTruck = foodTruck;
		this.prazo = prazo;
		this.isAtivo = isAtivo;
	}


    @OneToOne
	public FoodTruck getFoodTruck() {
		return foodTruck;
	}
	public Session setFoodTruck(FoodTruck foodTruck) {
		this.foodTruck = foodTruck;
		return this;
	}
	
	
	@Column(length = 100, nullable = false)
	public String getHashValor() {
		return hashValor;
	}

	public Session setHashValor(String hashValor) {
		this.hashValor = hashValor;
		return this;
	}

	
	@Column(length = 100, nullable = false)
	public Date getPrazo() {
		return prazo;
	}
	public Session setPrazo(Date prazo) {
		this.prazo = prazo;
		return this;
	}
	
	
	@Column(length = 100, nullable = false)
	public boolean isAtivo() {
		return isAtivo;
	}
	public Session setIsAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
		return this;
	}
	
	
	public boolean isExpired() {
		return this.getPrazo().after(new Date()) ;
	}
	
	public boolean isOver() {
		return this.getPrazo().before(new Date()) || !isAtivo;
	}
	

	@Override
	public Session clone() {
		try {
			return (Session) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}
	
	
	@Override
	public String toString() {
		return "Session [prazo=" + prazo + ", Id Food Truck=" + foodTruck.getId() + ", hash=" + hashValor + "]";
	}		
}