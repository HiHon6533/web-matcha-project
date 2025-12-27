package dao;

import model.Payment;

public class PaymentDAO extends GenericDAO<Payment, Long> {

    public PaymentDAO() {
        super(Payment.class);
    }
}