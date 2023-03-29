package com.aspire.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aspire.user.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//
//		registry.addMapping("/**").allowedMethods("*");
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
//		http.csrf().disable();
//		http.headers().frameOptions().disable(); 

		
		http.authorizeHttpRequests().antMatchers("/users","/token","/save","/user/*").permitAll().anyRequest().authenticated().and().httpBasic();
		http.csrf().disable();

		
//		http.csrf().disable().cors().disable().authorizeRequests().antMatchers("/token").permitAll().anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//		http
//			.csrf()
//			.disable()
//			.authorizeRequests()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.httpBasic();
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("USER");
//		.and().withUser("user").password("admin").roles("USER","");
		auth.userDetailsService(userService)
		.passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
