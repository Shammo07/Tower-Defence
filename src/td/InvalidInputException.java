package td;

/**
 * Our custom exception class. When a user enters invalid inputs, an
 * InvalidInputException
 * shall be triggered.
 */


public class InvalidInputException extends Exception{

    public InvalidInputException(String e){
        super(e);
    }
}

