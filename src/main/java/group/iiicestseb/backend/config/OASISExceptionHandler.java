package group.iiicestseb.backend.config;

import group.iiicestseb.backend.utils.CSVUtil;
import group.iiicestseb.backend.vo.Response;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jh
 * @date 2020/3/4
 */
@RestControllerAdvice
public class OASISExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response validateErrorException(MethodArgumentNotValidException ex) {
        final FieldError error = ex.getBindingResult().getFieldError();
        String errorMessage;
        if (error != null) {
            errorMessage = error.getDefaultMessage();
        } else {
            errorMessage = ex.getMessage();
        }
        return Response.buildFailure(errorMessage);
    }

    @ExceptionHandler(CSVUtil.CSVException.class)
    public Response CSVErrorException(CSVUtil.CSVException ex){
        return Response.buildFailure(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response assertErrorException(IllegalArgumentException ex) {
        return Response.buildFailure(ex.getMessage());
    }


}
