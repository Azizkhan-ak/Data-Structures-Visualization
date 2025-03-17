package datastructures;

public class TerminationException extends RuntimeException {
    private Object data;

    public TerminationException() {
        super();
    }

    public TerminationException(String message) {
        super(message);
    }

    public TerminationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TerminationException(Throwable cause) {
        super(cause);
    }

    public TerminationException(Object data) {
        super();
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
