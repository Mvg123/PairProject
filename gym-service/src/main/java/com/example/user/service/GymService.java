package com.example.user.service;

import com.example.user.model.Gym;
import com.example.user.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;

    public GymService(GymRepository gymRepository) { this.gymRepository = gymRepository; }

    public List<Gym> findAll() {return gymRepository.findAll(); }

    public Gym save(Gym gym) { return gymRepository.save(gym); }

    public void delete(Long id) { gymRepository.deleteById(id); }
}
