package com.example.backoffice.service;

import com.example.backoffice.entity.Document;
import com.example.backoffice.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @Test
    void shouldReturnCommunDocuments() {

        when(documentRepository.findByType("COMMUN"))
                .thenReturn(List.of(new Document()));

        List<Document> result = documentService.getAllCommuns();

        assertFalse(result.isEmpty());
        verify(documentRepository).findByType("COMMUN");
    }
}