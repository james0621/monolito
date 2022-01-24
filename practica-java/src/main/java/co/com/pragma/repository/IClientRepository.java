package co.com.pragma.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.pragma.entity.Client;

public interface IClientRepository extends CrudRepository<Client, Long>{
	
	public Optional<Client> findByDocTypeAndNumDoc(String docType, Long numDoc);
	
	@Query("SELECT c FROM Client c WHERE c.age >= ?1")
	public Iterable<Client> findByAgeGreatherThanEqual(Integer age);

}
