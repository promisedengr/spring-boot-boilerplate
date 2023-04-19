package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Address;
import com.nichoshop.main.repository.AddressRepository;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).get();
    }

    public Address getAddressByUserId(Long id) {
        return addressRepository.findByUserId(id);
    }

    public Address getAddressByUserIdAndType(Long userId, int addressType){
        return addressRepository.findByUserIdAndType(userId, addressType);
    }

    public void deleteAddressById(Long id){
        addressRepository.deleteById(id);
    }

    public void updateAddressStatus(Long addressId, int status){
        Address addressFromDB = addressRepository.findById(addressId).get();
        addressFromDB.setStatus(status);
        addressRepository.save(addressFromDB);
    }

    public Long createAddress (Address address){
        Address newAddress = addressRepository.save(address);
        return newAddress.getId();
    }

    public void changeAddress(Long addressId, String addressName, String address1, String address2, String city, String country, String state, String phone, String zip){

            Address addressFromDB = addressRepository.findById(addressId).get();
            addressFromDB.setName(addressName);
            addressFromDB.setAddress1(address1);
            addressFromDB.setAddress2(address2);
            addressFromDB.setCity(city);
            addressFromDB.setCountry(country);
            addressFromDB.setState(state);
            addressFromDB.setPhone(phone);
            addressFromDB.setZip(zip);
            addressRepository.save(addressFromDB);
    }
    public List<Address> getAllAddresses() {
      List<Address> Addresses = new ArrayList<Address>();
      addressRepository.findAll().forEach(Address -> Addresses.add(Address));
      return Addresses;
   }
}
// public class AddressService {
//    @Autowired
//    AddressRepo repository;





