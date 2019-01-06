package ua.com.pollapp.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Vote Dead Line has passed.")
public class VoteTimeOutException extends RuntimeException {
    public VoteTimeOutException(String message) {
        super(message);
    }
}