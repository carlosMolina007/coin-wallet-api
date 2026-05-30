package com.coinwallet.coin_wallet_api.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceBadRequestException extends RuntimeException{

    public ResourceBadRequestException(String message) {
        super(message);
    }

}
