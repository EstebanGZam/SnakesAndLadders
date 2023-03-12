package exception;

public class ZeroInputException extends Exception{

    private String message;

    public ZeroInputException(String message) {
        super(message);
        this.message = message;

    }

    public String getMessage() {
        return message;

    }

}