package dao;

import model.Matcha;

public class MatchaDAO extends GenericDAO<Matcha, Long> {

    public MatchaDAO() {
        super(Matcha.class);
    }
}