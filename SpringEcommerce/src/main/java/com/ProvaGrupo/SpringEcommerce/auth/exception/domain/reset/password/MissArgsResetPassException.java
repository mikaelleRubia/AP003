package com.ProvaGrupo.SpringEcommerce.auth.exception.domain.reset.password;

public class MissArgsResetPassException extends RuntimeException {
    public MissArgsResetPassException() {
        super("Missing arguments to reset password");
    }
}
