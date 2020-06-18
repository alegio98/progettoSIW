package it.uniroma3.siw.authentication;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;


@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource; //modella punto di accesso al DB


	@Override
	public void configure (HttpSecurity http) throws Exception{
		//POLITICHE DI AUTENTICAZIONE E AUTORIZZAZIONE
		//serie di chiamate e invocazioni concatenate all'oggetto http
		//i metodi restituiscono lo stesso oggetto evoluto nel tempo
		http
		.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/","/index","/login","/users/register").permitAll()
		.antMatchers(HttpMethod.POST,"/login","/users/register").permitAll()
		.antMatchers(HttpMethod.GET,"/admin").hasAnyAuthority(ADMIN_ROLE)
		.anyRequest().authenticated() //qualsiasi altra richiesta si può fare solo se l'utente è autenticato
		.and().formLogin()	
		.defaultSuccessUrl("/home")
		.and().logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/index");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{

		auth.jdbcAuthentication()
		
		.dataSource(this.dataSource) //dove trovo i dati da utilizzare
		
		
		//Ho user_name per la convenzione cammello
		.authoritiesByUsernameQuery("SELECT user_name,role FROM credentials WHERE user_name=?") //come ottenre lo username ed i ruoli ad esso associati
		//ci restituisce lo username ed il ruolo trovati nella tabella credentials
		
		
		
		//come ottenere username e password ed un flag booleano sempre abilitato a 1.
		.usersByUsernameQuery("SELECT user_name,password, 1 as enabled FROM credentials WHERE user_name=?");
	}
	
	
	//per la password cifrata
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
