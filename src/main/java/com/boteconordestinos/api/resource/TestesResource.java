package com.boteconordestinos.api.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boteconordestinos.api.model.Teste;


@RestController
@RequestMapping("/teste")
public class TestesResource {
	Teste aux = new Teste();
}
