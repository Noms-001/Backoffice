ALTER TABLE historique_demande
ALTER COLUMN date_changement TYPE TIMESTAMP,
ALTER COLUMN date_changement SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE historique_demande
ALTER COLUMN date_changement SET NOT NULL;