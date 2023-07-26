package br.com.banco.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.dtos.LoginDto;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/banco/login")
public class LoginController {

	@Autowired
	private LoginService service;
	
	@PostMapping
	public Sessao logar(@RequestBody LoginDto login) {
		return service.checarUser(login);
	}
}
