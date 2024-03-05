package com.tvt.recompileapi.exception;

import java.io.Serial;

public class InternalErrorException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public InternalErrorException (String msg) {
        super(msg);
    }
}
