package com.axa.softwareacademy.riskapi.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Patient {
    private Integer id;
    private String familyName;
    private String givenName;
    private String address;
    private Date dob;
    private String sex;
    private String phone;
    private Integer age;
}

