package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Integer id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	public Integer getId() {
		return id;
	}
	public BaseEntity setId(Integer id) {
		this.id = id;
		return this;
	}
	

	public BaseEntity criptografar() {
		return this;
	}

	public BaseEntity descriptografar() {
		return this;
	}
}
