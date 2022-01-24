package co.com.pragma.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ImageMongoDto;
import co.com.pragma.exception.message.ErrorMessage;
import co.com.pragma.service.ImageMongoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("image")
public class ImageController {

	@Autowired
	private ImageMongoService imagenService;

	@Operation(summary = "Consulta una imagen por su ID", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageMongoDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<ImageMongoDto> getById(@PathVariable String id) {
		return new ResponseEntity<>(imagenService.getById(id), HttpStatus.OK);
	}

	@Operation(summary = "Permite el guardado de una imagen", method = "POST", tags = "save", responses = {
			@ApiResponse(responseCode = "201", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageMongoDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "500", description = "Error en el proceso", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping("/save")
	public ResponseEntity<ImageMongoDto> Save(@RequestParam MultipartFile file) {
		ImageMongoDto image = null;
		try {
			image = new ImageMongoDto(file, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(imagenService.save(image), HttpStatus.CREATED);
	}

	@Operation(summary = "Elimina una imagen por ID", method = "DELETE", tags = "delete", responses = {
			@ApiResponse(responseCode = "204", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageMongoDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		if (imagenService.getById(id) != null) {
			imagenService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
