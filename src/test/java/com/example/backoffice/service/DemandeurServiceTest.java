package com.example.backoffice.service;

import com.example.backoffice.entity.Demandeur;
import com.example.backoffice.repository.DemandeurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemandeurServiceTest {

    @Mock
    private DemandeurRepository demandeurRepository;

    @InjectMocks
    private DemandeurService demandeurService;

    @Test
    void shouldSaveDemandeur() {
        Demandeur d = new Demandeur();
        d.setNom("Rakoto");

        when(demandeurRepository.save(any(Demandeur.class)))
                .thenReturn(d);

        Demandeur result = demandeurService.save(d);

        assertNotNull(result);
        assertEquals("Rakoto", result.getNom());

        verify(demandeurRepository, times(1)).save(d);
    }

    @Test
    void shouldThrowExceptionWhenNull() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> demandeurService.save(null));

        assertEquals("Demandeur obligatoire", ex.getMessage());
    }
}