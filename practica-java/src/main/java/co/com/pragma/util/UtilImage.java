package co.com.pragma.util;

import co.com.pragma.exception.ImageTypeIncompatibilityException;

public class UtilImage {
	
	//Valido el tipo de la imagen solo se permite .png y .jpeg
	public static void validateImageType(String type) {
		if(!type.equals("image/jpeg") && !type.equals("image/png") && !type.equals("image/jpg"))
			throw new ImageTypeIncompatibilityException();		
		
	}

}
