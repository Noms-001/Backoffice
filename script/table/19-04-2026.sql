-- =========================
-- TABLES DE BASE
-- =========================

CREATE TABLE situation_familiale (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE nationalite (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE type_demande (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE categorie_demande (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE statut_demande (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

-- =========================
-- DEMANDEUR
-- =========================

CREATE TABLE demandeur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    nom_jeune_fille VARCHAR(100),
    date_naissance DATE NOT NULL,
    lieu_naissance VARCHAR(150) NOT NULL,
    id_situation_familiale INT NOT NULL,
    id_nationalite INT NOT NULL,
    adresse_mada TEXT NOT NULL,
    email VARCHAR(150),
    numero VARCHAR(50) NOT NULL,

    CONSTRAINT fk_situation
        FOREIGN KEY (id_situation_familiale)
        REFERENCES situation_familiale(id),

    CONSTRAINT fk_nationalite
        FOREIGN KEY (id_nationalite)
        REFERENCES nationalite(id)
);

-- =========================
-- PASSEPORT
-- =========================

CREATE TABLE passeport (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(100) NOT NULL UNIQUE,
    date_delivrance DATE NOT NULL,
    lieu_delivrance VARCHAR(150) NOT NULL,
    date_expiration DATE NOT NULL,
    id_demandeur INT NOT NULL,

    CONSTRAINT fk_passeport_demandeur
        FOREIGN KEY (id_demandeur)
        REFERENCES demandeur(id)
        ON DELETE CASCADE
);

-- =========================
-- VISA TRANSFORMABLE
-- =========================

CREATE TABLE visa_transformable (
    id SERIAL PRIMARY KEY,
    date_entree DATE,
    lieu_entree VARCHAR(150),
    date_sortie DATE,
    lieu_sortie VARCHAR(150),
    numero_visa VARCHAR(100) NOT NULL,
    id_passeport INT NOT NULL,
    
    CONSTRAINT fk_vt_passeport
        FOREIGN KEY (id_passeport)
        REFERENCES passeport(id)
);

-- =========================
-- DEMANDE
-- =========================

CREATE TABLE demande (
    id SERIAL PRIMARY KEY,
    date_demande DATE NOT NULL DEFAULT CURRENT_DATE,
    id_categorie_demande INT NOT NULL,
    id_type_demande INT NOT NULL,
    id_demandeur INT NOT NULL,
    id_visa_transformable INT NOT NULL,
    CONSTRAINT fk_categorie
        FOREIGN KEY (id_categorie_demande)
        REFERENCES categorie_demande(id),

    CONSTRAINT fk_type
        FOREIGN KEY (id_type_demande)
        REFERENCES type_demande(id),

    CONSTRAINT fk_demandeur
        FOREIGN KEY (id_demandeur)
        REFERENCES demandeur(id),

    CONSTRAINT fk_visa_transformable
        FOREIGN KEY (id_visa_transformable)
        REFERENCES visa_transformable(id)
);

-- =========================
-- HISTORIQUE
-- =========================

CREATE TABLE historique_demande (
    id SERIAL PRIMARY KEY,
    id_demande INT NOT NULL,
    id_statut_demande INT NOT NULL,
    date_changement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    commentaire TEXT,

    CONSTRAINT fk_hist_demande
        FOREIGN KEY (id_demande)
        REFERENCES demande(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_hist_statut
        FOREIGN KEY (id_statut_demande)
        REFERENCES statut_demande(id)
);

-- =========================
-- CARTE RESIDENT
-- =========================

CREATE TABLE carte_resident (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(100) NOT NULL,
    id_passeport INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    id_demande INT NOT NULL,

    CONSTRAINT fk_cr_passeport
        FOREIGN KEY (id_passeport)
        REFERENCES passeport(id),

    CONSTRAINT fk_cr_demande
        FOREIGN KEY (id_demande)
        REFERENCES demande(id)
);

-- =========================
-- VISA CLASSIQUE
-- =========================

CREATE TABLE visa (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(100) NOT NULL,
    id_passeport INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    id_demande INT NOT NULL,

    CONSTRAINT fk_visa_passeport
        FOREIGN KEY (id_passeport)
        REFERENCES passeport(id),

    CONSTRAINT fk_visa_demande
        FOREIGN KEY (id_demande)
        REFERENCES demande(id)
);

-- =========================
-- DOCUMENTS
-- =========================

CREATE TABLE document (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(150) NOT NULL,
    type VARCHAR(20) CHECK (type IN ('COMMUN', 'SPECIFIQUE')) NOT NULL
);

CREATE TABLE categorie_document (
    id SERIAL PRIMARY KEY,
    id_categorie_demande INT NOT NULL,
    id_document INT NOT NULL,

    CONSTRAINT fk_cat_doc_categorie
        FOREIGN KEY (id_categorie_demande)
        REFERENCES categorie_demande(id),

    CONSTRAINT fk_cat_doc_document
        FOREIGN KEY (id_document)
        REFERENCES document(id),

    CONSTRAINT unique_categorie_document UNIQUE (id_categorie_demande, id_document)
);

CREATE TABLE document_demande (
    id SERIAL PRIMARY KEY,
    id_document INT NOT NULL,
    id_demande INT NOT NULL,

    CONSTRAINT fk_doc_demande_doc
        FOREIGN KEY (id_document)
        REFERENCES document(id),

    CONSTRAINT fk_doc_demande_demande
        FOREIGN KEY (id_demande)
        REFERENCES demande(id),

    CONSTRAINT unique_doc_demande UNIQUE (id_document, id_demande)
);