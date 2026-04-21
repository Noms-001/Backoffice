package com.example.backoffice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backoffice.entity.SituationFamiliale;
import com.example.backoffice.repository.SituationFamilialeRepository;

@Service
public class SituationFamilialeService {

    @Autowired
    private SituationFamilialeRepository situationFamilialeRepository;

    public List<SituationFamiliale> getAll() {
        return situationFamilialeRepository.findAll();
    }

}
