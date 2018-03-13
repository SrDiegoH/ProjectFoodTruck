package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="avaliacao")
public class Avaliacao extends BaseEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	private String comentario;
	private Long pontos;
	private Prato prato;
	
	
	public Avaliacao() {
		super();		
	}
	
	public Avaliacao(String comentario, Long pontos, Prato prato) {
		super();
		this.comentario = comentario;
		this.pontos = pontos;
		this.prato = prato;
	}
	
	
	@Column(length = 100, nullable = false)
	public String getComentario() {
		return comentario;
	}
	public Avaliacao setComentario(String comentario) {
		this.comentario = comentario;
		return this;
	}
	
	
	@Column(length = 100, nullable = false)
	public Long getPontos() {
		return pontos;
	}
	public Avaliacao setPontos(Long pontos) {
		this.pontos = pontos;
		return this;
	}

	
	@OneToOne
	@JoinColumn(name="prato_id", nullable = false)	
	public Prato getPrato() {
		return prato;
	}
	public Avaliacao setPrato(Prato prato) {
		this.prato = prato;
		return this;
	}
	

	@Override
	public Avaliacao clone() {
		try {
			return (Avaliacao) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}
}
