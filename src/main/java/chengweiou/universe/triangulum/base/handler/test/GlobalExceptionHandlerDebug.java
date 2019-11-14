package chengweiou.universe.triangulum.base.handler.test;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Profile("!prod")
@RestControllerAdvice
public class GlobalExceptionHandlerDebug {

    @ExceptionHandler(ProjException.class)
    public Rest handleProjException(ProjException ex) {
        Rest rest = Rest.fail(ex.getCode());
        rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(ParamException.class)
    public Rest handleParamException(ParamException ex) {
        Rest rest = Rest.fail(BasicRestCode.PARAM);
        rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(BindException.class)
    public Rest handleParamException(BindException ex) {
        Rest rest = Rest.fail(BasicRestCode.PARAM);
        rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public Rest handleParamException(MissingRequestHeaderException ex) {
        Rest rest = Rest.fail(BasicRestCode.PARAM);
        rest.setMessage(ex.getHeaderName() + "cannot be null");
        return rest;
    }
    @ExceptionHandler(FailException.class)
    public Rest handleFailException(FailException ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);
        rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(Exception.class)
    public Rest handleException(Exception ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);
        rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public Rest handleParamException(NoHandlerFoundException ex) {
        Rest rest = Rest.fail(BasicRestCode.PARAM);
        rest.setMessage(ex.getMessage());
        return rest;
    }
}
