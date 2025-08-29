package com.globaltechadvisor.SpringSecurityEx.config;

import com.globaltechadvisor.SpringSecurityEx.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// For details : https://www.youtube.com/watch?v=d33a1pK4OYs&list=PLsyeobzWxl7qbKoSgR5ub6jolI8-ocxCF&index=34
// Spring Security | Custom Configuration "TELUSKO"
// This will tell springSecurity that we will be building our own configuration for security
//If we do not define anything on class then that means nothing will be there in the name of security
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    //When we need UserDetailsService, which will take data from database and
    // use this for authentication , Below I am using object of something which is already created
    /*
     * @Bean public UserDetailsService userDetailsService() {
     *
     * UserDetails user=User .withDefaultPasswordEncoder() .username("navin")
     * .password("n@123") .roles("USER") .build();
     *
     * UserDetails admin=User .withDefaultPasswordEncoder() .username("admin")
     * .password("admin@789") .roles("ADMIN") .build();
     *
     * return new InMemoryUserDetailsManager(user,admin); }
     */

    // This is where we are creating UserDetailsService from the database
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        //No password encoder
        // provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        //Using BCryptPasswordEncoder
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return provider;
    }


    /*     JWT Authentication Implement     */
    // Note that we need to first call JWTAuthentication filter before UserPasswordAuthenticationFilter


    //Note: Authentication manager will talk to AuthenticationProvider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }






    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Disabling the CSRF
        http.csrf(customizer -> customizer.disable());

        //No one should be accessing anything without login // Enabling Authentication
        //http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        //Note Till now no login form is there

        //Now Adding authentication for al except some
        http.authorizeHttpRequests(request ->
                request.requestMatchers("register","login")
                        .permitAll().anyRequest().authenticated());

        //Enabling form login
        //http.formLogin(Customizer.withDefaults());
        //Note: Till now we are not able to access from Postman etc as it will work only with their login form
        //Which is something we don't want. Like we have used Keyclock in previous project

        // This will enable us to access data by sending authentication header which will be used by any UI appication like postman
        http.httpBasic(Customizer.withDefaults());

        // Make my Http stateless so not to worry about sessionId
        http.sessionManagement(session
                -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Note that we need to first call JWTAuthentication filter before UserPasswordAuthenticationFilter
        // Because if it is verified/Logged earlier, then he wll not pass username and password
        // They will pass Jwt Token and if it is valid then skip the authentication
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }








}
