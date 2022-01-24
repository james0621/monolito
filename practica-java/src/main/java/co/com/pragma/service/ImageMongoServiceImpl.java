package co.com.pragma.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.pragma.dto.ImageMongoDto;
import co.com.pragma.entity.ImageMongo;
import co.com.pragma.exception.DataNoFoundException;
import co.com.pragma.repository.ImageMongoRepository;

@Service
@Transactional
public class ImageMongoServiceImpl implements ImageMongoService {

	@Autowired
	private ImageMongoRepository imageRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ImageMongoDto getById(String id) {
		ImageMongo imageEntity = imageRepo.findById(id).orElseThrow(() -> new DataNoFoundException());
		ImageMongoDto image = modelMapper.map(imageEntity, ImageMongoDto.class);
		return image;
	}

	@Override
	public ImageMongoDto save(ImageMongoDto image) {
		ImageMongo imageEntity = modelMapper.map(image, ImageMongo.class);
		imageEntity = imageRepo.save(imageEntity);

		return modelMapper.map(imageEntity, ImageMongoDto.class);
	}

	@Override
	public boolean delete(String id) {
		if (getById(id) != null) {
			imageRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<ImageMongoDto> getByDocClient(Long numDoc) {
		List<ImageMongoDto> images = new ArrayList<ImageMongoDto>();
		imageRepo.findByDocClient(numDoc).forEach(i -> {
			ImageMongoDto imageM = modelMapper.map(i, ImageMongoDto.class);
			images.add(imageM);
		});
		return images;
	}

	@Override
	public boolean deleteAllByDocClient(Long numDoc) {
		List<ImageMongoDto> images = getByDocClient(numDoc);
		if (images != null && !images.isEmpty()) {
			images.forEach(i -> delete(i.getId()));
			return true;
		}

		return false;
	}

}
