package co.com.pragma.service;

import java.util.List;

import co.com.pragma.dto.ImageMongoDto;

public interface ImageMongoService {
	
	ImageMongoDto getById(String id);
	List<ImageMongoDto> getByDocClient(Long numDoc);
	ImageMongoDto save(ImageMongoDto image);
	boolean delete(String id);
	boolean deleteAllByDocClient(Long numDoc);

}
