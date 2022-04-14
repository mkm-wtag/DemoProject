package cinema.exception;

public class CustomerLoginException extends RuntimeException {

    private ErrorMessage errorMessage;

    public CustomerLoginException(String message) {
        this.errorMessage = new ErrorMessage();
        this.errorMessage.setMessage(message);
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}