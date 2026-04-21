package com.example.backoffice.util;

public enum TypeDemandeEnum {

    NOUVEAU_TITRE(1L),
    DUPLICATA(2L),
    TRANSFERT(3L);

    private final Long code;

    TypeDemandeEnum(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public static TypeDemandeEnum fromCode(Long code) {
        for (TypeDemandeEnum type : TypeDemandeEnum.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Code invalide pour TypeDemandeEnum : " + code);
    }
}