package eus.ehu.sharetrip.exceptions;

public class CityDoesNotExistExeception extends Exception{
    private static final long serialVersionUID = 1L;

    public CityDoesNotExistExeception()
    {
        super();
    }
    /**This exception is triggered if the city does not exist
    *@param s String of the exception
    */
    public CityDoesNotExistExeception(String s)
    {
        super(s);
    }
}
