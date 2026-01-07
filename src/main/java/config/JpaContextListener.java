package config;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import util.JPAUtil;

// Annotation báo cho Server biết đây là class chạy ngầm khi start
@WebListener 
public class JpaContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManager em = JPAUtil.getEntityManager();
        
        em.close();     
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Đóng kết nối khi tắt server
        JPAUtil.shutdown();
    }
}