package br.com.banco.conta.security;

import java.security.Key;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtCreator {
	public static final String AUTHORIZATION = "Authorization";
	public static final String ROLES_AUTHORITIES = "authorities";
	
	public static String create(String prefix, Key key, JwtToken jwt) {
		String token = Jwts.builder().setIssuedAt(jwt.getIssuedAT())
				.setExpiration(jwt.getExpiration())
				.setSubject(jwt.getSubject())
				.claim(ROLES_AUTHORITIES, jwt.getRoles())
				.signWith(key)
				.compact();
		return prefix + " " + token;
	}
	public static JwtToken create(String token, String prefix, Key key ) {
		token = token.replace(prefix, "");
		Claims jwt = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		JwtToken jwtObject = new JwtToken();
		jwtObject.setSubject(jwt.getSubject());
		jwtObject.setExpiration(jwt.getExpiration());
		jwtObject.setIssuedAT(jwt.getIssuedAt());
		jwtObject.setRoles((List) jwt.get(ROLES_AUTHORITIES));
		
		return jwtObject;
	}
}
