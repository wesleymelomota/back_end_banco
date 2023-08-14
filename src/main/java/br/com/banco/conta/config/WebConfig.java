package br.com.banco.conta.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.banco.conta.security.FilterJwt;

@Configuration
@EnableWebSecurity
public class WebConfig {
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.cors().and().csrf().disable()
		.addFilterBefore(new FilterJwt(), UsernamePasswordAuthenticationFilter.class)
		.authorizeHttpRequests()
		.antMatchers(HttpMethod.POST, "/banco/usuario").permitAll()
		.antMatchers(HttpMethod.POST, "/banco/login").permitAll()
		.antMatchers(HttpMethod.POST, "/banco/conta").permitAll()
		.antMatchers(HttpMethod.GET, "/banco/contas").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.GET, "/banco/transferencia/{numeroConta}").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.POST, "/banco/transaction/deposito").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.POST, "/banco/saque").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.GET, "/banco/transactions").hasAnyAuthority("ADMIN","USER")
		.antMatchers(HttpMethod.GET, "/banco/transaction/{id}").hasAnyAuthority("USER", "ADMIN")
		.antMatchers(HttpMethod.GET, "/banco/saldo/{numeroConta}").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.POST, "/banco/transferencia").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.GET, "/banco/transferencias").hasAnyAuthority("ADMIN", "USER")
		.anyRequest().authenticated().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}
	@Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); 
    }
	
}

