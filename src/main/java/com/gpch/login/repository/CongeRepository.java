package com.gpch.login.repository;


import com.gpch.login.model.Conge;
import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("congeRepository")
public interface CongeRepository  extends JpaRepository<Conge, Long> {

    Conge findById(int id);
    List<Conge> findByUser(User user);
    List<Conge> findByState(int state);

}
