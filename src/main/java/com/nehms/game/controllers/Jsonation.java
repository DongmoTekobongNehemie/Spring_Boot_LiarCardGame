package com.nehms.game.controllers;

import com.google.gson.Gson;

public class Jsonation {

	public String convertToJson(Object object) {
		
		Gson gson = new Gson();
		
		return gson.toJson(object);
		
	}
}
