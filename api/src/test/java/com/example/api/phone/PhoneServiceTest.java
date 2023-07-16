package com.example.api.phone;

import com.example.api.common.*;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.phoneType.PhoneType;
import com.example.api.phoneType.PhoneTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private PhoneTypeRepository phoneTypeRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PhoneService phoneService;

    private PhoneDTO phoneDTO;
    private PhoneType phoneType;
    private Person person;
    private Phone phone;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.phoneDTO = new PhoneDTO();
        this.phoneDTO.setValue("123456789");
        this.phoneDTO.setPersonId(1L);
        this.phoneDTO.setTypeId(1L);

        this.phoneType = new PhoneType();
        this.phoneType.setId(1L);
        this.phoneType.setName("Mobile");

        this.person = new Person();
        this.person.setId(1L);
        this.person.setFullName("John Doe");

        this.phone = new Phone();
        this.phone.setId(1L);
        this.phone.setNumber("123456789");
        this.phone.setPerson(person);
        this.phone.setType(phoneType);
    }

    @Test
    @DisplayName("NewPhone: Valid PhoneDTO should return APIResponse")
    void testNewPhone_whenPhoneDTOIsValid_shouldReturnAPIResponse() {
        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(phoneDTO.getTypeId())).thenReturn(Optional.of(phoneType));
        when(personRepository.findById(phoneDTO.getPersonId())).thenReturn(Optional.of(person));
        when(phoneRepository.save(any(Phone.class))).thenReturn(phone);

        APIResponse result = phoneService.newPhone(phoneDTO);

        assertEquals(HttpStatus.CREATED.value(), result.getStatus());
        assertEquals(MessagesResponse.addSuccess, result.getMessage());
        assertEquals(phone, result.getData());

        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(phoneDTO.getTypeId());
        verify(personRepository, times(1)).findById(phoneDTO.getPersonId());
        verify(phoneRepository, times(1)).save(any(Phone.class));
    }

    @Test
    @DisplayName("NewPhone: If value is null should throw NotValidException")
    void testNewPhone_whenValueIsNull_shouldThrowNotValidException() {
        phoneDTO.setValue(null);

        assertThrows(NotValidException.class, () -> phoneService.newPhone(phoneDTO));

        verify(phoneTypeRepository, never()).findByIdAndDeletedAtIsNull(anyLong());
        verify(personRepository, never()).findById(anyLong());
        verify(phoneRepository, never()).save(any(Phone.class));
    }

    @Test
    @DisplayName("NewPhone: If value is blank should throw NotValidException")
    void testNewPhone_whenValueIsBlank_shouldThrowNotValidException() {
        phoneDTO.setValue("");

        assertThrows(NotValidException.class, () -> phoneService.newPhone(phoneDTO));

        verify(phoneTypeRepository, never()).findByIdAndDeletedAtIsNull(anyLong());
        verify(personRepository, never()).findById(anyLong());
        verify(phoneRepository, never()).save(any(Phone.class));
    }

    @Test
    @DisplayName("NewPhone: If value is too long should throw NotValidException")
    void testNewPhone_whenValueIsTooLong_shouldThrowNotValidException() {
        phoneDTO.setValue("12345678901234567890123456789012345678901234567890");

        assertThrows(NotValidException.class, () -> phoneService.newPhone(phoneDTO));

        verify(phoneTypeRepository, never()).findByIdAndDeletedAtIsNull(anyLong());
        verify(personRepository, never()).findById(anyLong());
        verify(phoneRepository, never()).save(any(Phone.class));
    }

    @Test
    @DisplayName("NewPhone: If personId is null should throw NotValidException")
    void testNewPhone_whenPersonIdIsNull_shouldThrowNotValidException() {
        phoneDTO.setPersonId(null);

        assertThrows(NotValidException.class, () -> phoneService.newPhone(phoneDTO));

        verify(phoneTypeRepository, never()).findByIdAndDeletedAtIsNull(anyLong());
        verify(personRepository, never()).findById(anyLong());
        verify(phoneRepository, never()).save(any(Phone.class));
    }

    @Test
    @DisplayName("NewPhone: If typeId is null should throw NotValidException")
    void testNewPhone_whenTypeIdIsNull_shouldThrowNotValidException() {
        phoneDTO.setTypeId(null);

        assertThrows(NotValidException.class, () -> phoneService.newPhone(phoneDTO));

        verify(phoneTypeRepository, never()).findByIdAndDeletedAtIsNull(anyLong());
        verify(personRepository, never()).findById(anyLong());
        verify(phoneRepository, never()).save(any(Phone.class));
    }

    @Test
    @DisplayName("EditPhone: Valid PhoneDTO should return APIResponse")
    void testEditPhone_whenPhoneDTOIsValid_shouldReturnAPIResponse() {
        Long id = 1L;
        PhoneDTO updatedPhoneDTO = new PhoneDTO();
        updatedPhoneDTO.setValue("987654321");
        updatedPhoneDTO.setPersonId(2L);
        updatedPhoneDTO.setTypeId(2L);

        Optional<Phone> optionalPhone = Optional.of(phone);
        when(phoneRepository.findById(id)).thenReturn(optionalPhone);
        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(updatedPhoneDTO.getTypeId())).thenReturn(Optional.of(phoneType));
        when(personRepository.findById(updatedPhoneDTO.getPersonId())).thenReturn(Optional.of(person));
        when(phoneRepository.save(any(Phone.class))).thenReturn(phone);

        APIResponse result = phoneService.editPhone(id, updatedPhoneDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertEquals(phone, result.getData());

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(updatedPhoneDTO.getTypeId());
        verify(personRepository, times(1)).findById(updatedPhoneDTO.getPersonId());
        verify(phoneRepository, times(1)).save(any(Phone.class));
    }

    @Test
    @DisplayName("EditPhone: If phoneId not found should throw NotFoundException")
    void testEditPhone_whenPhoneIdNotFound_shouldThrowNotFoundException() {
        Long id = 1L;
        PhoneDTO updatedPhoneDTO = new PhoneDTO();
        updatedPhoneDTO.setValue("987654321");
        updatedPhoneDTO.setPersonId(2L);
        updatedPhoneDTO.setTypeId(2L);

        when(phoneRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> phoneService.editPhone(id, updatedPhoneDTO));

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneTypeRepository, never()).findByIdAndDeletedAtIsNull(anyLong());
        verify(personRepository, never()).findById(anyLong());
        verify(phoneRepository, never()).save(any(Phone.class));
    }

    @Test
    @DisplayName("DeletePhone: Valid phoneId should return APIResponse")
    void testDeletePhone_whenPhoneIdIsValid_shouldReturnAPIResponse() {
        Long id = 1L;
        Optional<Phone> optionalPhone = Optional.of(phone);
        when(phoneRepository.findById(id)).thenReturn(optionalPhone);
        when(phoneRepository.save(any(Phone.class))).thenReturn(phone);

        APIResponse result = phoneService.deletePhone(id);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.deleteSuccess, result.getMessage());
        assertEquals(phone, result.getData());

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneRepository, times(1)).save(any(Phone.class));
    }

    @Test
    @DisplayName("DeletePhone: If phoneId not found should throw NotFoundException")
    void testDeletePhone_whenPhoneIdNotFound_shouldThrowNotFoundException() {
        Long id = 1L;

        when(phoneRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> phoneService.deletePhone(id));

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneRepository, never()).save(any(Phone.class));
    }
}