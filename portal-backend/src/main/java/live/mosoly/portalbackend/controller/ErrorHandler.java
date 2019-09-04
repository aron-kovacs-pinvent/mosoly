package live.mosoly.portalbackend.controller;

import live.mosoly.portalbackend.model.dto.RestResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {

        log.error(ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                ErrorResponse.builder().code("INT_ERROR_500").message(ex.getMessage()).build(),
                status
        );
    }

    @Data
    @Builder
    private static class ErrorResponse {
        private String code;
        private String message;
    }

}
