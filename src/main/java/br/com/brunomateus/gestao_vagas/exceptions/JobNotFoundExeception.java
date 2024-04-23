package br.com.brunomateus.gestao_vagas.exceptions;

public class JobNotFoundExeception extends RuntimeException {
    public JobNotFoundExeception(){
        super("Job Not found.");
    }
}
