package com.boteconordestinos.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boteconordestinos.api.model.Teste;
import com.boteconordestinos.api.repository.TesteRepository;

@RestController
@RequestMapping("/teste")
public class TestesResource {
	
	@Autowired
	TesteRepository testeRepository;
	
	@GetMapping("/list")
	public List<Teste> listAll() {
		return testeRepository.findAll();
	}
}
