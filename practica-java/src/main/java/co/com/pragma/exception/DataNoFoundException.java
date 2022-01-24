package co.com.pragma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class DataNoFoundException extends RuntimeException {
	
	public DataNoFoundException() {
		super("No se encontraron datos");
	}

}
