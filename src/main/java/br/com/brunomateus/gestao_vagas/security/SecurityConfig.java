package br.com.brunomateus.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//indicamos que vamos criar uma classe de configuracao para que o spring gerencie
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilterCompany securityFilterCompany;

    @Autowired
    SecurityFilterCandidate securityFilterCandidate;
    //indicamos que vamos definir um OBJ ja gerenciado pelo spring
    
    private static final String[] PERMIT_ALL_LIST = {
        "swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resource/**",
        "/actuator/**"
};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable())
        
        .authorizeHttpRequests(auth->{
            auth.requestMatchers("/candidate/create").permitAll()
            .requestMatchers("/company/create").permitAll()
            .requestMatchers("/company/auth").permitAll()
            .requestMatchers("/candidate/auth").permitAll()
            .requestMatchers("/candidate/profile").permitAll()
            .requestMatchers(PERMIT_ALL_LIST).permitAll();
            auth.anyRequest().authenticated();
        }
        ).addFilterBefore(securityFilterCandidate, BasicAuthenticationFilter.class)
        .addFilterBefore(securityFilterCompany,BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoded(){
        return new BCryptPasswordEncoder();
    }
}
