package com.example.user_practice_1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int userId;
    @Pattern(regexp = "^[a-zA-Z. ]+$", message = "")
    private String userName;
    @Min(value = 18, message = "Age should be at least 18.")
    @Max(value = 100, message = "Age should not exceed 100.")
    private int userAge;
    @Positive(message = "Salary must be positive.")
    private int userSalary;
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number format. It should be 10 digits.")
    private String userMobileNumber;
}
