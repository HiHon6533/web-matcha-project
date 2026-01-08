package dao;

import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import model.Order;

public class StatisticsDAO {

    /**
     * Tổng doanh thu không có filter (tất cả các đơn 'Hoàn thành').
     */
    public BigDecimal getTotalRevenueAllTime() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BigDecimal> q = em.createQuery(
                "SELECT COALESCE(SUM(o.total), 0) FROM Order o WHERE o.orderStatus = 'Hoàn thành'",
                BigDecimal.class
            );
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Tổng doanh thu giữa 2 thời điểm (bao gồm) cho các order 'Hoàn thành'.
     */
    public BigDecimal getTotalRevenueBetween(LocalDateTime start, LocalDateTime end) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BigDecimal> q = em.createQuery(
                "SELECT COALESCE(SUM(o.total), 0) FROM Order o " +
                "WHERE o.orderStatus = 'Hoàn thành' AND o.createdAt >= :start AND o.createdAt <= :end",
                BigDecimal.class
            );
            q.setParameter("start", start);
            q.setParameter("end", end);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Đếm đơn hàng đã hoàn thành giữa 2 thời điểm.
     */
    public Long countCompletedOrdersBetween(LocalDateTime start, LocalDateTime end) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(o) FROM Order o " +
                "WHERE o.orderStatus = 'Hoàn thành' AND o.createdAt >= :start AND o.createdAt <= :end",
                Long.class
            );
            q.setParameter("start", start);
            q.setParameter("end", end);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Đếm tất cả đơn hoàn thành (all time).
     */
    public Long countCompletedOrdersAllTime() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'Hoàn thành'",
                Long.class
            );
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Order> getRecentOrders(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Order> q = em.createQuery(
                "SELECT o FROM Order o ORDER BY o.createdAt DESC", Order.class
            );
            q.setMaxResults(limit);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Top sản phẩm bán chạy theo tổng quantity trong các order 'Hoàn thành'.
     * Trả về List<Object[]> mỗi phần tử: [productName (String), totalQty (Long)]
     */
    public List<Object[]> getTopProducts(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> q = em.createQuery(
                "SELECT p.productName, COALESCE(SUM(li.quantity),0) " +
                "FROM Order o JOIN o.products li JOIN li.product p " +
                "WHERE o.orderStatus = 'Hoàn thành' " +
                "GROUP BY p.productName " +
                "ORDER BY SUM(li.quantity) DESC", Object[].class
            );
            q.setMaxResults(limit);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Lấy doanh thu GROUP BY day giữa start và end.
     * Trả về List<Object[]>: [year(Integer), month(Integer), day(Integer), sum(BigDecimal)]
     */
    public List<Object[]> getRevenueGroupedByDay(LocalDateTime start, LocalDateTime end) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> q = em.createQuery(
                "SELECT YEAR(o.createdAt), MONTH(o.createdAt), DAY(o.createdAt), COALESCE(SUM(o.total),0) " +
                "FROM Order o " +
                "WHERE o.orderStatus = 'Hoàn thành' AND o.createdAt >= :start AND o.createdAt <= :end " +
                "GROUP BY YEAR(o.createdAt), MONTH(o.createdAt), DAY(o.createdAt) " +
                "ORDER BY YEAR(o.createdAt), MONTH(o.createdAt), DAY(o.createdAt)",
                Object[].class
            );
            q.setParameter("start", start);
            q.setParameter("end", end);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
