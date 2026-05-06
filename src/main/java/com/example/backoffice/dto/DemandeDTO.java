package com.example.backoffice.dto;
import com.example.backoffice.entity.Demande;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
@Data
public class DemandeDTO {
    private Long id;
    private String reference;
    private String typeDemande;
    private String categorieDemande;
    private String statutActuel;
    private LocalDate dateDemande;
    private DemandeurDTO demandeur;
    private PasseportDTO passeport;
    private VisaDTO visa;
    private VisaTransformableDTO visaTransformable;
    private CarteResidentDTO carteResident;
    private List<DocumentDemandeDTO> documents;
    private List<HistoriqueDemandeDTO> historiques;
    private byte[] qrcode;

    public DemandeDTO() {}
    public DemandeDTO(Demande demande) {
        this.id = demande.getId();
        this.reference = demande.getReference();
        this.dateDemande = demande.getDateDemande();
    }
}
