package geun.hexdddstudy.domain.member;

public class DuplicateProfileException extends RuntimeException{
    public DuplicateProfileException(String message){super(message);}
}
