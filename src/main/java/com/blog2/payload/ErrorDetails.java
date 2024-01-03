package com.blog2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;


}