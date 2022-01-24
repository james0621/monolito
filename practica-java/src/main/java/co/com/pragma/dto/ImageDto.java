package co.com.pragma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
	

	private Long id;
	private String type;
	private String name;
	private Long size;
	private byte[] pixel;

}
