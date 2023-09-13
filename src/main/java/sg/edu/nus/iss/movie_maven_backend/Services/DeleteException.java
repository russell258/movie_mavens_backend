package sg.edu.nus.iss.movie_maven_backend.Services;

public class DeleteException extends Exception {
    public DeleteException(){}
    public DeleteException(String msg){
        super(msg);
    }
}
