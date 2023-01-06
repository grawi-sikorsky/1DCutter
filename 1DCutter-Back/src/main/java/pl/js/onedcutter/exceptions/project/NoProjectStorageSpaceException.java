package pl.js.onedcutter.exceptions.project;

public class NoProjectStorageSpaceException extends RuntimeException{

    public NoProjectStorageSpaceException(String message) {
        super(message);
    }    
}
