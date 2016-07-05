/**
 * 
 */
package bo.com.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * @author Cinthia Zabala
 *
 */
@Entity
@Table(name = "planes_seguro", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_individuo", "id_plan_seguro" }))
public class PlanesSeguro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3369027936311103384L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_individuo", nullable = false)
	private Individuo individuo;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_plan_seguro", nullable = false)
	private PlanSeguro planSeguro;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private Integer usuarioRegistro;

	/**
	 * 
	 */
	public PlanesSeguro() {
		super();
		this.id = 0;
		this.individuo = new Individuo();
		this.planSeguro = new PlanSeguro();
		this.estado = "AC";
		this.usuarioRegistro = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Individuo getIndividuo() {
		return individuo;
	}

	public void setIndividuo(Individuo individuo) {
		this.individuo = individuo;
	}

	public PlanSeguro getPlanSeguro() {
		return planSeguro;
	}

	public void setPlanSeguro(PlanSeguro planSeguro) {
		this.planSeguro = planSeguro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Integer usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	@Override
	public String toString() {
		return getId().toString();
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (!(obj instanceof PlanesSeguro)) {
				return false;
			} else {
				if (((PlanesSeguro) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
