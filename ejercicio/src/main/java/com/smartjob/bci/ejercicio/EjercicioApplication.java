package com.smartjob.bci.ejercicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartjob.bci.ejercicio.seguridad.JWTAuthorizationFilter;
import com.smartjob.bci.ejercicio.util.TablasH2;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan(basePackages = {"com.smartjob.bci.ejercicio"})
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
            /*http.cors().and().csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests().antMatchers(HttpMethod.POST, "/user/logeo").permitAll().anyRequest()
                    .authenticated();*/

            http.cors().and().csrf().disable()
	            .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
	            .authorizeRequests()
	            .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v2/api-docs/**", "/swagger-resources/**").permitAll()
	            .antMatchers(HttpMethod.POST, "/user/logeo").permitAll()
	            .anyRequest().authenticated();

        }
    }

    // Configuración de Swagger
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.smartjob.bci.ejercicio"))
                .paths(PathSelectors.any())
                .build();
    }
}
