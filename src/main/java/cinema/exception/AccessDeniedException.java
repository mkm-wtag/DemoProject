package cinema.exception;


public class AccessDeniedException extends RuntimeException {
    private ErrorMessage errorMessage;

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AccessDeniedException(String message) {
        this.errorMessage = new ErrorMessage();
        this.errorMessage.setMessage(message);
    }
}
