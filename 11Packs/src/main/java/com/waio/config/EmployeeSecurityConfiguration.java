package com.waio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class EmployeeSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    DataSource dataSource;

	//Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/api/1.0/matches").permitAll()
		.antMatchers("/api/job/1.0/matchesAPI").permitAll()
		.antMatchers("/api/1.0/leagues/*").permitAll()
		.antMatchers("/api/1.0/winningBreakup/*").permitAll()
		.antMatchers("/api/1.0/squad/*").permitAll()
		.antMatchers("/api/1.0/createSquad").permitAll()
		.antMatchers("/api/1.0/joinLeague").permitAll()
		.antMatchers("/api/1.0/allMatchesTeams/*").permitAll()
		.antMatchers("/api/1.0/teamsOfMatch/*/*").permitAll()
		.antMatchers("/api/1.0/teamView/*/*/*").permitAll()
		.antMatchers("/api/1.0/teamEdit/*/*/*").permitAll()
		.antMatchers("/api/job/1.0/createLeague").permitAll()
		.antMatchers("/welcome").hasAnyRole("USER", "ADMIN")
		.antMatchers("/getEmployees").hasAnyRole("USER", "ADMIN")
		.antMatchers("/addNewEmployee").hasAnyRole("ADMIN")
		.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
				.and().logout().permitAll();

		http.csrf().disable();
	}
	
	//@Override
    /*public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin",
                        "Access-Control-Allow-Methods",
                        "Access-Control-Allow-Headers",
                        "Access-Control-Max-Age",
                        "Access-Control-Request-Headers",
                        "Access-Control-Request-Method")
                .maxAge(3600);

        // Add more mappings...
    }*/
	//@Autowired
	//public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
	//	authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin").authorities("ROLE_USER").and()
	//			.withUser("javainuse").password("javainuse").authorities("ROLE_USER", "ROLE_ADMIN");
	//}


}