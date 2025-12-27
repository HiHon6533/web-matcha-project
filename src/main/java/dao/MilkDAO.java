package dao;

import model.Milk;

public class MilkDAO extends GenericDAO<Milk, Long> {

    public MilkDAO() {
        super(Milk.class);
    }
}