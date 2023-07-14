package com.example.api.province;

import com.example.api.common.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProvinceServiceTest {

    @Mock
    private ProvinceRepository provinceRepository;

    @InjectMocks
    private ProvinceService provinceService;

    private Province province;
    private Province province2;

    private List<Province> provinceList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.province = new Province(1L, "province");
        this.province2 = new Province(2L, "province2");
        this.provinceList = Arrays.asList(province, province2);
    }

    @Test
    @DisplayName("GetProvince: If id exists should return APIResponse data key")
    void testGetProvince_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(provinceRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(province));

        APIResponse result = provinceService.getProvince(id);

        assertEquals(result.getData(), province);
        verify(provinceRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetProvince: If province was soft deleted or id not exists should thrown NotFoundException")
    void testGetProvince_whenIdProvinceWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(provinceRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> provinceService.getProvince(id));

        verify(provinceRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetProvince: If id is null should thrown NotFoundException")
    void testGetProvince_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(provinceRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> provinceService.getProvince(id));

        verify(provinceRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetProvinces: Should return list of Specialities")
    void testGetSpecialties_ShouldReturnListOfSpecialties(){
        when(provinceRepository.findByDeletedAtIsNull()).thenReturn(provinceList);

        List<Province> result = provinceService.getProvinces();

        assertEquals(provinceList, result);

        verify(provinceRepository,times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("GetProvinces: If not Specialties exists should return empty list")
    void testGetSpecialties_whenNotSpecialtiesExists_ShouldReturnEmptyList(){
        when(provinceRepository.findByDeletedAtIsNull()).thenReturn(new ArrayList<>());

        List<Province> result = provinceService.getProvinces();

        assertEquals(0, result.size());

        verify(provinceRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("newProvince: If name alreadyExists should thrown DuplicatedRecordException")
    void testNewProvince_whenNameAlreadyExists_ShouldThrownDuplicatedRecordException(){
        String name = "province";

        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName(name);

        when(provinceRepository.findProvinceByName(name)).thenReturn(Optional.of(new Province()));

        assertThrows(DuplicateRecordException.class, () -> provinceService.newProvince(provinceDTO));

        verify(provinceRepository, times(1)).findProvinceByName(name);
    }

    @Test
    @DisplayName("newProvince: Valid ProvinceDTO should return APIResponse")
    void testNewProvince_whenProvinceDTOIsValid_ShouldReturnAPIResponse() {
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName("Valid Province");

        assertDoesNotThrow(() -> provinceService.newProvince(provinceDTO));

        verify(provinceRepository, times(1)).findProvinceByName(provinceDTO.getName());
    }

    @Test
    @DisplayName("newProvince: If name is null should throw NotValidException")
    void testNewProvince_whenNameIsNull_ShouldThrownNotValidException() {
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<ProvinceDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(provinceDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> provinceService.newProvince(provinceDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(provinceRepository, never()).findProvinceByName(anyString());
    }

    @Test
    @DisplayName("newProvince: If name is blank should throw NotValidException")
    void testNewProvince_whenNameIsBlank_ShouldThrownNotValidException() {
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<ProvinceDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(provinceDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> provinceService.newProvince(provinceDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(provinceRepository, never()).findProvinceByName(anyString());
    }

    @Test
    @DisplayName("newProvince: If name is much size should throw NotValidException")
    void testNewProvince_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<ProvinceDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(provinceDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> provinceService.newProvince(provinceDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(provinceRepository, never()).findProvinceByName(anyString());
    }

    @Test
    @DisplayName("EditProvince: Valid ProvinceDTO should return APIResponse")
    void testEditProvince_whenProvinceDTOIsValid_ShouldReturnAPIResponse() {
        Long id = 1L;
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName("Valid Province");

        Province existingProvince = new Province();
        existingProvince.setId(id);
        existingProvince.setName("Existing Province");

        when(provinceRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingProvince));
        when(provinceRepository.findProvinceByName(provinceDTO.getName())).thenReturn(Optional.empty());
        when(provinceRepository.save(existingProvince)).thenReturn(existingProvince);

        APIResponse result = provinceService.editProvince(id, provinceDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(provinceRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(provinceRepository, times(1)).findProvinceByName(provinceDTO.getName());
        verify(provinceRepository, times(1)).save(existingProvince);
    }

    @Test
    @DisplayName("EditProvince: If name is null should throw NotValidException")
    void testEditProvince_whenNameIsNull_ShouldThrownNotValidException() {
        Long id = 1L;
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<ProvinceDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(provinceDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        ProvinceDTO existingProvince = new ProvinceDTO();
        existingProvince.setName(null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> provinceService.editProvince(id,existingProvince), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(provinceRepository, never()).findProvinceByName(anyString());
    }

    @Test
    @DisplayName("EditProvince: If name is blank should throw NotValidException")
    void testEditProvince_whenNameIsBlank_ShouldThrownNotValidException() {
        Long id = 1L;
        ProvinceDTO existingProvince = new ProvinceDTO();
        existingProvince.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<ProvinceDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingProvince, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> provinceService.editProvince(id,existingProvince), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(provinceRepository, never()).findProvinceByName(anyString());
    }
    @Test
    @DisplayName("EditProvince: If name is much size should throw NotValidException")
    void testEditProvince_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        Long id = 1L;
        ProvinceDTO existingProvince = new ProvinceDTO();
        existingProvince.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<ProvinceDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingProvince, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> provinceService.editProvince(id,existingProvince), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(provinceRepository, never()).findProvinceByName(anyString());
    }

    @Test
    @DisplayName("EditProvince: If name Exists Should return DuplicatedRecordException")
    void testEditProvince_whenNameExists_ShouldThrownDuplicatedRecordException(){
        Long id = 1L;
        String newName = "Existing Province";
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName(newName);

        Province existingProvince = new Province();
        existingProvince.setId(id);
        existingProvince.setName("Existing Province");

        when(provinceRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingProvince));
        when(provinceRepository.findProvinceByName(newName)).thenReturn(Optional.of(existingProvince));

        assertThrows(DuplicateRecordException.class, () -> provinceService.editProvince(id, provinceDTO));

        verify(provinceRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(provinceRepository, times(1)).findProvinceByName(newName);
        verify(provinceRepository, never()).save(existingProvince);
    }

    @Test
    @DisplayName("EditProvince: If record not found should throw NotFoundException")
    void testEditProvince_whenRecordNotFound_ShouldThrowNotFoundException() {
        Long id = 1L;
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setName("Valid Province");

        when(provinceRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> provinceService.editProvince(id, provinceDTO));

        verify(provinceRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(provinceRepository, never()).findProvinceByName(anyString());
        verify(provinceRepository, never()).save(any());
    }
}