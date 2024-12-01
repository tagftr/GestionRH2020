package com.gpch.login.dtos;

import com.gpch.login.model.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

public class EditUserDto {


    private int id;
    @Email(message = "*Entrez un email valid")
    @NotEmpty(message = "*Entrez un email")
    private String email;

    @NotEmpty(message = "*Entrez le prenom")
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
        user.setLastName(this.lastName);
        user.setTelephone(this.telephone);
        user.setEmail(this.email);
        user.setDate_embauche(this.date_embauche);
        user.setSexe(this.sexe);
        user.setActive(1);
        return user;
    }
    public static EditUserDto convert(User u){
        EditUserDto user = new EditUserDto();
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
