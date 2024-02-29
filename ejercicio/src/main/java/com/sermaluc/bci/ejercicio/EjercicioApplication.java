package com.sermaluc.bci.ejercicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sermaluc.bci.ejercicio.seguridad.JWTAuthorizationFilter;
import com.sermaluc.bci.ejercicio.util.TablasH2;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan(basePackages = {"com.sermaluc.bci.ejercicio"})
public class EjercicioApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	@Autowired
    private JdbcTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(EjercicioApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EjercicioApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        // Para 'moneda'
        TablasH2.crearTablaUser(template);
        TablasH2.insertarRegistrosTablaUser(template);
        // Para 'tipocambio'
        TablasH2.crearTablaPhone(template);
        TablasH2.insertarRegistrosTablaPhone(template);
    }

    @EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.POST, "/user/logeo").permitAll().anyRequest()
					.authenticated();

//			http.cors().and().csrf().disable() // TODO This might be modified in a future ticket.
//					.authorizeRequests().antMatchers(HttpMethod.POST, publicUrlsPost.toArray(new String[0])).permitAll()
//					.antMatchers(HttpMethod.POST, "/usuario/logeo").permitAll().anyRequest()
//					.authenticated().and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
//					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			http.addFilterBefore(jwtTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);

		}
	}
//        @Bean
//	public WebMvcConfigurer getCorsConfiguration() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
//						.allowedOrigins("http://localhost:4200").maxAge(3600);
//				WebMvcConfigurer.super.addCorsMappings(registry);
//			}
//		};
//
//	}

}
