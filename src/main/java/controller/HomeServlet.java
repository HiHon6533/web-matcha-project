package controller;

import service.DrinkService; // Import Service
import model.Drink;          // Import Model
import java.util.List;       // Import List
import java.util.ArrayList;  // Import ArrayList để xử lý list rỗng
import model.Matcha;
import model.Milk;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    // Khai báo Service
    private DrinkService drinkService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo Service khi Servlet bắt đầu
        drinkService = new DrinkService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        // Khởi tạo danh sách mặc định là rỗng (tránh bị null gây lỗi bên JSP)
        List<Drink> listFeatured = new ArrayList<>();
        List<Matcha> listMatcha = new ArrayList<>();
        List<Milk> listMilk = new ArrayList<>();
        try {
            // 1. Gọi Service lấy sản phẩm
            listFeatured = drinkService.getFeaturedDrinks();
            listMatcha = drinkService.getAllMatchaTypes();
            listMilk = drinkService.getAllMilkTypes();
            // Log ra console để kiểm tra xem lấy được bao nhiêu sản phẩm (DEBUG)
            System.out.println("HomeServlet: Số lượng sản phẩm lấy được = " + (listFeatured != null ? listFeatured.size() : "null"));
            System.out.println("- Số lượng Matcha: " + (listMatcha != null ? listMatcha.size() : 0));
            System.out.println("- Số lượng Milk: " + (listMilk != null ? listMilk.size() : 0));
        } catch (Exception e) {
            // Nếu có lỗi kết nối DB, in lỗi ra console server thay vì làm chết trang web
            e.printStackTrace();
        }

        // 2. Kiểm tra an toàn: Nếu list bị null thì gán lại thành rỗng để vòng lặp c:forEach không chết
        if (listFeatured == null) {
            listFeatured = new ArrayList<>();
        }

        // Đẩy dữ liệu sang JSP
        req.setAttribute("featuredProducts", listFeatured);
        req.setAttribute("listMatcha", listMatcha); // Tên biến để dùng trong forEach
        req.setAttribute("listMilk", listMilk);

        // 3. Chuyển hướng về trang chủ

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}