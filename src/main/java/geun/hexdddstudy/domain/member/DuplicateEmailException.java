package geun.hexdddstudy.domain.member;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message){super(message);}
}
