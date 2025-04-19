package com.kitchenapp.kitchentech.authentication.model;

import com.kitchenapp.kitchentech.user.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStaffUserRequest {
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private String photo;
    private LocalDate birthDate;
    private Role role;
    private Long restaurantId;
}
