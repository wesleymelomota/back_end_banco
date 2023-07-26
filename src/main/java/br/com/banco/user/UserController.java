package br.com.banco.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.dtos.UserDto;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/banco")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/usuario")
	public UserDto create(@RequestBody Usuario user) {
		return service.salvar(user);
	}
}
