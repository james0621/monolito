package co.com.pragma.exceptionhandler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.com.pragma.exception.DataNoFoundException;
import co.com.pragma.exception.ImageTypeIncompatibilityException;
import co.com.pragma.exception.message.ErrorMessage;
import co.com.pragma.util.Message;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<ErrorMessage> argumentNoValidException(HttpServletRequest request,
			InvalidDataAccessResourceUsageException e) {
		// Creo el mensaje para devolverlo en formato json
		ErrorMessage error = new ErrorMessage(Message.NOT_FOUND_ATRIBUTE, HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorMessage> dataIntegrityViolationException(HttpServletRequest request,
			DataIntegrityViolationException e) {
		ErrorMessage error = new ErrorMessage(Message.DATA_DUPLICATE, HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataNoFoundException.class)
	public ResponseEntity<ErrorMessage> nullPointerException(HttpServletRequest request, DataNoFoundException e) {
		ErrorMessage error = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI());

		return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentNoValidException(HttpServletRequest request,
			MethodArgumentNotValidException e) {

		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		StringBuilder message = new StringBuilder();
		fieldErrors.forEach(field -> message.append(field.getField() + ": " + field.getDefaultMessage() + ". "));
		ErrorMessage error = new ErrorMessage(message.toString(), HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorMessage> bindException(HttpServletRequest request,
			BindException e) {

		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		StringBuilder message = new StringBuilder();
		fieldErrors.forEach(field -> message.append(field.getField() + ": " + field.getDefaultMessage() + ". "));
		ErrorMessage error = new ErrorMessage(message.toString(), HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(ImageTypeIncompatibilityException.class)
	public ResponseEntity<ErrorMessage> imageTypeIncompatibilityException(HttpServletRequest request, ImageTypeIncompatibilityException e){
		ErrorMessage error = new ErrorMessage(e.getMessage(), HttpStatus.FORBIDDEN.value(), request.getRequestURI());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
}
