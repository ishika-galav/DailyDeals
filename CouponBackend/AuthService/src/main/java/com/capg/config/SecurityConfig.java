package com.capg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capg.jwt.EntryPointJwt;
import com.capg.jwt.TokenFilter;
import com.capg.service.UserDetailsServiceImpl;


@Configuration
@EnableGlobalMethodSecurity(		
		prePostEnabled = true)
public class SecurityConfig { 
	@Autowired
	EntryPointJwt epj;
	@Autowired
	TokenFilter tf;
	@Autowired
	UserDetailsServiceImpl uss;
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(uss);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
	@Bean
  public SecurityFilterChain doFilter(HttpSecurity http) throws Exception {
    http
    .csrf().disable()
    .exceptionHandling()
    	.authenticationEntryPoint(epj)
    	.and()
    .sessionManagement()
    	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	.and()
	.authorizeRequests()
		.requestMatchers("/app/**, /user/**, /coupon/getAllCoupons, /user/registerUser").permitAll()
		//.requestMatchers("/user/**").authenticated()
		.requestMatchers("/admin/**").authenticated()
		.requestMatchers("/coupons/**").authenticated();
    http.authenticationProvider(daoAuthenticationProvider());
    http.addFilterBefore(tf, UsernamePasswordAuthenticationFilter.class);	
		
    return http.build();
  }
}