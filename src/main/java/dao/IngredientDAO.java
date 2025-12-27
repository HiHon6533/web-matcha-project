package dao;

import model.Ingredient;

public class IngredientDAO extends GenericDAO<Ingredient, Long> {

    public IngredientDAO() {
        super(Ingredient.class);
    }
}