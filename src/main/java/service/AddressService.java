package service;

import dao.AddressDAO;
import model.Address;
import model.Customer;
import java.util.List;

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
        
        boolean isFirstAddress = (customer.getAddresses() == null || customer.getAddresses().isEmpty());        
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
    
    public List<Address> getAddressesByUserId(Long userId) {
        return addressDAO.findByUserId(userId);
    }    
}