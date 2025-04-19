package com.kitchenapp.kitchentech.user.controller;

import com.kitchenapp.kitchentech.user.model.StaffUser;
import com.kitchenapp.kitchentech.user.Dto.StaffUserDto;
import com.kitchenapp.kitchentech.user.service.StaffUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/kitchentech/v1/staff-user")
public class StaffUserController {

    private final StaffUserService staffUserService;

    public StaffUserController(StaffUserService staffUserService) {
        this.staffUserService = staffUserService;
    }

    // URL: http://localhost:8080/api/kitchentech/v1/staff-user
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<StaffUserDto>> getAllStaffUsers() {
        List<StaffUser> staffUsers = staffUserService.getAllStaffUsers();
        return new ResponseEntity<List<StaffUserDto>>(staffUsers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/staff-user/{staffUserId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{staffUserId}")
    public ResponseEntity<StaffUserDto> getStaffUser(@PathVariable(name = "staffUserId") Long staffUserId) {
        staffUserService.existsStaffUserById(staffUserId);
        StaffUser staffUser = staffUserService.getStaffUserById(staffUserId);
        StaffUserDto staffUserDto = convertToDto(staffUser);
        return new ResponseEntity<StaffUserDto>(staffUserDto, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/user/{userId}
    // Method: PUT
    @Transactional
    @PutMapping("/{staffUserId}")
    public ResponseEntity<StaffUser> updateStaffUserById(@PathVariable(name = "staffUserId") Long staffUserId, @RequestBody StaffUserDto staffUserDto) {
        staffUserService.existsStaffUserById(staffUserId);

        StaffUser responseStaffUser = new StaffUser();
        responseStaffUser.setId(staffUserId);
        responseStaffUser.setUsername(staffUserDto.getUsername());
        responseStaffUser.setName(staffUserDto.getName());
        responseStaffUser.setLastname(staffUserDto.getLastname());
        responseStaffUser.setEmail(staffUserDto.getEmail());
        responseStaffUser.setPhone(staffUserDto.getPhone());
        responseStaffUser.setPhoto(staffUserDto.getPhoto());
        responseStaffUser.setBirthDate(staffUserDto.getBirthDate());
        responseStaffUser.setRole(staffUserDto.getRole());
        responseStaffUser.setRestaurantId(staffUserDto.getRestaurantId());

        StaffUser staffUserSaved = staffUserService.updateStaffUser(responseStaffUser);
        return new ResponseEntity<>(staffUserSaved, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{staffUserId}")
    public ResponseEntity<Void> deleteStaffUser(@PathVariable(name = "staffUserId") Long staffUserId) {
        staffUserService.existsStaffUserById(staffUserId);
        staffUserService.deleteStaffUser(staffUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private StaffUserDto convertToDto(StaffUser staffUser) {
        return StaffUserDto.builder()
                .id(staffUser.getId())
                .username(staffUser.getUsername())
                .name(staffUser.getName())
                .lastname(staffUser.getLastname())
                .email(staffUser.getEmail())
                .phone(staffUser.getPhone())
                .photo(staffUser.getPhoto())
                .birthDate(staffUser.getBirthDate())
                .role(staffUser.getRole())
                .restaurantId(staffUser.getRestaurantId())
                .build();
    }
}
