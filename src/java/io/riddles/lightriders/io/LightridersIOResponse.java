package io.riddles.lightriders.io;

import io.riddles.game.io.IORequest;
import io.riddles.game.io.IOResponse;

/**
 * io.riddles.chess.io
 * <p>
 * This file is a part of lightriders
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class LightridersIOResponse implements IOResponse {

    IORequest request;
    private LightridersIOResponseType type;

    String value;
    

    public LightridersIOResponse(IORequest request, LightridersIOResponseType type, String value) {
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