package br.com.brunomateus.gestao_vagas.exceptions;

public class CompanyNotFound extends RuntimeException {
        public  CompanyNotFound(){
            super("Comapany not found");
        }
}
