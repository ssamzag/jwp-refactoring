package kitchenpos.menu.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MenuRestControllerAdvice {
    @ExceptionHandler(kitchenpos.menu.exception.CustomException.class)
    public ResponseEntity<kitchenpos.menu.exception.ErrorResponse> handleCustomeException(CustomException e) {
        kitchenpos.menu.exception.ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }
}
