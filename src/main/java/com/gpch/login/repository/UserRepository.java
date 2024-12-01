package com.gpch.login.repository;

import com.gpch.login.model.Role;
import com.gpch.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);
    User findByEmail(String email);
    List<User> findByUser(User user);
    List<User> findByRolesIn(List<Role> roles);
}