package br.com.brunomateus.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//indicamos que vamos criar uma classe de configuracao para que o spring gerencie
@Configuration
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    //indicamos que vamos definir um OBJ ja gerenciado pelo spring
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable())
        .authorizeHttpRequests(auth->{
            auth.requestMatchers("/candidate/create").permitAll()
            .requestMatchers("/company/create").permitAll()
            .requestMatchers("/auth/company").permitAll();
            auth.anyRequest().authenticated();
        }
            ).addFilterBefore(this.securityFilter,BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoded(){
        return new BCryptPasswordEncoder();
    }
}
