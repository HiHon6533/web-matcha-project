//package dao;
//
//import model.Product;
//import model.Drink;
//import model.Ingredient;
//import java.util.List;
//
//public class ProductDAO extends GenericDAO<Product, Long> {
//
//    public ProductDAO() {
//        super(Product.class);
//    }
//
//    public List<Drink> getAllDrinks() {
//        return em.createQuery("FROM Drink", Drink.class).getResultList();
//    }
//
//    public List<Ingredient> getAllIngredients() {
//        return em.createQuery("FROM Ingredient", Ingredient.class).getResultList();
//    }
//}
