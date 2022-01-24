package co.com.pragma.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ImageMongoDto;
import co.com.pragma.service.ImageMongoService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ImageControllerTest {
	
	@InjectMocks
	ImageController imageController;
	
	@Mock
	ImageMongoService imageService;
	
	@Mock
	ImageMongoDto image;
	
	ImageMongoDto img;
	
	MultipartFile file;
	
	private static final String ID = "1";
	
	@Before
	public void setUp() {
		File f = new File("C:/Users/james.cardona/eclipse-workspace/practica-java/src/test/java/co/com/pragma/controller/image/testing.jpg");
		try {
			InputStream inputStream = new FileInputStream(f);
			file = new MockMultipartFile("test", f.getName(), "image/jpg", inputStream);
			img = new ImageMongoDto(file, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void findByIdImageTest() {
		when(imageService.getById(ID)).thenReturn(image);
		assertEquals(imageController.getById(ID).getBody(), image);
	}
	
	@Test
	public void saveImageTest() {
		when(imageService.save(img)).thenReturn(img);
		assertEquals(imageController.Save(file).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	public void deleteImageTestWhenImageIsPresent() {
		when(imageService.getById(ID)).thenReturn(img);
		assertEquals(imageController.delete(ID).getStatusCode(),HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deleteImageTestWhenImageIsNotPresent() {
		when(imageService.getById(ID)).thenReturn(null);
		assertEquals(imageController.delete(ID).getStatusCode(),HttpStatus.NOT_FOUND);
	}

}
