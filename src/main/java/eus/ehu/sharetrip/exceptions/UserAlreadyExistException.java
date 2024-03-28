package eus.ehu.sharetrip.exceptions;

public class UserAlreadyExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistException()
    {
        super();
    }
    /**This exception is triggered if the user already exists
    *@param s String of the exception
    */
    public UserAlreadyExistException(String s)
    {
        super(s);
    }
}
