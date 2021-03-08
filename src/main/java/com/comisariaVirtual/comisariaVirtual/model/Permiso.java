package com.comisariaVirtual.comisariaVirtual.model;
import javax.persistence.*;

@Entity
@Table(name = "permisos")
public class Permiso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "rut")
	private String rut;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "email")
    private String email;
    
    @Column(name = "motivo")
    private String motivo;
    
    @Column(name = "fechaHoraInicio")
	private String fechaHoraInicio;
	
	@Column(name = "fechaHoraFin")
	private String fechaHoraFin;

	public Permiso() {
        
    }
    
    public Permiso(String rut, String nombre, String direccion, String email, String motivo, String fechaHoraInicio, String fechaHoraFin) {
		this.rut = rut;
		this.nombre = nombre;
		this.direccion = direccion;
		this.email = email;
        this.motivo = motivo;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
	}

	public Long getId(){
		return Id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
        this.rut = rut;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
    }

    public String getMotivo() {
		return motivo;
	}
    
    public void setMotivo(String motivo) {
		this.motivo = motivo;
    }

    public String getFechaHoraInicio() {
		return fechaHoraInicio;
	}
    
    public void setFechaHoraInicio(String fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	
	public String getFechaHoraFin() {
		return fechaHoraFin;
	}
    
    public void setFechaHoraFin(String fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
    }
    
}