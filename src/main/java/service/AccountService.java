package service;

import model.Account;
import model.Customer;
import dao.AccountDAO;
import util.PasswordUtil;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;

public class AccountService {
    
    private AccountDAO accountDAO = new AccountDAO();
    
    //REGISTER
    public String registerUser(String fullname, String phone, String email, String password) 
    {       
        if (accountDAO.checkEmail(email))
        {
            return "Email đã được sử dụng!";
        }
        
        //Mã hóa
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        
        //Tạo account mới
        Account newAccount = new Account();
        newAccount.setEmail(email);
        newAccount.setPassword(hashedPassword);
        newAccount.setActived(true);
        newAccount.setToken(null);
        newAccount.setCreatedAt(LocalDateTime.now());
        
        //Tạo customer mới
        Customer newCustomer = new Customer();
        newCustomer.setFullName(fullname);
        newCustomer.setPhoneNumber(phone);
        
        //Nạp vào database
        return accountDAO.register(newAccount, newCustomer) ? "Success" : "EROR!";
    }
    //LOGIN
    public Account login(String email, String password){
        Account account = accountDAO.findByEmail(email);

        if (!account.getActived()) {
            throw new RuntimeException("Tài khoản chưa được kích hoạt");
        }
        
        if (account == null) {
            throw new RuntimeException("Email chưa đăng ký");
        }

        boolean match = PasswordUtil.verify(password, account.getPassword());
        if (!match) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        return account;
        
    }
}
