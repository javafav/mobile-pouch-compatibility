package com.mobilematching.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brnad not found")
public class BrandNotFoundRestException extends Exception {





}
