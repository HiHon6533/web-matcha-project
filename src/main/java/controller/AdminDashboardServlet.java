package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StatisticsService;
import service.AdminService;
import service.DrinkService;
import model.Matcha;
import model.Milk;
import model.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final StatisticsService statisticsService = new StatisticsService();
    private final AdminService adminService = new AdminService();
    private final DrinkService drinkService = new DrinkService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filter = request.getParameter("filter");
        if (filter == null) filter = "today";

        LocalDateTime start = null;
        LocalDateTime end = null;

        LocalDate today = LocalDate.now();

        switch (filter) {
            case "today":
                start = today.atStartOfDay();
                end = LocalDateTime.of(today, LocalTime.MAX);
                break;
            case "week":
                LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                start = monday.atStartOfDay();
                end = LocalDateTime.of(sunday, LocalTime.MAX);
                break;
            case "month":
                LocalDate first = today.with(TemporalAdjusters.firstDayOfMonth());
                LocalDate last = today.with(TemporalAdjusters.lastDayOfMonth());
                start = first.atStartOfDay();
                end = LocalDateTime.of(last, LocalTime.MAX);
                break;
            case "all":
            default:
                start = null;
                end = null;
                break;
        }

        // Lấy tổng doanh thu và tổng đơn hàng theo filter (có xử lý null)
        BigDecimal totalRevenue;
        Long totalOrders;

        if (start != null && end != null) {
            totalRevenue = safeGet(statisticsService.getTotalRevenueBetween(start, end));
            totalOrders = safeLong(statisticsService.countCompletedOrdersBetween(start, end));
        } else {
            totalRevenue = safeGet(statisticsService.getTotalRevenueAllTime());
            totalOrders = safeLong(statisticsService.countCompletedOrdersAllTime());
        }

        // Tính doanh thu kỳ trước để so sánh (ví dụ: hôm trước / tuần trước / tháng trước)
        BigDecimal prevRevenue = BigDecimal.ZERO;
        if (start != null && end != null) {
            LocalDateTime prevStart = start;
            LocalDateTime prevEnd = end;
            switch (filter) {
                case "today":
                    prevStart = start.minusDays(1);
                    prevEnd = end.minusDays(1);
                    break;
                case "week":
                    prevStart = start.minusWeeks(1);
                    prevEnd = end.minusWeeks(1);
                    break;
                case "month":
                    prevStart = start.minusMonths(1);
                    prevEnd = end.minusMonths(1);
                    break;
            }
            prevRevenue = safeGet(statisticsService.getTotalRevenueBetween(prevStart, prevEnd));
        }

        // Tính phần trăm thay đổi
        double revenueChangePercent = 0.0;
        String revenueChangeSign = "neutral";
        if (prevRevenue != null && prevRevenue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal diff = totalRevenue.subtract(prevRevenue);
            // tránh chia cho 0, dùng RoundingMode
            revenueChangePercent = diff
                    .divide(prevRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100))
                    .doubleValue();
            revenueChangeSign = (diff.compareTo(BigDecimal.ZERO) > 0) ? "up"
                    : (diff.compareTo(BigDecimal.ZERO) < 0 ? "down" : "neutral");
        } else {
            // nếu prev = 0 → nếu totalRevenue>0 thì coi là tăng 100%
            revenueChangePercent = (totalRevenue.compareTo(BigDecimal.ZERO) > 0) ? 100.0 : 0.0;
            revenueChangeSign = (totalRevenue.compareTo(BigDecimal.ZERO) > 0) ? "up" : "neutral";
        }

        // Lấy 7 ngày gần nhất để vẽ biểu đồ (labels và values)
        LocalDateTime chartStart = today.minusDays(6).atStartOfDay();
        LocalDateTime chartEnd = LocalDateTime.of(today, LocalTime.MAX);

        List<Object[]> revenueByDay = statisticsService.getRevenueGroupedByDay(chartStart, chartEnd);
        if (revenueByDay == null) revenueByDay = new ArrayList<>();

        // Khởi tạo label list (dd/MM) và value list (BigDecimal) theo 7 ngày liên tiếp
        List<String> chartLabels = new ArrayList<>();
        List<BigDecimal> chartValues = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            String lbl = String.format("%02d/%02d", d.getDayOfMonth(), d.getMonthValue());
            chartLabels.add(lbl);
            chartValues.add(BigDecimal.ZERO);
        }
        // điền giá trị từ revenueByDay (object[]: year,month,day,sum)
        for (Object[] row : revenueByDay) {
            if (row == null || row.length < 4) continue;
            Integer y = (Integer) row[0];
            Integer m = (Integer) row[1];
            Integer d = (Integer) row[2];
            BigDecimal sum = (BigDecimal) row[3];
            if (sum == null) sum = BigDecimal.ZERO;
            String lbl = String.format("%02d/%02d", d, m);
            int idx = chartLabels.indexOf(lbl);
            if (idx >= 0) chartValues.set(idx, sum);
        }

        // Lấy recent orders và format createdAt -> chuỗi dd/MM/yyyy HH:mm để JSP dễ hiển thị
        List<Object> recentOrdersRaw = statisticsService.getRecentOrders(8);
        if (recentOrdersRaw == null) recentOrdersRaw = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Map<String, Object>> recentOrders = new ArrayList<>();
        for (Object obj : recentOrdersRaw) {
            try {
                Order order = (Order) obj; // Nếu class Order nằm ở package khác, điều chỉnh import
                Map<String, Object> m = new HashMap<>();
                m.put("orderID", order.getOrderID());
                m.put("customer", order.getCustomer()); // giữ object để JSP sử dụng .fullName/.phoneNumber
                if (order.getCreatedAt() != null) {
                    m.put("createdAtStr", order.getCreatedAt().format(dtf));
                } else {
                    m.put("createdAtStr", "");
                }
                m.put("total", order.getTotal());
                m.put("orderStatus", order.getOrderStatus());
                recentOrders.add(m);
            } catch (ClassCastException ex) {
                // bỏ qua nếu không cast được
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Object[]> topProducts = statisticsService.getTopProducts(5);
        if (topProducts == null) topProducts = new ArrayList<>();

        // ================== PHẦN MỚI: LẤY DỮ LIỆU CHO TAB "SẢN PHẨM" VÀ "ĐƠN HÀNG" ===================
        List<Matcha> listMatcha = new ArrayList<>();
        List<Milk> listMilk = new ArrayList<>();
        List<Order> listOrders = new ArrayList<>();
        try {
            listMatcha = drinkService.getAllMatchaTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            listMilk = drinkService.getAllMilkTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // lấy tất cả orders (admin view). Nếu bạn chỉ cần một số trạng thái, chỉnh adminService.getAllOrders() tương ứng.
            listOrders = adminService.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // =======================================================================================

        // Đẩy attribute cho JSP
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("selectedFilter", filter);
        request.setAttribute("revenueChangePercent", revenueChangePercent);
        request.setAttribute("revenueChangeSign", revenueChangeSign);
        request.setAttribute("chartLabels", chartLabels);
        request.setAttribute("chartValues", chartValues);
        request.setAttribute("recentOrders", recentOrders);
        request.setAttribute("topProducts", topProducts);

        // Đẩy thêm dữ liệu cho tab products / orders
        request.setAttribute("listMatcha", listMatcha);
        request.setAttribute("listMilk", listMilk);
        request.setAttribute("orders", listOrders);

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    // Helper: đảm bảo không trả về null cho BigDecimal
    private BigDecimal safeGet(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    // Helper: đảm bảo Long không null
    private Long safeLong(Long l) {
        return l == null ? 0L : l;
    }
}
