package br.com.brunomateus.gestao_vagas.exceptions;


public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Este usuário ja existe");
    }
}