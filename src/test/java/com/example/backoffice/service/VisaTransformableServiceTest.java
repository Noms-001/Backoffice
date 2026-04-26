package com.example.backoffice.service;

import com.example.backoffice.entity.VisaTransformable;
import com.example.backoffice.repository.VisaTransformableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisaTransformableServiceTest {

    @Mock
    private VisaTransformableRepository repository;

    @InjectMocks
    private VisaTransformableService service;

    @Test
    void shouldSaveVisa() {

        VisaTransformable v = new VisaTransformable();
        v.setNumeroVisa("VISA-001");

        when(repository.save(any(VisaTransformable.class)))
                .thenReturn(v);

        VisaTransformable result = service.save(v);

        assertEquals("VISA-001", result.getNumeroVisa());
        verify(repository).save(v);
    }
}