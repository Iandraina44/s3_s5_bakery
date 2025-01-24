insert into categorie (nom_categorie,etat) values
('vienoiserie','true'),
('patisserie','true');


insert into gout (nom_gout,etat) values
('nature','true'),
('chocolat','true'),
('au fromage','true');

insert into genre (nom_genre)values ('homme'),('femme');

insert into vendeur (nom_vendeur,id_genre) values
('vendeur 1',1),
('vendeur2',1),
('vendeur 3',2),
('vendeur 4',2);

insert into client (nom_client,etat) values
('Rakoto','true'),
('Rabe','true'),
('Rasoa','true');


insert into recette (nom_recette, prix_recette,etat,id_gout,id_categorie) values
('croissant',15000,'true',1,1),
('pain au chocolat',10000,'true',2,1),
('pain au fromage',12000,'true',3,1),
('tarte au chocolat',13000,'true',2,2),
('tarte au fromage',14000,'true',3,2),
('tarte nature',15000,'true',1,2);
insert into recette (nom_recette, prix_recette,etat,id_gout,id_categorie) values
('phare breton',16000,'true',1,1);





-- insert into ingredient (nom_ingredient,etat) values
-- ('farine','true'),
-- ('sucre','true'),
-- ('beurre','true'),
-- ('oeuf','true'),
-- ('lait','true'),
-- ('chocolat','true'),
-- ('fromage','true');

insert into recette_detail (quantite,etat,id_recette,id_ingredient) values
(500,'true',1,1),
(100,'true',1,2),
(100,'true',1,3),
(100,'true',1,4),
(100,'true',1,5),
(100,'true',2,1),
(100,'true',2,2),
(100,'true',2,3),
(100,'true',2,4),
(100,'true',2,5),
(100,'true',2,6),
(100,'true',3,1),
(100,'true',3,2),
(100,'true',3,3),
(100,'true',3,4),
(100,'true',3,5),
(100,'true',3,7);

insert into vente (quantite_vente,prix_unitaire_vente,prix_total_vente,etat,id_recette) values
(10,1.5,15,'true',1),
(10,1.5,15,'true',2),
(10,1.5,15,'true',3);



