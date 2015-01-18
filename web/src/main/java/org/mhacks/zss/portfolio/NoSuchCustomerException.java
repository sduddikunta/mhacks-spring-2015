package org.mhacks.zss.portfolio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such customer")
public class NoSuchCustomerException extends RuntimeException {
}
