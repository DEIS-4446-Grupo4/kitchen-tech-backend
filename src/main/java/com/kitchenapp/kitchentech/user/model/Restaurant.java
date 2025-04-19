package com.kitchenapp.kitchentech.user.model;

import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.model.Supply;
import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import com.kitchenapp.kitchentech.user.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="restaurant")
public class Restaurant implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name="name", nullable = false, length = 150)
    private String name;

    @Column(name="password", nullable = false, length = 100)
    private String password;

    @Column(name="phone", length = 20)
    private String phone;

    @Column(name="email", nullable = false, length = 150)
    private String email;

    @Column(name="image")
    private String image;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "district", length = 100)
    private String district;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Supply> supplies;

    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StaffUser> staffUsers;

    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TableRestaurant> tables;

    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Client> clients ;

    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Account> accounts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
