package com.talianet.springmongodemo.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class ResponseError {
    private String title;
    private String message;
}
