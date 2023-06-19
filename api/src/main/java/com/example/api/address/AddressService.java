package com.example.api.address;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.location.Location;
import com.example.api.location.LocationRepository;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.province.Province;
import com.example.api.province.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class AddressService {
    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, PersonRepository personRepository, LocationRepository locationRepository, ProvinceRepository provinceRepository)
    {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
        this.provinceRepository = provinceRepository;
        this.locationRepository = locationRepository;
    }

    public List<Address> getAddresses(){return this.addressRepository.findByDeletedAtIsNull();}

    public APIResponse newAddress(AddressDTO addressDTO){
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setSection(addressDTO.getSection());
        address.setNumber(addressDTO.getNumber());
        address.setFloor(addressDTO.getFloor());
        address.setApartment(addressDTO.getApartment());
        address.setZip(addressDTO.getZip());

        address.setComplete_address(getCompleteAddress(addressDTO));

        Optional<Person> optionalPerson = personRepository.findById(addressDTO.getPersonId());
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();
        address.setPerson(person);

        Optional<Location> optionalLocation = locationRepository.findByIdAndDeletedAtIsNull(addressDTO.getLocationId());
        if(!optionalLocation.isPresent()){
            Location location = null;
        }
        Location location = optionalLocation.get();
        address.setLocation(location);

        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(addressDTO.getProvinceId());
        if(!optionalProvince.isPresent()){
            Province province = null;
        }
        Province province = optionalProvince.get();
        address.setProvince(province);

        addressRepository.save(address);
        data.put("message", MessagesResponse.addSuccess);
        data.put("data", address);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editAddress(Long id, AddressDTO addressDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Address> optionalAddress = addressRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalAddress.isPresent()){
            Address address = optionalAddress.get();
            address.setStreet(addressDTO.getStreet());
            address.setSection(addressDTO.getSection());
            address.setNumber(addressDTO.getNumber());
            address.setFloor(addressDTO.getFloor());
            address.setApartment(addressDTO.getApartment());
            address.setZip(addressDTO.getZip());
            address.setComplete_address(getCompleteAddress(addressDTO));

            Optional<Person> optionalPerson = personRepository.findById(addressDTO.getPersonId());
            if(!optionalPerson.isPresent()){
                Person person = null;
            }
            Person person = optionalPerson.get();
            address.setPerson(person);

            Optional<Location> optionalLocation = locationRepository.findByIdAndDeletedAtIsNull(addressDTO.getLocationId());
            if(!optionalLocation.isPresent()){
                Location location = null;
            }
            Location location = optionalLocation.get();
            address.setLocation(location);

            Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(addressDTO.getProvinceId());
            if(!optionalProvince.isPresent()){
                Province province = null;
            }
            Province province = optionalProvince.get();
            address.setProvince(province);

            addressRepository.save(address);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", address);
            apiResponse.setData(data);

        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deleteAddress(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        boolean exists = this.addressRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Address> optionalAddress = addressRepository.findById(id);

        Address existingAddress = optionalAddress.get();
        existingAddress.setDeletedAt(LocalDateTime.now());
        addressRepository.save(existingAddress);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }

    public String getCompleteAddress(AddressDTO addressDTO){
        String completeAddress = "";

        if (addressDTO.getStreet() != null) {
            completeAddress = addressDTO.getStreet() + " ";
        }
        if (addressDTO.getNumber() != null) {
            completeAddress = completeAddress.concat(addressDTO.getNumber()) + " - ";
        }

        if (addressDTO.getFloor() != null) {
            completeAddress = completeAddress.concat(addressDTO.getFloor()) + "  ";
        }
        if (addressDTO.getApartment() != null) {
            completeAddress = completeAddress.concat(addressDTO.getApartment());
        }
        completeAddress = completeAddress.concat(" - ");
        if (addressDTO.getSection() != null) {
            completeAddress = completeAddress.concat(addressDTO.getSection() + " - ");
        }
        if (addressDTO.getZip() != null) {
            completeAddress = completeAddress.concat(addressDTO.getZip());
        }
        return completeAddress;
    }

    public HashMap createAddress(AddressDTO addressDTO,HashMap data, Long personId){
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setSection(addressDTO.getSection());
        address.setNumber(addressDTO.getNumber());
        address.setFloor(addressDTO.getFloor());
        address.setApartment(addressDTO.getApartment());
        address.setZip(addressDTO.getZip());
        address.setComplete_address(getCompleteAddress(addressDTO));

        Optional<Location> optionalLocation = locationRepository.findByIdAndDeletedAtIsNull(addressDTO.getLocationId());
        if(!optionalLocation.isPresent()){
            Location location = null;
        }
        Location location = optionalLocation.get();
        address.setLocation(location);

        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(addressDTO.getProvinceId());
        if(!optionalProvince.isPresent()){
            Province province = null;
        }
        Province province = optionalProvince.get();
        address.setProvince(province);

        Optional<Person> optionalPerson = personRepository.findById(personId);
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();
        address.setPerson(person);

        addressRepository.save(address);
        data.put("address-message", MessagesResponse.addSuccess);
        data.put("address", address);
        return data;
    }
}
