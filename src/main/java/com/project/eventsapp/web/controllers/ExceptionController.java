package com.project.eventsapp.web.controllers;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return "Error:" + e.getMessage();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    // @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("error", e.getMessage());
        // return "Error:"+e.getMessage();
        return "errors";
    }

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
