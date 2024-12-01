package com.gpch.login.dtos;

import com.gpch.login.model.Conge;
import com.gpch.login.model.CongeType;
import com.gpch.login.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

public class AddCongeDto {


    private Map<CongeType, CongeDto> liste;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_debut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_fin;
    private int state=0;
    private CongeType type;
    private String email;
    private User user;

    public Map<CongeType, CongeDto> getListe() {
        return liste;
    }

    public void setListe(Map<CongeType, CongeDto> liste) {
        this.liste = liste;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public CongeType getType() {
        return type;
    }

    public void setType(CongeType type) {
        this.type = type;
    }

    public Conge convert(){

        Conge conge = new Conge();
        conge.setDate_debut(this.getDate_debut());
        conge.setDate_fin(this.getDate_fin());
        conge.setState(0);
        conge.setType(this.type);
        conge.setUser(this.user);
        return conge;

    }
}
