package com.example.adverts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.example.adverts.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public MyWebSecurity(MyUserDetailsService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void configure(WebSecurity web) throws Exception { //not triggered
//        super.configure(web);
        web.ignoring()
                .antMatchers(SIGN_UP_URL);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers(SIGN_UP_URL).permitAll();
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
