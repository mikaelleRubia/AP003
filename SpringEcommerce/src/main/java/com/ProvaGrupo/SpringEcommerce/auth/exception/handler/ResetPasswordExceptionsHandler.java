package com.ProvaGrupo.SpringEcommerce.auth.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ProvaGrupo.SpringEcommerce.auth.exception.RestErrorMessage;
import com.ProvaGrupo.SpringEcommerce.auth.exception.domain.reset.password.MissingArgumentsToResetPasswordException;
import com.ProvaGrupo.SpringEcommerce.auth.exception.domain.reset.password.PasswordsDoNotMatchException;

/**
 * This class is responsible for handling exceptions related to reset password.
 */
@ControllerAdvice
public class ResetPasswordExceptionsHandler extends ResponseEntityExceptionHandler {
    
    /**
     * This method handles MissingArgumentsToResetPasswordException. It returns a response with status 400.
     * @param ex MissingArgumentsToResetPasswordException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(MissingArgumentsToResetPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleMissingArgumentsToResetPasswordException(MissingArgumentsToResetPasswordException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    /**
     * This method handles PasswordsDoNotMatchException. It returns a response with status 400.
     * @param ex PasswordsDoNotMatchException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(PasswordsDoNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

}
