package com.nehms.game.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
	

	@GetMapping("/game/play")
	public String showGamePage() {
		
		return "acceuil";
	}

	
}