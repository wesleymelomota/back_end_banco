package br.com.banco.user;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/usuario/{username}")
	public ResponseEntity<UserDto> getUser(@PathVariable String username){
		return new ResponseEntity<>(service.findUser(username), HttpStatus.OK);
	}
	@PostMapping("/usuario")
	public ResponseEntity<UserDto> createUser(@RequestBody Usuario user) {
		return new ResponseEntity<>( service.salvar(user), HttpStatus.CREATED);
	}
	@PutMapping("/usuario")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
		return new ResponseEntity<>(service.update(user), HttpStatus.OK);
	}
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
		return new ResponseEntity<>(service.delete(id));
	}
	
}
