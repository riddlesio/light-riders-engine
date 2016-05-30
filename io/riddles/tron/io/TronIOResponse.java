package io.riddles.tron.io;

import io.riddles.game.io.IORequest;
import io.riddles.game.io.IOResponse;

/**
 * io.riddles.chess.io
 * <p>
 * This file is a part of tron
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class TronIOResponse implements IOResponse {

    IORequest request;
    private TronIOResponseType type;

    String value;
    

    public TronIOResponse(IORequest request, TronIOResponseType type, String value) {
        this.request = request;
        this.type = type;
        this.value = value;
    }

    @Override
    public IORequest getRequest() {
        return request;
    }

    @Override
    public Enum<?> getType() {
        return type;
    }
    
    @Override
    public String getValue() { return value;  }    
    public void setValue(String value) { this.value = value; }
}