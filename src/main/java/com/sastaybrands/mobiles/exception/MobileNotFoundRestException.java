package com.sastaybrands.mobiles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Mobile not found")
public class MobileNotFoundRestException extends Exception {





}
