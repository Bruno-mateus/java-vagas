    package br.com.brunomateus.gestao_vagas.exceptions;

    import java.util.ArrayList;
    import java.util.List;

    import org.springframework.context.MessageSource;
    import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;

    //ControllerAdvice é uma classe controladora onde conseguimos tratar nossas exceçoes
    @ControllerAdvice
    public class ExceptionHandleController {

        //pegara nossa mensagem de erro
        private MessageSource messageSource;
        //constructor
        public ExceptionHandleController(MessageSource message){
                this.messageSource = message;
            }
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<List<ErrorMessageDTO>> handleNotValidationException(MethodArgumentNotValidException e) {
            //onde iremos armazenar os erros
            List<ErrorMessageDTO> dto = new ArrayList<>();
            e.getBindingResult().getFieldErrors().forEach(error -> {
                // deixa o erro de forma mais amigavel
                @SuppressWarnings("null")
                String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                //pegamos cada msg de erro e campo e passamos para o err
                ErrorMessageDTO err = new ErrorMessageDTO(message, error.getField());
                //passa os erros para nosso array
                dto.add(err);
            });
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        }
    }


    