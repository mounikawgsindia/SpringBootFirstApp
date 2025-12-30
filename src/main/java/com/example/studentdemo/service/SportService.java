package com.example.studentdemo.service;

import com.example.studentdemo.entity.Sport;
import com.example.studentdemo.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SportService {
    
    @Autowired
    private SportRepository sportRepository;
    
    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }
    
    public Optional<Sport> getSportById(Long id) {
        return sportRepository.findById(id);
    }
    
    public Sport createSport(Sport sport) {
        // Check if sport with same name already exists
        Optional<Sport> existingSport = sportRepository.findByName(sport.getName());
        if (existingSport.isPresent()) {
            throw new RuntimeException("Sport with name '" + sport.getName() + "' already exists");
        }
        return sportRepository.save(sport);
    }
    
    public Sport updateSport(Long id, Sport sportDetails) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + id));
        
        // Check if another sport with the same name exists
        Optional<Sport> existingSport = sportRepository.findByName(sportDetails.getName());
        if (existingSport.isPresent() && !existingSport.get().getId().equals(id)) {
            throw new RuntimeException("Sport with name '" + sportDetails.getName() + "' already exists");
        }
        
        sport.setName(sportDetails.getName());
        return sportRepository.save(sport);
    }
    
    public void deleteSport(Long id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + id));
        
        sportRepository.delete(sport);
    }
}


