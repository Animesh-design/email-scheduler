package com.example.emailscheduler.payload;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Setter
@Getter
public class EmailRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;

    @NotNull
    private LocalDateTime dataTime;

    @NotNull
    private ZoneId timeZone;

}
