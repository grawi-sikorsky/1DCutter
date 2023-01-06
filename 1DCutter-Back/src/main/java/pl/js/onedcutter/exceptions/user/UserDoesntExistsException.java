package pl.js.onedcutter.exceptions.user;

public class UserDoesntExistsException extends RuntimeException {

    public UserDoesntExistsException(String message) {
        super(message);
    }
    
}
