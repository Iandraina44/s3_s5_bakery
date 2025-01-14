CREATE VIEW vue_vente_details AS
SELECT 
    v.id_vente,
    v.quantite_vente,
    v.prix_unitaire_vente,
    v.prix_total_vente,
    v.date_vente,
    v.etat AS vente_etat,
    r.id_recette,
    r.nom_recette,
    r.prix_recette,
    r.etat AS recette_etat,
    g.id_gout,
    g.nom_gout,
    g.etat AS gout_etat,
    c.id_categorie,
    c.nom_categorie,
    c.etat AS categorie_etat
FROM 
    vente v
JOIN 
    recette r ON v.id_recette = r.id_recette
JOIN 
    gout g ON r.id_gout = g.id_gout
JOIN 
    categorie c ON r.id_categorie = c.id_categorie;
