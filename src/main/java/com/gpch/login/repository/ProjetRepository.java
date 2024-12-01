package com.gpch.login.repository;


import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("projetRepository")
public interface ProjetRepository  extends JpaRepository<Projet, Long> {
    Projet findById(int id);
    List<Projet> findByUser(User user);
}
