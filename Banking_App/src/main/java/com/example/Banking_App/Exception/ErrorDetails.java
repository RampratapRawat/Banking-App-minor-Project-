package com.example.Banking_App.Exception;

import java.time.LocalDateTime;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class ErrorDetails {
//     private LocalDateTime timeStamp;
//     private String message;
//     private String details;
//     private String errorCode;
// }

public record ErrorDetails(LocalDateTime timeStamp,
     String message,
     String details,
     String errorCode) {
}
