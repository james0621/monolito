package co.com.pragma.dto;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.util.UtilImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageMongoDto {
	

	private String id;
	private String type;
	private String name;
	private Long size;
//	private byte[] pixel;
	private String base64;
	private Long docClient;
	
	public ImageMongoDto(MultipartFile file, Long docClient) throws IOException {
		UtilImage.validateImageType(file.getContentType());
		this.type = file.getContentType();
		this.name = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		this.size = file.getSize();
		this.base64 = Base64.getEncoder().encodeToString(file.getBytes());
		this.docClient = docClient;
	}

}
