package service;

import model.Account;
import model.Customer;
import dao.AccountDAO;
import util.PasswordUtil;
import util.TokenUtil;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import service.EmailService;

public class AccountService {
    
    private AccountDAO accountDAO = new AccountDAO();
    private EmailService emailService = new EmailService();
    
    //REGISTER
    public String registerUser(String fullname, String phone, String email, String password, String baseUrl) 
    {       
        if (accountDAO.checkEmail(email))
        {
            return "Email đã được sử dụng!";
        }
        
        //Mã hóa
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        //Tạo token lúc tạo tk nè
        String randomToken = TokenUtil.generateToken();
        
        //Tạo account mới
        Account newAccount = new Account();
        newAccount.setEmail(email);
        newAccount.setPassword(hashedPassword);
        newAccount.setActived(false);
        newAccount.setToken(randomToken);
        newAccount.setTokenExpiry(LocalDateTime.now().plusHours(1));
        newAccount.setCreatedAt(LocalDateTime.now());
        
        //Tạo customer mới
        Customer newCustomer = new Customer();
        newCustomer.setFullName(fullname);
        newCustomer.setPhoneNumber(phone);
        
        //Nạp vào database
        boolean isSuccess = accountDAO.register(newAccount, newCustomer);

        if (isSuccess) {
            new Thread(() -> {
                emailService.sendVerificationEmail(email, fullname, randomToken, baseUrl);
            }).start();

            return "Success";
        } else {
            return "ERROR!";
        }
    }
    //LOGIN
    public Account login(String email, String password){
        Account account = accountDAO.findByEmail(email);
        
        if (account == null) {
            throw new RuntimeException("Email chưa đăng ký");
        }

        boolean match = PasswordUtil.verify(password, account.getPassword());
        if (!match) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        if (!account.getActived()) {
            throw new RuntimeException("Tài khoản chưa được kích hoạt");
        }
        return account;
        
    }

    //CHANGE PASSWORD
    public void changePassword(){
        
    }
    
    public boolean verifyAccount(String token) {
        Account account = accountDAO.findByToken(token);
        if (account == null) {
        return false;
    }
        if (account.getTokenExpiry() != null && LocalDateTime.now().isAfter(account.getTokenExpiry())) {
            account.setToken(null);
            account.setTokenExpiry(null);
            accountDAO.update(account);
            return false; 
        }
        account.setActived(true);
        account.setToken(null);
        account.setTokenExpiry(null);
        
        return accountDAO.update(account);
    }
}
