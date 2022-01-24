package co.com.pragma.service;

import java.io.IOException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ImageDto;
import co.com.pragma.entity.Image;
import co.com.pragma.repository.ImageRepository;
import co.com.pragma.util.UtilImage;

@Service
@Transactional
public class ImageServiceImpl implements ImageService{
	

	@Autowired
	private ImageRepository imagenDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ImageDto getById(Long id) {
		
		Image imageEntity = imagenDao.findById(id).orElseThrow(()-> new NullPointerException()); 
		ImageDto image = modelMapper.map(imageEntity, ImageDto.class);		
		return image;
	}

	@Override
	public ImageDto save(MultipartFile file, Long id) {
		ImageDto image = null;
		Image imageEntity = null;
		if(file != null) {
			try {
				UtilImage.validateImageType(file.getContentType());
				String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
				image = new ImageDto(id,file.getContentType(),uniqueFilename, file.getSize(), file.getBytes());
				imageEntity = new Image();
				imageEntity = modelMapper.map(image, Image.class);
				imageEntity = imagenDao.save(imageEntity);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return imageEntity != null ? modelMapper.map(imageEntity, ImageDto.class) : image;
	}
	

	@Override
	public boolean delete(Long id) {
		if(getById(id) != null) {
			imagenDao.deleteById(id);
			return true;
		}
		return false;
		
	}
	
}
