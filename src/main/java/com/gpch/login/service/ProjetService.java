package com.gpch.login.service;


import com.gpch.login.dtos.ProjetDto;
import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import com.gpch.login.repository.ProjetRepository;
import com.gpch.login.repository.RoleRepository;
import com.gpch.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service("projetService")
public class ProjetService {

    private ProjetRepository projetRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjetService(ProjetRepository projetRepository,
                         UserRepository userRepository
    ) {
        this.projetRepository = projetRepository;
        this.userRepository = userRepository;
    }

    public List<Projet> findAllProjets(){
        return projetRepository.findAll();
    }
    public Projet saveProjet(ProjetDto projetDto){
        Projet projet = projetDto.convert();
        User chef = userRepository.findById(projetDto.getChef_id());
        projet.setUser(chef);
        return projetRepository.save(projet);
    }
    public Projet modifierProjets(ProjetDto projetdto){

        Projet projet = projetRepository.findById(projetdto.getId());
        projet.setName(projetdto.getName());
        projet.setDescription(projetdto.getDescription());
        User chef = userRepository.findById(projetdto.getChef_id());
        projet.setUser(chef);
        return projetRepository.save(projet);

    }

    public List<Projet> findByUser(User user){
        List<Projet> projets = this.findAllProjets();
        List<Projet> employe_projets = projets.stream().filter(p  -> p.getEmployes().stream().filter(u -> u.getId() == user.getId()).collect(Collectors.toList()).size() != 0).collect(Collectors.toList());
    return employe_projets;
    }
    public List<Projet> findbyChef(User user){
        return projetRepository.findByUser(user);
    }

    public void affecterUser(User u,Projet p) {
        p.getEmployes().add(u);
        projetRepository.save(p);
    }
    public void supprimerEmploye(int projet_id,int user_id){
        Projet p = projetRepository.findById(projet_id);
        User user = userRepository.findById(user_id);
        p.getEmployes().remove(user);
        projetRepository.save(p);
    }

    public Projet findById(int id){
        return projetRepository.findById(id);
    }
    public void supprimerProjet(int id){
        Projet p = projetRepository.findById(id);
        projetRepository.delete(p);
    }

}
