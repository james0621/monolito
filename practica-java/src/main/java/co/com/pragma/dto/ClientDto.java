package co.com.pragma.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ClientDto {
	
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String docType;
	
	@NotNull
	private Long numDoc;	
	
	@Min(value = 18, message = "El cliente debe ser mayor de edad")
	private Integer age;
	private String cityBirth;
	private Date regisDate;
	
	//Relación imagen MySql
//	private ImageDto image;
	
	//Relación imagen mongo
	private String idImage;

}
