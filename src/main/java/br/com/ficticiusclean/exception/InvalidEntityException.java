package br.com.ficticiusclean.exception;

public class InvalidEntityException extends RuntimeException {

    private static final long serialVersionUID = 7348298921252491470L;

    public InvalidEntityException(String message) {
        super(message);
    }
}
