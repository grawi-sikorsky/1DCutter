package pl.js.onedcutter.exceptions.project;

public class ProjectDoesntExistException extends RuntimeException{

    public ProjectDoesntExistException(String message) {
        super(message);
    }
}
