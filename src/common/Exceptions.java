package common;

public class Exceptions extends Exception{
    /**
     * Construct with a helpful message.
     *
     * @param msg exception message
     */
    public Exceptions(String msg) {
        super(msg);
    }

    /**
     * Construct with a Throwable
     * @param cause the Throwable cause
     */
    public Exceptions(Throwable cause) {
        super(cause);
    }
}
