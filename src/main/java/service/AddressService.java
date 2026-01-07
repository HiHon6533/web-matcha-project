package service;

import dao.AddressDAO;
import model.Address;
import model.Customer;

public class AddressService {
    private AddressDAO addressDAO = new AddressDAO();

    public boolean changeDefaultAddress(Long userID, Long df_adddressID) {
        return addressDAO.changeDefaultAddress(userID, df_adddressID);
    }
    
    public boolean deleteUserAddress(Long userId, Long addressId) {
        return addressDAO.deleteAddress(addressId, userId);
    }

    public boolean addNewAddress(Customer customer, String province, String ward, 
                                 String hamlet, String houseNumber, String note, boolean isUserChosenDefault) {
        
        // --- SỬA LỖI Ở ĐÂY ---
        // Thay vì dùng customer.getAddresses().isEmpty() (gây lỗi Lazy Load),
        // ta hỏi DB xem ông khách này đã có địa chỉ nào chưa.
        boolean hasAnyAddress = addressDAO.checkHasAddress(customer.getUserID());
        
        // Nếu chưa có địa chỉ nào -> Đây là địa chỉ đầu tiên
        boolean isFirstAddress = !hasAnyAddress;        
        
        // Logic cũ: Mặc định = (Người dùng chọn) HOẶC (Là cái đầu tiên)
        boolean finalIsDefault = isUserChosenDefault || isFirstAddress;

        Address newAddress = new Address();
        newAddress.setProvince(province);
        newAddress.setWard(ward);
        newAddress.setHamlet(hamlet);
        newAddress.setHouse_number(houseNumber);
        newAddress.setNote(note);
        newAddress.setCustomer(customer);
        newAddress.setIs_default(finalIsDefault);

        return addressDAO.insertAddress(newAddress);
    }
    
}