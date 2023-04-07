package weblux.jwt.models;

public class ExceptionModel extends RuntimeException {

    private final String msg;
    private final int statusCode;

    public ExceptionModel(String message, Throwable cause, String msg, int statusCode) {
        super(message, cause);
        this.msg = msg;
        this.statusCode = statusCode;
    }
    @Override
    public String getMessage() {
        return msg;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
