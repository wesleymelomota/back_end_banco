package br.com.banco.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.banco.dtos.UserDto;
import br.com.banco.enums.Roles;


@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public UserDto salvar(Usuario user) {
		
		Usuario usuario = new Usuario();
		UserDto userDto = new UserDto();
		usuario.setConta(user.getConta());
		usuario.setEmail(user.getEmail());
		usuario.setName(user.getName());
		usuario.setUsername(user.getUsername());
		usuario.setPassword(encoder.encode(user.getPassword()));
		usuario.setRole(Roles.USER);

		repository.save(usuario);
		return userDto.convert(user);
		
	}
	public UserDto update(UserDto user) {
		UserDto userDto = new UserDto();
		
		if(repository.existsById(user.getId())) {
			Usuario usuario = repository.findByid(user.getId());
			usuario.setEmail(user.getEmail());
			usuario.setName(user.getName());
			usuario.setUsername(user.getUsername());
			
			repository.save(usuario);
			return userDto.convert(usuario);
		}else {
			return userDto;
		}

	}
	public HttpStatus delete(Long id) {
		if(repository.existsById(id)) {
			repository.deleteById(id);
			
			return HttpStatus.ACCEPTED;
		}else {
			return HttpStatus.NOT_FOUND;
		}
	}
	public UserDto findUser(String username) {
		UserDto userDto = new UserDto();
		if(repository.existsByusername(username)) {
			
			return userDto.convert(repository.findByusername(username));
		}else {
			return userDto;
		}
	}
	
}