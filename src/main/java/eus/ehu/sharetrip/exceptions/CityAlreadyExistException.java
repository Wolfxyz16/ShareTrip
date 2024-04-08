package eus.ehu.sharetrip.exceptions;

public class CityAlreadyExistException extends Exception{
    private static final long serialVersionUID = 1L;

    public CityAlreadyExistException()
    {
        super();
    }

    public CityAlreadyExistException(String s)
    {
        super(s);
    }

}
