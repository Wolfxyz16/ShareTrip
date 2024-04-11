package eus.ehu.sharetrip.exceptions;

public class CityDoesNotExistException extends Exception{
    private static final long serialVersionUID = 1L;

    public CityDoesNotExistException()
    {
        super();
    }
    /**This exception is triggered if the question already exists
     *@param s String of the exception
     */
    public CityDoesNotExistException(String s)
    {
        super(s);
    }
}
