package br.com.brunomateus.gestao_vagas.modules.company.useCases;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.brunomateus.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.brunomateus.gestao_vagas.modules.company.dto.AuthResponseCompanyDTO;
import br.com.brunomateus.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
     private PasswordEncoder passwordEncoder;

     
    @Value("${security.token.secret}")
    private String secretKey;

    public AuthResponseCompanyDTO execute(AuthCompanyDTO authCompanyDTO){
            
        var company = this.companyRepository.findCompanyByUsername(authCompanyDTO.getUsername()).orElseThrow(
            ()->{
                throw new UsernameNotFoundException("Company is not found");
            });
        
       
                        
        //verifica se as senhas são iguais
        var passwordMatchers = this.passwordEncoder.matches(authCompanyDTO.getPassword(),company.getPassword());

            if(!passwordMatchers){
                throw new BadCredentialsException("Password does not match");
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            var expiresIn = Instant.now().plus(Duration.ofHours(2));

           var token = JWT.create()
           .withExpiresAt(expiresIn)
           .withIssuer("javagas")
           .withClaim("roles", Arrays.asList("COMPANY"))
           .withSubject(company.getId().toString()).sign(algorithm);

        var authResponseCompanyDTO = AuthResponseCompanyDTO
            .builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();
           
        return authResponseCompanyDTO;
    }
}