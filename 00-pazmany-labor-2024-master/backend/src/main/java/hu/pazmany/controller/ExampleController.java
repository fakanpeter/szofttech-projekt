package hu.pazmany.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExampleController {
	private static final String OKAY_STRING = "OK";

	/**
	 * Provides an OK string
	 * @return An OK string
	 */

	@GetMapping("/ok")
	public String ok() {
		return OKAY_STRING;
	}
}
