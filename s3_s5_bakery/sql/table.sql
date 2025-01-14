CREATE TABLE ingredient(
   id_ingredient SERIAL,
   nom_ingredient VARCHAR(50)  NOT NULL,
   prix_ingredient NUMERIC(15,2)   NOT NULL,
   etat BOOLEAN NOT NULL,
   PRIMARY KEY(id_ingredient)
);

CREATE TABLE stock_ingredient(
   id_stock_ingredient SERIAL,
   quantite_stock_ingredient NUMERIC(15,2)   NOT NULL,
   etat BOOLEAN NOT NULL,
   date_stock_ingredient TIMESTAMP NOT NULL,
   id_ingredient INTEGER NOT NULL,
   PRIMARY KEY(id_stock_ingredient),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id_ingredient)
);

CREATE TABLE categorie(
   id_categorie SERIAL,
   nom_categorie VARCHAR(50)  NOT NULL,
   etat BOOLEAN NOT NULL,
   PRIMARY KEY(id_categorie)
);

CREATE TABLE gout(
   id_gout SERIAL,
   nom_gout VARCHAR(50)  NOT NULL,
   etat BOOLEAN NOT NULL,
   PRIMARY KEY(id_gout)
);

CREATE TABLE recette(
   id_recette SERIAL,
   nom_recette VARCHAR(50)  NOT NULL,
   prix_recette NUMERIC(15,2)   NOT NULL,
   etat BOOLEAN NOT NULL,
   id_gout INTEGER NOT NULL,
   id_categorie INTEGER NOT NULL,
   PRIMARY KEY(id_recette),
   FOREIGN KEY(id_gout) REFERENCES gout(id_gout),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie)
);

CREATE TABLE achat_ingredient(
   id_achat_ingredient SERIAL,
   prix_unitaire_ingredient NUMERIC(15,2)   NOT NULL,
   prix_total_ingredient NUMERIC(15,2)   NOT NULL,
   quantite_achat NUMERIC(15,2)   NOT NULL,
   date_achat_ingredient TIMESTAMP NOT NULL,
   etat BOOLEAN NOT NULL,
   id_stock_ingredient INTEGER NOT NULL,
   id_ingredient INTEGER NOT NULL,
   PRIMARY KEY(id_achat_ingredient),
   FOREIGN KEY(id_stock_ingredient) REFERENCES stock_ingredient(id_stock_ingredient),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id_ingredient)
);

CREATE TABLE fabrication(
   id_fabrication SERIAL,
   quantite_fabrication NUMERIC(15,2)   NOT NULL,
   date_fabrication TIMESTAMP NOT NULL,
   etat BOOLEAN NOT NULL,
   id_stock_ingredient INTEGER NOT NULL,
   id_recette INTEGER NOT NULL,
   PRIMARY KEY(id_fabrication),
   FOREIGN KEY(id_stock_ingredient) REFERENCES stock_ingredient(id_stock_ingredient),
   FOREIGN KEY(id_recette) REFERENCES recette(id_recette)
);

CREATE TABLE recette_detail(
   id_recette_detail SERIAL,
   quantite NUMERIC(15,2)   NOT NULL,
   etat BOOLEAN NOT NULL,
   id_recette INTEGER NOT NULL,
   id_ingredient INTEGER NOT NULL,
   PRIMARY KEY(id_recette_detail),
   FOREIGN KEY(id_recette) REFERENCES recette(id_recette),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id_ingredient)
);

CREATE TABLE vente(
   id_vente SERIAL,
   quantite_vente NUMERIC(15,2)   NOT NULL,
   prix_unitaire_vente NUMERIC(15,2)   NOT NULL,
   prix_total_vente NUMERIC(15,2)   NOT NULL,
   date_vente TIMESTAMP NOT NULL,
   etat BOOLEAN NOT NULL,
   id_recette INTEGER NOT NULL,
   PRIMARY KEY(id_vente),
   FOREIGN KEY(id_recette) REFERENCES recette(id_recette)
);
