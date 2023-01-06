package pl.js.onedcutter.exceptions.user;

public class RegisterErrorException extends RuntimeException{

    public RegisterErrorException(String message) {
        super(message);
    }
}