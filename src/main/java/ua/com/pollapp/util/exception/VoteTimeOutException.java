package ua.com.pollapp.util.exception;

public class VoteTimeOutException extends RuntimeException {
    public VoteTimeOutException(String message) {
        super(message);
    }
}