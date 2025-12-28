package service;

import model.Account;
import dao.AccountDAO;
import util.PasswordUtil;

public class AccountService {
    
    private AccountDAO accountDAO = new AccountDAO();
    
    //REGISTER
    public void register(){
        
    }
    //LOGIN
    public Account login(String email, String password){
        Account account = accountDAO.findByEmail(email);

        if (account == null) {
            throw new RuntimeException("Email không tồn tại");
        }

        if (!PasswordUtil.verify(password, account.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        return account;

    }
    //CHANGE PASSWORD
    public void changePassword(){
        
    }
}
