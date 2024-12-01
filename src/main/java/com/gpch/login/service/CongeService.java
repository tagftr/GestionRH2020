package com.gpch.login.service;

import com.gpch.login.dtos.AddCongeDto;
import com.gpch.login.dtos.CongeDto;
import com.gpch.login.model.Conge;
import com.gpch.login.model.CongeType;
import com.gpch.login.model.User;
import com.gpch.login.repository.CongeRepository;
import com.gpch.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("congeService")
public class CongeService {

    @Autowired
    private CongeRepository congeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Conge> listConges(){

        return congeRepository.findAll();
    }

    public Conge changeState(int conge_id, int state){

        Conge conge = congeRepository.findById(conge_id);
        conge.setState(state);
       return congeRepository.save(conge);
    }
    public List<Conge> listCongesByUser(String email){

        User user = userRepository.findByEmail(email);
        return congeRepository.findByUser(user);
    }

    public List<Conge> listCongesByState(int state){

        return congeRepository.findByState(state);
    }
    public Conge ajouterConge(AddCongeDto congeDto){

        User user = userRepository.findByEmail(congeDto.getEmail());
        congeDto.setUser(user);
        Conge conge = congeDto.convert();
        return this.congeRepository.save(conge);
    }
    public Map<CongeType,String> getNames()
    {
        Map<CongeType,String> conges = new HashMap<CongeType,String>();
        conges.put(CongeType.cif,"Congé individuel formation");
        conges.put(CongeType.cap,"Congé annuel payé");
        conges.put(CongeType.cm,"Congé maternité");
        conges.put(CongeType.cprf,"Congé pour raisons familiale");
        conges.put(CongeType.cpe,"Congé parental d'education");
        conges.put(CongeType.clm,"Congé longue maladie");
        return conges;
    }


    private double getCongeAnnuel(String  email){
        User user = userRepository.findByEmail(email);

        Date date_embauche = user.getDate_embauche();
        Date now = new Date();
        int counter = 0;

        for (int year =date_embauche.getYear(); year<= now.getYear() ; year++){

            //last year
            if(year == now.getYear() && now.getYear() == date_embauche.getYear()){
                counter += now.getMonth() - date_embauche.getMonth();
                break;
            }

            if(year  == now.getYear()){
                counter += now.getMonth();
            }

            else if(year == date_embauche.getYear()){
                counter +=12 - date_embauche.getMonth();
            }
            // middle
            else {
                counter+= 12 ;
            }
        }
        double days = 1.5 * counter;
        return getRest(user.getId(),CongeType.cap, days);
    }

    public double getRest(int user_id, CongeType type, double original){

        User user = userRepository.findById(user_id);
        List<Conge> conges = congeRepository.findByUser(user);
        List<Conge> filtredConges = conges.stream().
                                        filter(c -> c.getState() == 1).
                                        filter(c -> c.getType() == type).collect(Collectors.toList());
        if(filtredConges.size() == 0)
            return original;

        int somme=0;
        for(Conge conge : filtredConges){
            Date from =  conge.getDate_debut();
            Date to = conge.getDate_fin();
            if(from.getYear() == from.getYear()){
                long diffInMillies = Math.abs(to.getTime() - from.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                somme+= diff;
            }
        }
        return original - somme;
    }

    public Map<CongeType,CongeDto> getConges(String email)
    {
        User user = userRepository.findByEmail(email);
        Map<CongeType,CongeDto> conges = new HashMap<CongeType,CongeDto>();
        conges.put(CongeType.cif,new CongeDto("Congé individuel formation", getRest(user.getId(),CongeType.cif,120)));
        conges.put(CongeType.cap,new CongeDto("Congé annuel payé",getRest(user.getId(),CongeType.cap,getCongeAnnuel(user.getEmail()))));
        conges.put(CongeType.cm, new CongeDto("Congé maternité", getRest(user.getId(),CongeType.cm,90)));
        conges.put(CongeType.cprf, new CongeDto("Congé pour raisons familiale",getRest(user.getId(),CongeType.cprf,3)));
        conges.put(CongeType.cpe, new CongeDto("Congé parental d'education",getRest(user.getId(),CongeType.cpe,1)));
        conges.put(CongeType.clm, new CongeDto("Congé longue maladie",getRest(user.getId(),CongeType.clm,120)));
        return conges;
    }



}
