package br.com.banco.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.dtos.LoginDto;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/banco")
public class LoginController {

	@Autowired
	private LoginService service;
	
	@PostMapping("/login")
	public ResponseEntity<Sessao> logar(@RequestBody LoginDto login)  {
		try {
			Sessao sessao = service.checarUser(login);
			return new ResponseEntity<>( sessao, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}
}
