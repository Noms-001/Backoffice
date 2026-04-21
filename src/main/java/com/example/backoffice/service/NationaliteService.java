package com.example.backoffice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backoffice.entity.Nationalite;
import com.example.backoffice.repository.NationaliteRepository;

@Service
public class NationaliteService {

    @Autowired
    private NationaliteRepository nationaliteRepository;

    public List<Nationalite> getAll() {
        return nationaliteRepository.findAll();
    }

}
