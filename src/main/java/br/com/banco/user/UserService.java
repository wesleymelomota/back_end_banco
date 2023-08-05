package br.com.banco.user;

import org.springframework.beans.factory.annotation.Autowired;
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
}