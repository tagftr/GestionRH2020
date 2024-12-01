package com.gpch.login.dtos;

import com.gpch.login.model.Role;
import com.gpch.login.model.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserDto {


    private int id;
    @Email(message = "*Entrez un email valid")
    @NotEmpty(message = "*Entrez l'email")
    private String email;

    @Length(min = 5, message = "*Le mot de passe doit contenir au minimium 5 caractére")
    @NotEmpty(message = "*Entrez le mot de passe")
    private String password;
    @Length(min = 5, message = "*Le mot de passe doit contenir au minimium 5 caractére")
    @NotEmpty(message = "*Entrez la confirmation du mot de passe")
    private String confirmationPassword;
    @NotEmpty(message = "*Entrez le nom")
    private String name;

    @NotEmpty(message = "*Entrez le nom")
    private String lastName;

    @NotEmpty(message = "*Entrez le telephone")
    private String telephone;

    @NotEmpty(message = "*Entrez le sexe")
    private String sexe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_embauche;

    private List<User> chefs;
    private int  chef_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getDate_embauche() {
        return date_embauche;
    }

    public void setDate_embauche(Date date_embauche) {
        this.date_embauche = date_embauche;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public int getChef_id() {
        return chef_id;
    }

    public void setChef_id(int chef_id) {
        this.chef_id = chef_id;
    }



    public List<User> getChefs() {
        return chefs;
    }

    public void setChefs(List<User> chefs) {
        this.chefs = chefs;
    }

    public User convert(){
        User user = new User();
        if(this.id != 0)
            user.setId(this.id);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setLastName(this.lastName);
        user.setTelephone(this.telephone);
        user.setEmail(this.email);
        user.setDate_embauche(this.date_embauche);
        user.setSexe(this.sexe);
        user.setActive(1);
        return user;
    }
    public static UserDto convert(User u){
        UserDto user = new UserDto();
        user.setChef_id(u.getUser().getId());
        user.setName(u.getName());
        user.setLastName(u.getLastName());
        user.setDate_embauche(u.getDate_embauche());
        user.setTelephone(u.getTelephone());
        user.setEmail(u.getEmail());
        user.setSexe(u.getSexe());
        user.setId(u.getId());
        return user;
    }

}
