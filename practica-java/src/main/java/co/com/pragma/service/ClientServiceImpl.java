package co.com.pragma.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.pragma.dto.ClientDto;
import co.com.pragma.entity.Client;
import co.com.pragma.exception.DataNoFoundException;
import co.com.pragma.repository.IClientRepository;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	private IClientRepository clientRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ClientDto> getAll() {
		List<ClientDto> clients = new ArrayList<ClientDto>();
		List<Client> clientsEntity = (List<Client>) clientRepo.findAll();
		
		clientsEntity.forEach(clientE -> { 
			ClientDto client = modelMapper.map(clientE, ClientDto.class);
			clients.add(client);
		});

		return clients;
	}

	@Override
	public ClientDto getById(Long id) {
		Client clientEntity = clientRepo.findById(id)
				.orElseThrow(()-> new DataNoFoundException());
		ClientDto client = modelMapper.map(clientEntity, ClientDto.class);
		return client;
	}

	@Override
	public ClientDto save(ClientDto client) {
		
		Client clientEntity = modelMapper.map(client, Client.class);
		clientEntity = clientRepo.save(clientEntity);
		return modelMapper.map(clientEntity, ClientDto.class);
	}

	@Override
	public boolean delete(Long id) {
		
		if(getById(id) != null) {
			clientRepo.deleteById(id);
			return true;
		}
		
		return false;

	}

	@Override
	public ClientDto getByDocTypeAndNumDoc(String docType, Long numDoc) {
		Client clientEntity = clientRepo.findByDocTypeAndNumDoc(docType, numDoc)
				.orElseThrow(() -> new DataNoFoundException());
		return modelMapper.map(clientEntity, ClientDto.class);
	}

	@Override
	public List<ClientDto> findByAgeGreatherThanEqual(Integer age) {
		List<ClientDto> clients =  new ArrayList<ClientDto>();
		clientRepo.findByAgeGreatherThanEqual(age).forEach(c -> {
			ClientDto client =  modelMapper.map(c, ClientDto.class);
			clients.add(client);
		});
		return clients;
	}
}
