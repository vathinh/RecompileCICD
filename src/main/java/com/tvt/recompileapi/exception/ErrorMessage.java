package com.tvt.recompileapi.exception;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private final String errorTitle = "Oops!!... Something went wrong: ";
    private int statusCode;
    private Date timeStamp;
    private String message;
    private String description;
}
