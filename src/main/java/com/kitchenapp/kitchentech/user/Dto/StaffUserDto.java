package com.kitchenapp.kitchentech.user.Dto;

import com.kitchenapp.kitchentech.user.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffUserDto {
    private Long id;
    private String username;
    private String phone;
    private String name;
    private String lastname;
    private String email;
    private String photo;
    private LocalDate birthDate;
    private Role role;
    private Long restaurantId;
}
