package com.gpch.login.service;

import com.gpch.login.dtos.EditUserDto;
import com.gpch.login.dtos.UserDto;
import com.gpch.login.model.Projet;
import com.gpch.login.model.Role;
import com.gpch.login.model.User;
import com.gpch.login.repository.ProjetRepository;
import com.gpch.login.repository.RoleRepository;
import com.gpch.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ProjetRepository projetRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       ProjetRepository projetRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.projetRepository = projetRepository;
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

    public List<User> findbyChefAndNotInProject(int projetId){
        Projet projet = projetRepository.findById(projetId);
        List<User> allemployes = userRepository.findByUser(projet.getUser());
        allemployes.removeAll(projet.getEmployes());
        return allemployes;
    }
    public List<User> findbyChef(User user){
        return userRepository.findByUser(user);
    }

    public User saveUser(UserDto userdto) {
        User user = userdto.convert();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
         User chef = userRepository.findById(userdto.getChef_id());
         user.setUser(chef);
        Role userRole = roleRepository.findByRole("ROLE_EMPLOYE");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public User modifierEmploye(EditUserDto user){

        User user1 = userRepository.findById(user.getId());
        user1.setDate_embauche(user.getDate_embauche());
        user1.setEmail(user.getEmail());
        user1.setSexe(user.getSexe());
        user1.setTelephone(user.getTelephone());
        User chef = userRepository.findById(user.getChef_id()) ;
        user1.setUser(chef);
        user1.setName(user.getName());
        user1.setLastName(user.getLastName());
        return userRepository.save(user1);

    }

    public void supprimerUser(int id){
        User user = userRepository.findById(id);
        userRepository.delete(user);

    }
}