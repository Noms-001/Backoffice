package com.example.backoffice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backoffice.entity.Demande;
import com.example.backoffice.entity.Document;
import com.example.backoffice.entity.DocumentDemande;
import com.example.backoffice.repository.DocumentDemandeRepository;
import com.example.backoffice.repository.DocumentRepository;

@Service
public class DocumentService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentDemandeRepository documentDemandeRepository;

    public List<Document> getAllCommuns() {
        return documentRepository.findByType("COMMUN");
    }

    public List<Document> getAllByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return documentRepository.findAllById(ids);
    }

    public List<Document> uploadDocuments(Demande demande, Map<String, MultipartFile> files)
            throws IOException {
        List<Document> documents = new ArrayList<>();

        // Créer le répertoire si nécessaire
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
            String key = entry.getKey();
            Long documentId = Long.valueOf(
                    key.substring(key.indexOf("[") + 1, key.indexOf("]")));

            MultipartFile file = entry.getValue();

            if (file != null && !file.isEmpty()) {
                // Générer un nom de fichier unique
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String uniqueId = UUID.randomUUID().toString().substring(0, 8);
                String originalFilename = file.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String fileName = String.format("doc_%d_%s_%s%s", documentId, timestamp, uniqueId, extension);

                // Sauvegarder le fichier
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);

                DocumentDemande docDemande = documentDemandeRepository
                        .findByDemandeIdAndDocumentId(demande.getId(), documentId);
                if (docDemande == null) {
                    Document document = new Document();
                    document.setId(documentId);
                    docDemande = new DocumentDemande();
                    docDemande.setDocument(document);
                    docDemande.setDemande(demande);
                } else {
                    Files.deleteIfExists(Paths.get(uploadDir + "/" + docDemande.getPath()));
                }
                docDemande.setPath(fileName);

                docDemande = documentDemandeRepository.save(docDemande);

                documents.add(docDemande.getDocument());
            }
        }

        return documents;
    }

    public boolean areAllDocumentsUploaded(Demande demande, List<Document> specifiques, List<Document> uploadedDocs) {
        // Récupérer les documents communs
        List<Document> docs = getAllCommuns();

        docs.addAll(specifiques);

        // Vérifier que tous les documents requis sont présents
        return docs.containsAll(uploadedDocs);
    }

}
