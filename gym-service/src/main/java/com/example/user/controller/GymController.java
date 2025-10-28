package com.example.user.controller;

import com.example.user.model.Gym;
import com.example.user.service.GymService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gym")
public class GymController {
    private final GymService service;

    public GymController(GymService service) {
        this.service = service;
    }

    @GetMapping
    public List<Gym> list() {
        return service.findAll();
    }

    @PostMapping
    public Gym add(@RequestBody Gym gym) {
        return service.save(gym);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public Gym update(@PathVariable Long id, @RequestBody Gym gym) {
        // 요청 바디의 데이터로 기존 고객 수정
        gym.setId(id);
        return service.save(gym);
    }

}

