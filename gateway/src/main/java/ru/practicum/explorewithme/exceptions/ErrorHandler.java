package ru.practicum.explorewithme.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleRequestException(final RequestException e, WebRequest webRequest) {
        List<String> errors = new ArrayList<>();
        Arrays.stream(e.getStackTrace()).forEach(error -> errors.add(error.toString()));
        return new ApiError(errors, e.getLocalizedMessage(), "Запрос составлен с ошибкой " +
                webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
    }
}
