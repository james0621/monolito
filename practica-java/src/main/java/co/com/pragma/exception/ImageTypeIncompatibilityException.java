package co.com.pragma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.com.pragma.util.Message;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ImageTypeIncompatibilityException extends RuntimeException{
	
	public ImageTypeIncompatibilityException() {
		super(Message.IMAGE_TYPE_NOT_FOUND);
	}

}
