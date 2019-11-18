package com.rlsp.socialbooks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Habilita a seguranca na WEB
 * @author Rodrigo
 *
 */

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	/*
	 * Configura o tipo de autenticacao que sera usada (Memoria, Banco de Dados, Arquivo)
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("rlsp")
			.password("{noop}12345")
			//.password("12345")
			.roles("USER");
	}

	/**
	 * Mostra como a autenticacao ira se proceder
	 * authorizeRequests() ==> como sera autorizada as requisicoes
	 * httpBasic() ==> metodo basico de autenticacao HTTP
	 * csrf() ==> proteger contra ataques (Para API que diferentes acessos nao eh legal estar "ENABLE")
	 */
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //permit acesso no Chrome (Libera a protecao em relacao ao CORS) ==> Libera autenticacao do OPTIONS do Chrome
	        .antMatchers("/h2-console/**")
	          .permitAll()
	          .anyRequest()
	          .authenticated().and().httpBasic();;
	          //.and().formLogin()
	          
	         
		http.csrf()
	          .ignoringAntMatchers("/h2-console/**")
	    	  .disable();
		
		http.headers()
	          .frameOptions()
	          .sameOrigin();
	}
}
