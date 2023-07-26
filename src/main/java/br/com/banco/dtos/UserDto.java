package br.com.banco.dtos;

import org.springframework.beans.BeanUtils;
import br.com.banco.user.Usuario;

public class UserDto {
	
	private Long id;
	private String name;
	private String username;
	private String email;
	
	
	public UserDto convert(Usuario user) {
		BeanUtils.copyProperties(user, this, "password");
		return this;
	}
	public UserDto() {}
	public UserDto(Usuario user) {
		this.id = user.getId();
		this.name = user.getName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
	
}