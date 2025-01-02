CREATE TABLE ingredient(
   id_ingredient SERIAL,
   nom_ingredient VARCHAR(50)  NOT NULL,
   prix_ingredient NUMERIC(15,2)   NOT NULL,
   etat_ingredient BOOLEAN NOT NULL,
   PRIMARY KEY(id_ingredient)
);

CREATE TABLE recette(
   id_recette SERIAL,
   nom_recette VARCHAR(50)  NOT NULL,
   prix_recette NUMERIC(15,2)   NOT NULL,
   etat_recette BOOLEAN NOT NULL,
   PRIMARY KEY(id_recette)
);

CREATE TABLE achat_ingredient(
   id_achat_ingredient SERIAL,
   prix_unitaire_ingredient NUMERIC(15,2)   NOT NULL,
   prix_total_ingredient NUMERIC(15,2)   NOT NULL,
   date_achat_ingredient DATE NOT NULL,
   id_ingredient INTEGER NOT NULL,
   PRIMARY KEY(id_achat_ingredient),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id_ingredient)
);

CREATE TABLE fabrication(
   id_fabrication SERIAL,
   quantite_fabrication NUMERIC(15,2)   NOT NULL,
   prix_unitaire_fabrication NUMERIC(15,2)   NOT NULL,
   prix_total_fabrication NUMERIC(15,2)   NOT NULL,
   date_fabrication DATE NOT NULL,
   id_recette INTEGER NOT NULL,
   PRIMARY KEY(id_fabrication),
   FOREIGN KEY(id_recette) REFERENCES recette(id_recette)
);

CREATE TABLE vente_details(
   id_vente_details SERIAL,
   quantite_vente_details NUMERIC(15,2)   NOT NULL,
   prix_unitaire_vente_details NUMERIC(15,2)   NOT NULL,
   prix_total_vente_details NUMERIC(15,2)   NOT NULL,
   etat_vente_details VARCHAR(50) ,
   id_recette INTEGER NOT NULL,
   PRIMARY KEY(id_vente_details),
   FOREIGN KEY(id_recette) REFERENCES recette(id_recette)
);

CREATE TABLE vente(
   id_vente SERIAL,
   date_vente DATE NOT NULL,
   etat_vente VARCHAR(50) ,
   PRIMARY KEY(id_vente)
);

CREATE TABLE recette_ingredient(
   id_ingredient INTEGER,
   id_recette INTEGER,
   PRIMARY KEY(id_ingredient, id_recette),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id_ingredient),
   FOREIGN KEY(id_recette) REFERENCES recette(id_recette)
);

CREATE TABLE vente_vente_details(
   id_vente_details INTEGER,
   id_vente INTEGER,
   PRIMARY KEY(id_vente_details, id_vente),
   FOREIGN KEY(id_vente_details) REFERENCES vente_details(id_vente_details),
   FOREIGN KEY(id_vente) REFERENCES vente(id_vente)
);
