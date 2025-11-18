// src/main/java/com/uade/tpo/marketplace/exceptions/CompraNotFoundException.java
package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "No se pudo realizar la compra")
public class CompraNotFoundException extends Exception {
}