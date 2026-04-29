package com.example.backoffice.util;

public enum StatutDemandeEnum {
    
    CREER(1L),
    APPROUVER(2L),
    SCAN_TERMINE(3L);

    private final Long code;

    StatutDemandeEnum(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public static StatutDemandeEnum fromCode(Long code) {
        for (StatutDemandeEnum statut : StatutDemandeEnum.values()) {
            if (statut.code == code) {
                return statut;
            }
        }
        throw new IllegalArgumentException("Code invalide pour StatutDemandeEnum : " + code);
    }
}
