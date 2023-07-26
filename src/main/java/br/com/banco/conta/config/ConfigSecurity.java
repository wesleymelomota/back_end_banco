package br.com.banco.conta.config;

import java.security.Key;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
@ConfigurationProperties(prefix = "security.config")
public class ConfigSecurity {
	
	private static final String SECRETE_KEY = "4A404E635166546A576E5A7234753778214125442A472D4B6150645367556B58";
	public static String PREFIX;
	public static Long EXPIRATION;
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public void setPrefix(String prefix) {
		PREFIX = prefix;
	}
	public void setExpiration(Long exp) {
		EXPIRATION = exp;
	}
	/*public static Key getKey() {
		return key;
	}*/
	public static Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRETE_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

