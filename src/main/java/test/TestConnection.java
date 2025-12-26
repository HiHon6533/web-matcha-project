
package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("--- Đang thử kết nối... ---");
        
        try {
            // "matchaPU" phải trùng y hệt tên trong persistence.xml nha Sensei
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("matchaPU");
            EntityManager em = emf.createEntityManager();
            
            System.out.println("--------------------------------------------");
            System.out.println("KẾT NỐI THÀNH CÔNG RỒI SENSEI ƠI! (≧◡≦)");
            System.out.println("--------------------------------------------");
            
            em.close();
            emf.close();
            
        } catch (Exception e) {
            System.out.println("Huhu, lỗi kết nối rồi Sensei:");
            e.printStackTrace(); // Nó sẽ hiện rõ lỗi sai pass hay sai tên DB ở đây
        }
    }
}
