package com.example.backoffice.service;

import com.example.backoffice.entity.Demandeur;
import com.example.backoffice.entity.Passeport;
import com.example.backoffice.repository.PasseportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasseportServiceTest {

    @Mock
    private PasseportRepository passeportRepository;

    @InjectMocks
    private PasseportService passeportService;

    @Test
    void shouldSavePasseport() {

        Demandeur d = new Demandeur();
        d.setNom("Test");

        Passeport p = new Passeport();
        p.setReference("P123456");
        p.setDateDelivrance(LocalDate.now());
        p.setLieuDelivrance("Tana");
        p.setDateExpiration(LocalDate.now().plusYears(10));
        p.setDemandeur(d);

        when(passeportRepository.save(any(Passeport.class)))
                .thenReturn(p);

        Passeport result = passeportService.save(p);

        assertEquals("P123456", result.getReference());
        verify(passeportRepository).save(p);
    }
}