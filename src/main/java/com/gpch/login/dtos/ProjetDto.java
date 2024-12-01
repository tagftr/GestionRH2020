package com.gpch.login.dtos;

import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ProjetDto {

    private int id;
    @NotEmpty(message = "*Entrez le nom")
    private String name;

    @NotEmpty(message = "*Entrez la description")
    private String description;
    private List<User> chefs;
    private int  chef_id;
    private Set<User> employes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<User> getEmployes() {
        return employes;
    }

    public void setEmployes(Set<User> employes) {
        this.employes = employes;
    }

    public Projet convert(){
        Projet projet = new Projet();
        if(this.id != 0)
            projet.setId(this.id);
        projet.setName(this.name);
        projet.setDescription(this.description);
        return projet;
    }
    public static ProjetDto convert(Projet projet){
        ProjetDto projetDto = new ProjetDto();
        projetDto.setId(projet.getId());
        projetDto.setChef_id(projet.getUser().getId());
        projetDto.setName(projet.getName());
        projetDto.setDescription(projet.getDescription());
        return projetDto;
    }

}
