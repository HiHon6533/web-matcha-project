package controller;

import model.Customer;
import service.AddressService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AddAddressServlet", urlPatterns = {"/add-address"})
public class AddAddressServlet extends HttpServlet {

    private AddressService addressService = new AddressService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        String province = request.getParameter("province"); 
        String ward = request.getParameter("ward");
        String hamlet = request.getParameter("hamlet");
        String houseNumber = request.getParameter("houseNumber");
        String note = request.getParameter("note");

        String isDefaultParam = request.getParameter("isDefault");
        boolean isUserChosenDefault = (isDefaultParam != null);

        boolean isSuccess = addressService.addNewAddress(
                customer, 
                province,   
                ward, 
                hamlet, 
                houseNumber, 
                note, 
                isUserChosenDefault
        );

        if (isSuccess) {
            response.sendRedirect("user-address-list"); 
        } else {            
            request.setAttribute("errorMessage", "Thêm địa chỉ thất bại!");
            request.getRequestDispatcher("add-address-form.jsp").forward(request, response);
        }
    }
}



// Nam làm jsp đặt tên theo này giúp Tú
//<form action="add-address" method="post">
//    
//    <input type="text" name="province" placeholder="Tỉnh/Thành phố">
        
//      Mấy cái kia nữa nha Nam
//    <div class="form-check">
//        <input class="form-check-input" type="checkbox" name="isDefault" id="defaultCheck">
//        <label class="form-check-label" for="defaultCheck">
//            Đặt làm địa chỉ mặc định
//        </label>
//    </div>
//
//    <button type="submit">Lưu địa chỉ</button>
//</form>