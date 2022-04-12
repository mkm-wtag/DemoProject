package cinema.exception;

public class ResourceNotFoundException extends RuntimeException {
    private ErrorMessage errorMessage;

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResourceNotFoundException(String message) {
        this.errorMessage = new ErrorMessage();
        this.errorMessage.setMessage(message);
    }
}