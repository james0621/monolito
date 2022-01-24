package co.com.pragma.exception.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("status_code")
	private int statusCode;
	
	@JsonProperty("uri")
	private String uriRequested;
	
	
	
}
