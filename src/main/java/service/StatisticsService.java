package service;

import dao.StatisticsDAO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class StatisticsService {
    private final StatisticsDAO dao = new StatisticsDAO();

    public BigDecimal getTotalRevenueAllTime() {
        return dao.getTotalRevenueAllTime();
    }

    public BigDecimal getTotalRevenueBetween(LocalDateTime start, LocalDateTime end) {
        return dao.getTotalRevenueBetween(start, end);
    }

    public Long countCompletedOrdersBetween(LocalDateTime start, LocalDateTime end) {
        return dao.countCompletedOrdersBetween(start, end);
    }

    public Long countCompletedOrdersAllTime() {
        return dao.countCompletedOrdersAllTime();
    }

    /* New wrappers */
    public List getRecentOrders(int limit) {
        return dao.getRecentOrders(limit);
    }

    public List<Object[]> getTopProducts(int limit) {
        return dao.getTopProducts(limit);
    }

    public List<Object[]> getRevenueGroupedByDay(LocalDateTime start, LocalDateTime end) {
        return dao.getRevenueGroupedByDay(start, end);
    }
}
