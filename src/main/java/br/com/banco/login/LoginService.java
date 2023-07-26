package br.com.banco.login;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.banco.conta.config.ConfigSecurity;
import br.com.banco.conta.security.JwtCreator;
import br.com.banco.conta.security.JwtToken;
import br.com.banco.dtos.LoginDto;
import br.com.banco.user.UserRepository;
import br.com.banco.user.Usuario;

@Service
public class LoginService {
	@Autowired
	private UserRepository repoUser;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public Sessao checarUser(LoginDto login) {
		Usuario usuario = repoUser.findByusername(login.getUserName());
		if(usuario != null) {
			Boolean checkPass = encoder.matches(login.getPassword(), usuario.getPassword());
			
			if(!checkPass) {
				throw new RuntimeException("Erro! senha ou usuario invalido");
				
			}
			Sessao sessao = new Sessao();
			sessao.setUserName(usuario.getUsername());
			JwtToken token = new JwtToken();
			
			token.setExpiration(new Date(System.currentTimeMillis() + ConfigSecurity.EXPIRATION));
			token.setIssuedAT(new Date(System.currentTimeMillis()));
			token.setRoles(usuario.getRole().name());
			token.setSubject(usuario.getUsername());
			sessao.setToken(JwtCreator.create(ConfigSecurity.PREFIX, ConfigSecurity.getKey(), token));
			
			return sessao;
		}else {
			
			throw new RuntimeException("ERRO! Usario invalido");
		}
	}
} 
