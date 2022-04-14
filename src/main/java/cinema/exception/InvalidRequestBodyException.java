package cinema.exception;

import org.springframework.validation.BindingResult;

public class InvalidRequestBodyException extends RuntimeException {

    private BindingResult bindingResult;

    public InvalidRequestBodyException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}