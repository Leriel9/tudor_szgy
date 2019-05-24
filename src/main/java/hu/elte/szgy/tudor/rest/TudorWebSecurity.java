package hu.elte.szgy.tudor.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class TudorWebSecurity extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) 
      throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("tesztelek").password("teszt").roles("ADMIN");
    }
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {    	
    	http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/user/self").authenticated()
                .antMatchers(HttpMethod.GET,"/user/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/user/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/user/password").authenticated()
                .antMatchers(HttpMethod.POST,"/user/password/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/user/delete/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/kerdes/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/kerdes/szakterulet/*").hasRole("TUDOR")
                .antMatchers(HttpMethod.GET,"/kerdes/bongeszes").hasRole("UGYFEL")
                .antMatchers(HttpMethod.POST,"/kerdes/new").hasRole("UGYFEL")
                .antMatchers(HttpMethod.POST,"/kerdes/delete/*").hasAnyRole("ADMIN", "UGYFEL")
                .antMatchers(HttpMethod.POST,"/kerdes/masolat/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/valasz/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/valasz/tudor/*").hasRole("TUDOR")
                .antMatchers(HttpMethod.POST,"/valasz/new").hasRole("TUDOR")
                .antMatchers(HttpMethod.POST,"/valasz/ertekeles/*").hasRole("UGYFEL")
                .antMatchers(HttpMethod.POST,"/valasz/delete/*").hasAnyRole("ADMIN", "TUDOR")                
                .and()
            .csrf().disable()
            .formLogin()
                .loginPage("/login")
                .successForwardUrl( "/user/dispatch" )
                .defaultSuccessUrl("/kerdes_home.html", true)
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
		return new TudorUserService();
    }
}
