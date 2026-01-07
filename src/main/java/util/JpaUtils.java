package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtils {
    // "matchaPU" là tên trong file persistence.xml của bạn
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("matchaPU");

    // Hàm này dùng để lấy kết nối
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}