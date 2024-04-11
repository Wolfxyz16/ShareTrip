package eus.ehu.sharetrip.exceptions;

public class AlertAlreadyExistException extends Exception{
private static final long serialVersionUID = 1L;

    public AlertAlreadyExistException()
    {
        super();
    }

    public AlertAlreadyExistException(String s)
    {
        super(s);
    }
}
