package co.com.pragma.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ClientDto;
import co.com.pragma.dto.ImageMongoDto;
import co.com.pragma.exception.message.ErrorMessage;
import co.com.pragma.service.ClientService;
import co.com.pragma.service.ImageMongoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	/*
	 * Para relación con imagen mysql
	 * 
	 * @Autowired private ImageService imageService;
	 */

	@Autowired
	private ImageMongoService imageService;
	
	@Operation(
			summary = "Consulta todos los clientes registrados",
			method = "GET",
			tags = "read",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Operación exitosa",
							content = @Content(schema = @Schema(
									implementation = ClientDto.class)))
			})
	@GetMapping("/")
	public ResponseEntity<List<ClientDto>> getAll() {

		return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
				
	}

	@Operation(
			summary = "Consulta un cliente por su ID",
			method = "GET",
			tags = "read",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Operación exitosa",
							content = @Content(schema = @Schema(
									implementation = ClientDto.class))),
					@ApiResponse(
							responseCode = "400",
							description = "Envió de datos incorrectos",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class)))
			})
	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
		return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
	}
	
	
	@Operation(
			summary = "Consulta un cliente por tipo y numero de documento",
			method = "GET",
			tags = "read",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Operación exitosa",
							content = @Content(schema = @Schema(
									implementation = ClientDto.class))),
					@ApiResponse(
							responseCode = "400",
							description = "Envió de datos incorrectos",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class)))
			})
	@GetMapping("/search/doc")
	public ResponseEntity<ClientDto> getByDocTypeAndNumDoc(@RequestParam String docType, @RequestParam Long numDoc) {
		return new ResponseEntity<>(clientService.getByDocTypeAndNumDoc(docType, numDoc), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Consulta los clientes mayores o iguales a una edad",
			method = "GET",
			tags = "read",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Operación exitosa",
							content = @Content(schema = @Schema(
									implementation = ClientDto.class))),
					@ApiResponse(
							responseCode = "400",
							description = "Envió de datos incorrectos",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class)))
			})
	@GetMapping("search/age")
	public ResponseEntity<List<ClientDto>> getByAgeGreatherThanEqual(@RequestParam Integer age) {
		return clientService.findByAgeGreatherThanEqual(age).size() > 0
				? new ResponseEntity<>(clientService.findByAgeGreatherThanEqual(age), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Operation(
			summary = "Permite el guardado de un cliente",
			method = "POST",
			tags = "save",
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Operación exitosa",
							content = @Content(schema = @Schema(
									implementation = ClientDto.class))),
					@ApiResponse(
							responseCode = "400",
							description = "Envió de datos incorrectos",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(
							responseCode = "500",
							description = "Error en el proceso",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class)))
			})
	@PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> save(@Valid @RequestPart ClientDto client,
			@RequestParam("file") MultipartFile file) {

		if (file.getContentType() != null) {
			ImageMongoDto image =  null;
			try {
				image = new ImageMongoDto(file, client.getNumDoc());
			} catch (IOException e) {
				e.printStackTrace();
			}
			image = imageService.save(image);
			client.setIdImage(image.getId());
		}
		return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
	}
	
	
	@Operation(
			summary = "Permite actualizar un cliente por ID",
			method = "PUT",
			tags = "update",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Operación exitosa",
							content = @Content(schema = @Schema(
									implementation = ClientDto.class))),
					@ApiResponse(
							responseCode = "400",
							description = "Envió de datos incorrectos",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(
							responseCode = "500",
							description = "Error en el proceso",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class)))
			})
	@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> update(@Valid @RequestPart ClientDto client, @RequestParam MultipartFile file,
			@PathVariable Long id) {
		ClientDto clientResult = clientService.getById(id);
		// Capturo el id de la imagen para la relacion con mysql
//		Long idImagen = clientResult.getImage() != null ? clientResult.getImage().getId() : null;
		Date dateReg = clientResult.getRegisDate() != null ? clientResult.getRegisDate() : new Date();
		if (clientResult != null && clientResult.getId() != null) {
			client.setId(id);
			client.setRegisDate(dateReg);
			if (file.getContentType() != null) {
				ImageMongoDto image = null;
				try {
					image = new ImageMongoDto(file, client.getNumDoc());
				} catch (IOException e) { e.printStackTrace(); }
				image = imageService.save(image);
				client.setIdImage(image.getId());
			}
			client.setIdImage(clientResult.getIdImage());
		}

		return new ResponseEntity<>(clientService.save(client), HttpStatus.OK);
	}
	
	
	@Operation(
			summary = "Elimina un cliente por ID",
			method = "DELETE",
			tags = "delete",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "Operación exitosa",
							content = @Content(schema = @Schema(
									implementation = ClientDto.class))),
					@ApiResponse(
							responseCode = "400",
							description = "Envió de datos incorrectos",
							content = @Content(schema =  @Schema(implementation = ErrorMessage.class)))
			})
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (clientService.getById(id) != null) {
			/* Para eliminar la imagen en la relacion con mysql
			Long idImage = clientService.getById(id).getImage() != null ?
			 clientService.getById(id).getImage().getId(): null;*/
			Long docClient = clientService.getById(id).getNumDoc();
			clientService.delete(id);
			if (docClient != null)
				imageService.deleteAllByDocClient(docClient);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
