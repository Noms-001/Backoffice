package com.example.backoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backoffice.entity.Passeport;
import com.example.backoffice.repository.PasseportRepository;

@Service
public class PasseportService {

    @Autowired
    private PasseportRepository passeportRepository;

    public Passeport save(Passeport passeport) {
        if (passeport == null) {
            throw new IllegalArgumentException("Passeport obligatoire");
        }

        return passeportRepository.save(passeport);
    }
}