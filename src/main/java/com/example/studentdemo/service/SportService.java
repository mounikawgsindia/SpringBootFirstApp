package com.example.studentdemo.service;

import com.example.studentdemo.entity.Sport;
import com.example.studentdemo.repository.SportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SportService {

    private static final Logger logger = LoggerFactory.getLogger(SportService.class);

    @Autowired
    private SportRepository sportRepository;

    public List<Sport> getAllSports() {
        logger.debug("Retrieving all sports");
        List<Sport> sports = sportRepository.findAll();
        logger.debug("Retrieved {} sports", sports.size());
        return sports;
    }

    public Optional<Sport> getSportById(Long id) {
        logger.debug("Retrieving sport with id: {}", id);
        Optional<Sport> sport = sportRepository.findById(id);
        if (sport.isPresent()) {
            logger.debug("Found sport with id: {}", id);
        } else {
            logger.debug("Sport not found with id: {}", id);
        }
        return sport;
    }

    public Sport createSport(Sport sport) {
        logger.info("Creating new sport with name: {}", sport.getName());
        // Check if sport with same name already exists

        Optional<Sport> existingSport = sportRepository.findByName(sport.getName());
        if (existingSport.isPresent()) {
            logger.warn("Sport with name '{}' already exists", sport.getName());
            throw new RuntimeException("Sport with name '" + sport.getName() + "' already exists");
        }
        Sport savedSport = sportRepository.save(sport);
        logger.info("Successfully created sport with id: {} and name: {}", savedSport.getId(), savedSport.getName());
        return savedSport;
    }

    public Sport updateSport(Long id, Sport sportDetails) {
        logger.info("Updating sport with id: {} and new name: {}", id, sportDetails.getName());
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Sport not found with id: {}", id);
                    return new RuntimeException("Sport not found with id: " + id);
                });

        // Check if another sport with the same name exists
        Optional<Sport> existingSport = sportRepository.findByName(sportDetails.getName());
        if (existingSport.isPresent() && !existingSport.get().getId().equals(id)) {
            logger.warn("Sport with name '{}' already exists (different id)", sportDetails.getName());
            throw new RuntimeException("Sport with name '" + sportDetails.getName() + "' already exists");
        }

        sport.setName(sportDetails.getName());
        Sport updatedSport = sportRepository.save(sport);
        logger.info("Successfully updated sport with id: {}", id);
        return updatedSport;
    }

    public void deleteSport(Long id) {
        logger.info("Deleting sport with id: {}", id);
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Sport not found with id: {}", id);
                    return new RuntimeException("Sport not found with id: " + id);
                });

        sportRepository.delete(sport);
        logger.info("Successfully deleted sport with id: {}", id);
    }
}


