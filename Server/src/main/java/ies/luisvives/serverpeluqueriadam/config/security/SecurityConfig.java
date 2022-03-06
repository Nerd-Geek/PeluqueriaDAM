package ies.luisvives.serverpeluqueriadam.config.security;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import ies.luisvives.serverpeluqueriadam.config.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled  = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/appointments/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/appointments/").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/appointments/mobile").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/appointments/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/appointments/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/appointments/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/services/").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/services/all").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/services/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/services/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/services/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/services/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/").hasRole( "ADMIN")
                .antMatchers(HttpMethod.GET,  APIConfig.API_PATH + "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,  APIConfig.API_PATH + "/users/{usename}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,  APIConfig.API_PATH + "/users/{email}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,  APIConfig.API_PATH + "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,  APIConfig.API_PATH + "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/users/").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/users/login").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/me").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/logins/").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/logins/{id}").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
