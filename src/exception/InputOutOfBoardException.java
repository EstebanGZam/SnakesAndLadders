package exception;

public class InputOutOfBoardException extends Exception {
    private String message;

    public InputOutOfBoardException(String message) {
        super(message);
        this.message = message;

    }

    public String getMessage() {
        return message;

    }
}
