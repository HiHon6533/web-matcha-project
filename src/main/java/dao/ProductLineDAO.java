package dao;

import model.ProductLine;

public class ProductLineDAO extends GenericDAO<ProductLine, Long> {

    public ProductLineDAO() {
        super(ProductLine.class);
    }
}