package bo.com.spaps.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Cita
 *
 */
@Entity
@Table(name = "cita", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Cita implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 1)
	@Column(name = "asistencia", nullable = false)
	private String asistencia;
	@Size(max = 2)
	@Column(name = "clase", nullable = false)
	private String clase;
	@Column(name = "fecha_atencion", nullable = false)
	private Date fechaAtencion;
	@Column(name = "hora", nullable = false)
	private Date hora;
	@Size(max = 255)
	@Column(name = "motivo", nullable = true)
	private String motivo;
	@Size(max = 255)
	@Column(name = "observacion", nullable = true)
	private String observacion;
	@Size(max = 2)
	@Column(name = "origen", nullable = false)
	private String origen;
	@Size(max = 2)
	@Column(name = "medio_registro", nullable = false)
	private String medioRegistro;
	@Size(max = 1)
	@Column(name = "tipo", nullable = false)
	private String tipo;
	@ManyToOne
	@JoinColumn(name = "id_agenda", nullable = false)
	private Agenda agenda;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_individuo", nullable = false)
	private Individuo individuo;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_personal", nullable = false)
	private Personal personal;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_especialidad", nullable = false)
	private Especialidad especialidad;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private Integer usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public Cita() {
		super();
		this.id = 0;
		this.asistencia = "";
		this.clase = "";
		this.motivo = "";
		this.observacion = "";
		this.origen = "";
		this.medioRegistro = "";
		this.clase = "";
		this.tipo = "";
		this.agenda = new Agenda();
		this.individuo = new Individuo();
		this.personal = new Personal();
		this.especialidad = new Especialidad();
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = 0;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsistencia() {
		return this.asistencia;
	}

	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Date getFechaAtencion() {
		return this.fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getMedioRegistro() {
		return this.medioRegistro;
	}

	public void setMedioRegistro(String medioRegistro) {
		this.medioRegistro = medioRegistro;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Individuo getIndividuo() {
		return individuo;
	}

	public void setIndividuo(Individuo individuo) {
		this.individuo = individuo;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Date getFechaModificacion() {
		return fechaAtencion;
	}

	public void setFechaModificacion(Date fecha_modificacion) {
		this.fechaAtencion = fecha_modificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fecha_registro) {
		this.fechaRegistro = fecha_registro;
	}

	public Integer getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Integer usuario_registro) {
		this.usuarioRegistro = usuario_registro;
	}

	@Override
	public String toString() {
		return getFechaAtencion().toString();
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
			if (!(obj instanceof Cita)) {
				return false;
			} else {
				if (((Cita) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
