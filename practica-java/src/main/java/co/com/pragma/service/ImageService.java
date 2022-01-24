package co.com.pragma.service;

import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ImageDto;

public interface ImageService {
	
	ImageDto getById(Long id);
	ImageDto save(MultipartFile file, Long id);
	boolean delete(Long id);

}
