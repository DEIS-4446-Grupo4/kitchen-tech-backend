package com.kitchenapp.kitchentech.authentication.service.impl;

import com.kitchenapp.kitchentech.authentication.model.AuthResponse;
import com.kitchenapp.kitchentech.authentication.model.LoginRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterRestaurantRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterStaffUserRequest;
import com.kitchenapp.kitchentech.authentication.service.AuthService;
import com.kitchenapp.kitchentech.exception.ValidationException;
import com.kitchenapp.kitchentech.jwt.JwtService;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.model.StaffUser;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import com.kitchenapp.kitchentech.user.repository.StaffUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final StaffUserRepository staffUserRepository;
    private final RestaurantRepository restaurantRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(StaffUserRepository staffUserRepository, RestaurantRepository restaurantRepository, JwtService jwtService,
                           PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager){
        this.staffUserRepository = staffUserRepository;
        this.restaurantRepository = restaurantRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse registerRestaurant(RegisterRestaurantRequest registerRestaurantRequest) {

        Restaurant restaurant = Restaurant.builder()
                .username(registerRestaurantRequest.getUsername())
                .password(passwordEncoder.encode(registerRestaurantRequest.getPassword()))
                .name(registerRestaurantRequest.getName())
                .phone(registerRestaurantRequest.getPhone())
                .email(registerRestaurantRequest.getEmail())
                .image(registerRestaurantRequest.getImage())
                .city(registerRestaurantRequest.getCity())
                .district(registerRestaurantRequest.getDistrict())
                .role(registerRestaurantRequest.getRole())
                .build();
        restaurantRepository.save(restaurant);
        return AuthResponse.builder()
                .token(jwtService.getToken(restaurant))
                .id(restaurant.getId())
                .build();
    }

    @Override
    public AuthResponse registerStaffUser(RegisterStaffUserRequest registerStaffUserRequest) {
        StaffUser staffUser = StaffUser.builder()
                .username(registerStaffUserRequest.getUsername())
                .password(passwordEncoder.encode(registerStaffUserRequest.getPassword()))
                .name(registerStaffUserRequest.getName())
                .lastname(registerStaffUserRequest.getLastname())
                .phone(registerStaffUserRequest.getPhone())
                .email(registerStaffUserRequest.getEmail())
                .photo(registerStaffUserRequest.getPhoto())
                .birthDate(registerStaffUserRequest.getBirthDate())
                .role(registerStaffUserRequest.getRole())
                .restaurantId(registerStaffUserRequest.getRestaurantId()) // Asignar el ID del restaurante
                .build();
        staffUserRepository.save(staffUser);
        return AuthResponse.builder()
                .token(jwtService.getToken(staffUser))
                .id(staffUser.getId())
                .build();
    }



    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        UserDetails user;
        Long id;
        Long restaurantId;
        String role;

        // Verificar si el usuario es un Staff
        var staffUserOpt = staffUserRepository.findByUsername(loginRequest.getUsername());
        if (staffUserOpt.isPresent()) {
            user = staffUserOpt.get();
            id = staffUserOpt.get().getId();
            restaurantId = staffUserOpt.get().getRestaurantId();
            role = staffUserOpt.get().getRole().toString();

        } else {
            // Verificar si el usuario es un Restaurante
            var restaurantOpt = restaurantRepository.findByUsername(loginRequest.getUsername());
            if (restaurantOpt.isPresent()) {
                user = restaurantOpt.get();
                id = restaurantOpt.get().getId();
                restaurantId = id;
                role = restaurantOpt.get().getRole().toString();
            } else {
                throw new ValidationException("User not found");
            }
        }

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .id(id)
                .restaurantId(restaurantId) // ID del restaurante, si es staff
                .role(role)
                .build();
    }

    @Override
    public void existsUserByUsername(RegisterRestaurantRequest registerRestaurantRequest, RegisterStaffUserRequest registerStaffUserRequest) {
        boolean existsInStaff = registerStaffUserRequest != null && staffUserRepository.existsByUsername(registerStaffUserRequest.getUsername());
        boolean existsInRestaurant = registerRestaurantRequest != null && restaurantRepository.existsByUsername(registerRestaurantRequest.getUsername());

        if (existsInStaff || existsInRestaurant) {
            String username = registerStaffUserRequest != null ? registerStaffUserRequest.getUsername() : registerRestaurantRequest.getUsername();
            throw new ValidationException("Ya existe un usuario con el username " + username);
        }
    }
}
