//package com.hraf.sec;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("1234")).roles("ADMIN","USER");
//        auth.inMemoryAuthentication().withUser("user").password(encoder.encode("2345")).roles("USER");
//
//    }
//
////    protected void configure(HttpSecurity http) throws Exception {
////       http.formLogin();
////       //http.authorizeRequests().anyRequest().authenticated();
////        http.authorizeRequests().antMatchers("/operation", "/consultercompte").hasRole("USER");
////        http.authorizeRequests().antMatchers("/getClientForm", "/saveOperation","/saveClient","/clients","/comptes","/saveCompte")
////                .hasRole("ADMIN");
////        http.logout().permitAll();
////        http.exceptionHandling().accessDeniedPage("/403");
////
////    }
//}
