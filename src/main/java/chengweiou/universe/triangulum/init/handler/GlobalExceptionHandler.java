package chengweiou.universe.triangulum.init.handler;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjException.class)
    public Rest handleProjException(ProjException ex) {
        Rest rest = Rest.fail(ex.getCode());

        // todo if debug
        if (1==1) rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(ParamException.class)
    public Rest handleParamException(ParamException ex) {
        Rest rest = Rest.fail(BasicRestCode.PARAM);

        // todo if debug
        if (1==1) rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public Rest handleParamException(MissingRequestHeaderException ex) {
        Rest rest = Rest.fail(BasicRestCode.PARAM);

        // todo if debug
        if (1==1) rest.setMessage(ex.getHeaderName() + "cannot be null");
        return rest;
    }
    @ExceptionHandler(FailException.class)
    public Rest handleFailException(FailException ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);

        // todo if debug
        if (1==1) rest.setMessage(ex.getMessage());
        return rest;
    }
    @ExceptionHandler(Exception.class)
    public Rest handleException(Exception ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);

        // todo if debug
        if (1==1) rest.setMessage(ex.getMessage());
        return rest;
    }
}
