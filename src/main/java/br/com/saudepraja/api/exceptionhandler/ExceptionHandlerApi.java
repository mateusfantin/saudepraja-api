package br.com.saudepraja.api.exceptionhandler;

import br.com.saudepraja.domain.exception.SaudePrajaBusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerApi extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SaudePrajaBusinessException.class)
    public ResponseEntity<?> handleSaudePrajaBusinessException(SaudePrajaBusinessException ex, WebRequest webRequest) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String detail = ex.getMessage();

        Problem problem = Problem.builder()
                .status(status.value())
                .detail(detail)
                .dataHora(LocalDateTime.now())
                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, webRequest);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        String reasonPhrase = (statusCode instanceof HttpStatus)
                ? ((HttpStatus) statusCode).getReasonPhrase()
                : "Erro";

        int statusValue = statusCode.value();

        if(body == null) {
            body = Problem.builder()
                    .status(statusCode.value())
                    .title(reasonPhrase)
                    .dataHora(LocalDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(statusCode.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
