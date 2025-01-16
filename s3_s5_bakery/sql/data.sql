insert into categorie (nom_categorie,etat) values
('vienoiserie','true'),
('patisserie','true');


insert into gout (nom_gout,etat) values
('nature','true'),
('chocolat','true'),
('au fromage','true');

-- insert into ingredient (nom_ingredient,etat) values
-- ('farine','true'),
-- ('sucre','true'),
-- ('beurre','true'),
-- ('oeuf','true'),
-- ('lait','true'),
-- ('chocolat','true'),
-- ('fromage','true');

insert into recette (nom_recette, prix_recette,etat,id_gout,id_categorie) values
('croissant',1.5,'true',1,1),
('pain au chocolat',1.5,'true',2,1),
('pain au fromage',1.5,'true',3,1),
('tarte au chocolat',1.5,'true',2,2),
('tarte au fromage',1.5,'true',3,2),
('tarte nature',1.5,'true',1,2);


insert into recette (nom_recette, prix_recette,etat,id_gout,id_categorie) values
('phare breton',1.5,'true',1,1);



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



