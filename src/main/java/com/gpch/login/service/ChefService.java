package com.gpch.login.service;


import com.gpch.login.dtos.ChefDto;
import com.gpch.login.dtos.EditChefDto;
import com.gpch.login.dtos.EditUserDto;
import com.gpch.login.dtos.UserDto;
import com.gpch.login.model.Role;
import com.gpch.login.model.User;
import com.gpch.login.repository.RoleRepository;
import com.gpch.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("chefService")
public class ChefService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public ChefService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findUserById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findUserByRole(String role) {
        Role roleModel = roleRepository.findByRole(role);
        return userRepository.findByRolesIn( Arrays.asList(roleModel));
    }

    public User saveChef(ChefDto userdto) {
        User user = userdto.convert();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ROLE_CHEF");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public User modifierChef(EditChefDto user){

        User user1 = userRepository.findById(user.getId());
        user1.setDate_embauche(user.getDate_embauche());
        user1.setEmail(user.getEmail());
        user1.setSexe(user.getSexe());
        user1.setTelephone(user.getTelephone());
        user1.setName(user.getName());
        user1.setLastName(user.getLastName());
        return userRepository.save(user1);
    }

    public void supprimerChef(int id){
        User user = userRepository.findById(id);
        userRepository.delete(user);

    }
}
