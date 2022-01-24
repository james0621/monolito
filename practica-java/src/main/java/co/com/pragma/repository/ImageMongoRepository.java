package co.com.pragma.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.pragma.entity.ImageMongo;

public interface ImageMongoRepository extends MongoRepository<ImageMongo, String>{
	
	public Iterable<ImageMongo> findByDocClient(Long docClient);

}
