package com.example.api.address;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.common.NotFoundException;
import com.example.api.common.NotValidException;
import com.example.api.location.Location;
import com.example.api.location.LocationRepository;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.province.Province;
import com.example.api.province.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        validateAddress(addressDTO);
        APIResponse apiResponse = new APIResponse();

        Optional<Person> optionalPerson = findPerson(addressDTO.getPersonId());

        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setSection(addressDTO.getSection());
        address.setNumber(addressDTO.getNumber());
        address.setFloor(addressDTO.getFloor());
        address.setApartment(addressDTO.getApartment());
        address.setZip(addressDTO.getZip());

        address.setComplete_address(getCompleteAddress(addressDTO));
        Person person = optionalPerson.get();
        address.setPerson(person);

        Location location = getLocation(addressDTO.getLocationId());
        address.setLocation(location);

        Province province = getProvince(addressDTO.getProvinceId());
        address.setProvince(province);

        addressRepository.save(address);
        apiResponse.setData(address);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editAddress(Long id, AddressDTO addressDTO) {
        validateAddress(addressDTO);
        APIResponse apiResponse = new APIResponse();

        Optional<Address> optionalAddress = findAddress(id);
        Optional<Person> optionalPerson = findPerson(addressDTO.getPersonId());

        Address address = optionalAddress.get();
        address.setStreet(addressDTO.getStreet());
        address.setSection(addressDTO.getSection());
        address.setNumber(addressDTO.getNumber());
        address.setFloor(addressDTO.getFloor());
        address.setApartment(addressDTO.getApartment());
        address.setZip(addressDTO.getZip());
        address.setComplete_address(getCompleteAddress(addressDTO));
        Person person = optionalPerson.get();
        address.setPerson(person);

        Location location = getLocation(addressDTO.getLocationId());
        address.setLocation(location);

        Province province = getProvince(addressDTO.getProvinceId());
        address.setProvince(province);

        addressRepository.save(address);
        apiResponse.setMessage(MessagesResponse.editSuccess);
        apiResponse.setData(address);
        apiResponse.setStatus(HttpStatus.OK.value());

        return apiResponse;
    }

    public APIResponse deleteAddress(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Address> optionalAddress = findAddress(id);

        Address existingAddress = optionalAddress.get();
        existingAddress.setDeletedAt(LocalDateTime.now());
        addressRepository.save(existingAddress);

        apiResponse.setData(existingAddress);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.deleteSuccess);
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

    public void createAddress(AddressDTO addressDTO){
        validateAddress(addressDTO);
        Optional<Person> optionalPerson = findPerson(addressDTO.getPersonId());
        Location location = getLocation(addressDTO.getLocationId());
        Province province = getProvince(addressDTO.getProvinceId());

        if (addressDTO.getId() != null) {
            Optional<Address> optionalAddress = addressRepository.findByIdAndDeletedAtIsNull(addressDTO.getId());
            if (optionalAddress.isPresent()) {
                Address existingAddress = optionalAddress.get();
                existingAddress.setPerson(optionalPerson.get());
                existingAddress.setLocation(location);
                existingAddress.setProvince(province);
                existingAddress.setStreet(addressDTO.getStreet());
                existingAddress.setSection(addressDTO.getSection());
                existingAddress.setNumber(addressDTO.getNumber());
                existingAddress.setFloor(addressDTO.getFloor());
                existingAddress.setApartment(addressDTO.getApartment());
                existingAddress.setZip(addressDTO.getZip());
                existingAddress.setComplete_address(getCompleteAddress(addressDTO));
                addressRepository.save(existingAddress);
                return;
            }
        }
        
        Address newAddress = new Address();
        newAddress.setPerson(optionalPerson.get());
        newAddress.setLocation(location);
        newAddress.setProvince(province);
        newAddress.setStreet(addressDTO.getStreet());
        newAddress.setSection(addressDTO.getSection());
        newAddress.setNumber(addressDTO.getNumber());
        newAddress.setFloor(addressDTO.getFloor());
        newAddress.setApartment(addressDTO.getApartment());
        newAddress.setZip(addressDTO.getZip());
        newAddress.setComplete_address(getCompleteAddress(addressDTO));
        addressRepository.save(newAddress);
    }
    public APIResponse getAddress(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Address> optionalAddress = findAddress(id);

        Address existingAddress = optionalAddress.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingAddress);
        return apiResponse;
    }

    public Optional<Address> findAddress(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()){
            throw new NotFoundException(MessagesResponse.addressNotFound);
        }
        return optionalAddress;
    }

    public Optional<Person> findPerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()){
            throw  new NotFoundException(MessagesResponse.personNotFund);
        }
        return optionalPerson;
    }

    public void validateAddress(AddressDTO addressDTO) {
        if (Stream.of(addressDTO)
                .map(AddressDTO::getPersonId)
                .anyMatch(personId -> personId == null)){
            throw new NotValidException(MessagesResponse.personCannotBeNull);
        }
    }

    public Location getLocation(Long id){
        Optional<Location> optionalLocation = locationRepository.findByIdAndDeletedAtIsNull(id);
        if(!optionalLocation.isPresent()){
            Location location = null;
            return location;
        } else {
            Location location = optionalLocation.get();
            return location;
        }
    }

    public Province getProvince(Long id){
        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalProvince.isPresent()){
            Province province = null;
            return province;
        } else {
            Province province = optionalProvince.get();
            return province;
        }
    }
}
