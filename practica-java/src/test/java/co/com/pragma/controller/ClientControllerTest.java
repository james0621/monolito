package co.com.pragma.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ClientDto;
import co.com.pragma.dto.ImageMongoDto;
import co.com.pragma.service.ClientService;
import co.com.pragma.service.ImageMongoService;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {
	
	@InjectMocks
	ClientController clientController;
	
	@Mock 
	ClientService clientService;
	
	@Mock
	ClientDto clientDto;
	
	@Mock
	MultipartFile file;
	
	@Mock
	ImageMongoService imageService;
	
	@Mock
	ImageMongoDto image; 
	
	private static final Long ID = 1L;
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	
	@Test
	public void findByIdClientTest() {
		when(clientService.getById(ID)).thenReturn(clientDto);
		assertEquals(clientController.getById(ID).getBody(), clientDto);
	}
	
	@Test
	public void findAllClientTest() {
		List<ClientDto> clients = new ArrayList<ClientDto>();
		clients.add(clientDto);
		when(clientService.getAll()).thenReturn(clients);
		assertEquals(clientController.getAll().getBody().size(), clients.size());
	}
	
	
	@Test
	public void saveClientTestWhenImageIsNull() {
		assertEquals(clientController.save(clientDto, file).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	public void deleteClientTestWhenClientIsPresent() {
		when(clientService.getById(ID)).thenReturn(clientDto);
		assertEquals(clientController.delete(ID).getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deleteClientTestWhenClientIsNotPresent() {
		when(clientService.getById(ID)).thenReturn(null);
		assertEquals(clientController.delete(ID).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void updateClientTest() {
		when(clientService.getById(ID)).thenReturn(clientDto);
		assertEquals(clientController.update(clientDto, file, ID).getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void findByDocTypeAndNumDocClientTest() {
		String docType = "CC";
		when(clientService.getByDocTypeAndNumDoc(docType, ID)).thenReturn(clientDto);
		assertEquals(clientController.getByDocTypeAndNumDoc("CC", ID).getBody(), clientDto);
	}
	
	@Test
	public void findByAgeGreatherThanEqualClientTest() {
		int age = 30;
		List<ClientDto> clients = new ArrayList<ClientDto>();
		clients.add(clientDto);
		when(clientService.findByAgeGreatherThanEqual(30)).thenReturn(clients);
		assertEquals(clientController.getByAgeGreatherThanEqual(age).getBody().size(), clients.size());
	}
	
}
