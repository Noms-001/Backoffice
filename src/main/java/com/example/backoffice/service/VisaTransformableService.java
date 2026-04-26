package com.example.backoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backoffice.entity.VisaTransformable;
import com.example.backoffice.repository.VisaTransformableRepository;

@Service
public class VisaTransformableService {

    @Autowired
    private VisaTransformableRepository visaTransformableRepository;

    public VisaTransformable save(VisaTransformable visaTransformable) {
        if (visaTransformable == null) {
            throw new IllegalArgumentException("Visa transformable obligatoire");
        }

        return visaTransformableRepository.save(visaTransformable);
    }

}