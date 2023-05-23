package fr.knsrhuseyin.utils.downloader.utils;

public class KnsrUpdaterException extends RuntimeException {

    public KnsrUpdaterException()
    {
        super();
    }

    /**
     * Initialize the exception with an error message.
     * @param message error message.
     */
    public KnsrUpdaterException(String message)
    {
        super(message);
    }

    /**
     * Initialize the exception with an error message and a cause.
     * @param message error message.
     * @param cause cause.
     */
    public KnsrUpdaterException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Initialize the exception with a cause.
     * @param cause cause.
     */
    public KnsrUpdaterException(Throwable cause)
    {
        super(cause);
    }

}
