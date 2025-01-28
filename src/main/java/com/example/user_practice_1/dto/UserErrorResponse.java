package com.example.user_practice_1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
