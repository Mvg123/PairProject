package com.example.user.repository;

import com.example.user.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym,Long> {
}
