package com.kitchenapp.kitchentech.user.service;

import com.kitchenapp.kitchentech.user.model.StaffUser;

import java.util.List;

public interface StaffUserService {
    public abstract StaffUser createStaffUser(StaffUser staffUser);
    public abstract StaffUser getStaffUserById(Long id);
    public abstract StaffUser updateStaffUser(StaffUser staffUser);
    public abstract void deleteStaffUser(Long id);
    public abstract List<StaffUser> getAllStaffUsers();
    public void existsStaffUserById(Long Id);
}
