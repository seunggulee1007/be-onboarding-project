package com.innercicle.advice;

import com.innercicle.advice.exceptions.AlreadyLockedException;
import com.innercicle.advice.exceptions.NotExistsSurveyException;
import com.innercicle.advice.exceptions.NotMatchedException;
import com.innercicle.advice.exceptions.RequiredFieldException;
import com.innercicle.utils.ApiUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.innercicle.utils.ApiUtil.fail;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiUtil.ApiResult<Void> defaultException(Exception e) {
        log.error("e :: {}, message :: {}", e.getClass().getName(), e.getMessage());
        e.printStackTrace();
        return fail(e, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
        ConstraintViolationException.class,
        RequiredFieldException.class,
        AlreadyLockedException.class,
        NotExistsSurveyException.class,
        NotMatchedException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public ApiUtil.ApiResult<Void> badRequest(Exception e) {
        log.error("e :: {}, message :: {}", e.getClass().getName(), e.getMessage());
        return fail(e, BAD_REQUEST);
    }

}
