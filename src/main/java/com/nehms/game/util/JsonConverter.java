package com.nehms.game.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter implements Converter<Object, String> {

	@Override
	public String convert(Object o) {
		Gson gson = new Gson();

		return gson.toJson(o);
	}
}
