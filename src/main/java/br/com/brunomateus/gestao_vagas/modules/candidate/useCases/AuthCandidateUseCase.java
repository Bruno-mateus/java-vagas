package br.com.brunomateus.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.brunomateus.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import br.com.brunomateus.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.brunomateus.gestao_vagas.modules.candidate.repository.CandidateRepository;

@Service
public class AuthCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

     @Value("${security.token.secret.candidate}")
    private String secretKey;

    public AuthCandidateResponseDTO execute(AuthCandidateDTO authCandidateDTO) throws AuthenticationException{
        //verify user exists
      var candidate =  this.candidateRepository.findByUsername(authCandidateDTO.username())
        .orElseThrow(()->{
            throw new UsernameNotFoundException("Username/Password incorrect");
        });
        
        //verify password
        var verifyPassword = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if(!verifyPassword){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
       var token = JWT.create()
        .withIssuer("javagas") 
        .withSubject(candidate.getId().toString()) 
        .withClaim("rules", Arrays.asList("candidate")) 
        .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10))) 
        .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder().access_token(token).build();
        
        return authCandidateResponse;
    }
}
