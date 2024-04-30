package eus.ehu.sharetrip.exceptions;

public class UnknownUser extends Exception {
    public UnknownUser() {
        super();
    }

    public UnknownUser(String s) {
        super(s);
    }
}
