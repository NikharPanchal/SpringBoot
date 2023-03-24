package com.aspire.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aspire.user.utils.Users;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//
//		registry.addMapping("/**").allowedMethods("*");
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
		http.authorizeHttpRequests().antMatchers("/users","/save").permitAll().antMatchers("/users","/save").hasRole("USER");
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.inMemoryAuthentication().withUser("admin").password(this.passwordEncoder().encode("admin")).roles("USER");
	}
}
