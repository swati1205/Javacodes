package com.javawebservice;

public class WebService {

	public String WebService(String animal) {
		// TODO Auto-generated method stub
		String animalType = "";
		if ("Lion".equals(animal)) {
			animalType = "Wild";
		} else if ("Dog".equals(animal)) {
			animalType = "Domestic";
		} else {
			animalType = "I don't know!";
		}
		return animalType;
	}
	}


