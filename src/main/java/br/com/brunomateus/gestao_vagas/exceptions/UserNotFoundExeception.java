package br.com.brunomateus.gestao_vagas.exceptions;

public class UserNotFoundExeception extends RuntimeException{
   public UserNotFoundExeception(){
        super("Usuário não encontrado.");
   }
}
