package service;

import model.*;
import dao.*;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();
    //Tim customer tu email
    public Customer findCustomerByEmail(String email){
        Customer customer = customerDAO.findByAccountEmail(email);
        if (customer == null){
            throw new RuntimeException("Không tìm thấy customer tương ứng");
        }
        return customer;
    }
}
