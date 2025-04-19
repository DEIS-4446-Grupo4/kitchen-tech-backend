package com.kitchenapp.kitchentech.user.service.impl;

import com.kitchenapp.kitchentech.exception.ValidationException;
import com.kitchenapp.kitchentech.user.model.StaffUser;
import com.kitchenapp.kitchentech.user.repository.StaffUserRepository;
import com.kitchenapp.kitchentech.user.service.StaffUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffUserServiceImpl implements StaffUserService {
    private final StaffUserRepository staffUserRepository;

    public StaffUserServiceImpl(StaffUserRepository staffUserRepository) {
        this.staffUserRepository = staffUserRepository;
    }

    @Override
    public StaffUser createStaffUser(StaffUser restaurant){
        return staffUserRepository.save(restaurant);
    }

    @Override
    public StaffUser getStaffUserById(Long id) {
        return staffUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<StaffUser> getAllStaffUsers() {
        return staffUserRepository.findAll();
    }

    @Override
    public StaffUser updateStaffUser(StaffUser staffUser){
        StaffUser existingStaffUser = getStaffUserById(staffUser.getId());
        if(existingStaffUser != null){
            StaffUser staffUserToUpdate = existingStaffUser;
            staffUserToUpdate.setUsername(staffUser.getUsername());
            staffUserToUpdate.setName(staffUser.getName());
            staffUserToUpdate.setLastname(staffUser.getLastname());
            staffUserToUpdate.setPhone(staffUser.getPhone());
            staffUserToUpdate.setEmail(staffUser.getEmail());
            staffUserToUpdate.setPhoto(staffUser.getPhoto());
            staffUserToUpdate.setBirthDate(staffUser.getBirthDate());
            staffUserToUpdate.setRole(staffUser.getRole());
            return staffUserRepository.save(staffUserToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteStaffUser(Long id) {
        staffUserRepository.deleteById(id);
    }

    @Override
    public void existsStaffUserById(Long id) {
        if (!staffUserRepository.existsById(id)) {
            throw new ValidationException("No existe ningun usuario con ese id");
        }
    }
}
