package recette;

import ingredient.Ingredient;

public class RecetteDetail {
    Ingredient ingredient;
    double quantite;

    public RecetteDetail() {
    }
    public RecetteDetail(Ingredient ingredient, double quantite) {
        this.ingredient = ingredient;
        this.quantite = quantite;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    public double getQuantite() {
        return quantite;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }



}
