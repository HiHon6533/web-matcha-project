package service;

import dao.AddressDAO;
import model.Address;
import model.Customer;

public class AddressService
{
    private AddressDAO addressDAO = new AddressDAO();
    
    //Đặt địa chỉ làm địa chỉ mặc định
    public boolean changeDefaultAddress(Long userID, Long df_adddressID)
    {
        addressDAO.removeAllDefault(userID);
        return addressDAO.setAsDefault(df_adddressID);
    }
    
    //Dùng khi thêm địa chỉ mới
    public boolean addNewAddress(Customer customer, String province, String ward, 
                                 String hamlet, String houseNumber, String note, boolean df_addressID) 
    {

        boolean isFirstAddress = (customer.getAddresses() == null || customer.getAddresses().isEmpty());
        boolean finalIsDefault = df_addressID || isFirstAddress;

        if (finalIsDefault) {
            addressDAO.removeAllDefault(customer.getUserID());
        }

        Address newAddress = new Address();
        newAddress.setProvince(province);
        newAddress.setWard(ward);
        newAddress.setHamlet(hamlet);
        newAddress.setHouse_number(houseNumber);
        newAddress.setNote(note);
        newAddress.setIs_default(finalIsDefault); 
        newAddress.setCustomer(customer); 

        return addressDAO.insertAddress(newAddress);
    }    
}