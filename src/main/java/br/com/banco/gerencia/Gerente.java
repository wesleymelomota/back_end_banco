package br.com.banco.gerencia;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.banco.emprestimo.Emprestimo;
import br.com.banco.enums.Roles;
@Entity
public class Gerente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true, nullable = false, length = 60)
	private String name;
	@Column(unique = true, nullable = false, length = 60)
	private String username;
	@Column(length = 150)
	private String password;
	@Enumerated(EnumType.STRING)
	private Roles role;
	@Column(unique = true, nullable = false, length = 60)
	private String email;
	
	public Gerente() {}
	
	public Gerente(String name, String username, String password, Roles role, String email) {
		 this.setName(name);
		 this.setUsername(username);
		 this.setPassword(password);
		 this.setRole(role.ADMIN);
		 this.setEmail(email);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
