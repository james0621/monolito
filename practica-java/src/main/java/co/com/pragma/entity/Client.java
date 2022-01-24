package co.com.pragma.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "clients")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(name = "last_name")
	private String lastName;
	
	// Tipo y Numero de documento de la persona
	@Column(name = "doc_type")
	private String docType;
	
	@Column(name = "num_doc", unique = true)
	private Long numDoc;
	
	//Edad de la persona
	private Integer age;

	//Ciudad de nacimiento de la persona
	@Column(name = "city_birth")
	private String cityBirth;
	
	//Fecha en la que se registro la persona
	@Column(name = "registration_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date regisDate;
	
	//Relacion con la imagen en mysql
	/*@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_image")
	private Image image;*/
	
	//Relación con la imagen en mongo
	private String idImage;
	
	@PrePersist
	private void init() {
		this.regisDate = new Date();
	}
		

}
