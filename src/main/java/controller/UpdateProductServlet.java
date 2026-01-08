package controller;

import dao.IngredientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "UpdateProductServlet", urlPatterns = {"/updateProduct"})
public class UpdateProductServlet extends HttpServlet {

    /**
     * Hàm xử lý chung cho cả GET và POST
     * Giúp bạn có thể dùng cả <form> hoặc gọi link trực tiếp từ JS
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // 1. Cấu hình tiếng Việt cho Request và Response
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        try {
            // 2. Lấy dữ liệu từ tham số gửi lên
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String unit = req.getParameter("unit");
            String image = req.getParameter("image");
            
            // Lấy chuỗi giá và số lượng
            String priceStr = req.getParameter("price");
            String qtyStr = req.getParameter("quantity");

            // 3. Kiểm tra dữ liệu bắt buộc (Validation đơn giản)
            if (idStr == null || idStr.trim().isEmpty()) {
                resp.getWriter().println("<h1>Lỗi: ID sản phẩm không hợp lệ!</h1>");
                return;
            }

            // 4. Xử lý chuyển đổi sang BigDecimal (Tránh lỗi nếu chuỗi rỗng)
            BigDecimal price = BigDecimal.ZERO;
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                // Xóa các ký tự không phải số (ví dụ dấu phẩy) nếu có
                priceStr = priceStr.replace(",", "").replace(".", ""); 
                // Lưu ý: Nếu input html là type="number" thì không cần replace, 
                // nhưng để chắc chắn ta cứ try-catch.
                try {
                     // Nếu input là số nguyên (ví dụ 20000), parse trực tiếp
                     price = new BigDecimal(req.getParameter("price")); 
                } catch (NumberFormatException e) {
                     System.out.println("Lỗi parse giá: " + e.getMessage());
                }
            }

            BigDecimal quantity = BigDecimal.ZERO;
            if (qtyStr != null && !qtyStr.trim().isEmpty()) {
                try {
                    quantity = new BigDecimal(qtyStr);
                } catch (NumberFormatException e) {
                     System.out.println("Lỗi parse số lượng: " + e.getMessage());
                }
            }

            // 5. Gọi DAO để cập nhật
            IngredientDAO dao = new IngredientDAO();
            boolean isUpdated = dao.updateIngredientFull(idStr, name, price, quantity, unit, image);

            // 6. Điều hướng sau khi xử lý
            if (isUpdated) {
                // Thành công -> Quay về trang Admin
                resp.sendRedirect("admin"); // Hoặc tên servlet admin của bạn
            } else {
                // Thất bại -> Thông báo lỗi
                resp.getWriter().println("<h1>Cập nhật thất bại!</h1>");
                resp.getWriter().println("<p>Không tìm thấy sản phẩm có ID: " + idStr + "</p>");
                resp.getWriter().println("<a href='admin'>Quay lại</a>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<h1>Đã xảy ra lỗi hệ thống!</h1>");
            resp.getWriter().println("<p>" + e.getMessage() + "</p>");
        }
    }

    // --- Các phương thức Override gọi về hàm xử lý chung ---

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
}