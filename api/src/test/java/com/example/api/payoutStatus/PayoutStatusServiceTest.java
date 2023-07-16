package com.example.api.payoutStatus;

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

class PayoutStatusServiceTest {

    @Mock
    private PayoutStatusRepository payoutStatusRepository;

    @InjectMocks
    private PayoutStatusService payoutStatusService;

    private PayoutStatus payoutStatus;
    private PayoutStatus payoutStatus2;

    private List<PayoutStatus> payoutStatusList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.payoutStatus = new PayoutStatus(1L, "payoutStatus");
        this.payoutStatus2 = new PayoutStatus(2L, "payoutStatus2");
        this.payoutStatusList = Arrays.asList(payoutStatus, payoutStatus2);
    }

    @Test
    @DisplayName("GetPayoutStatus: If id exists should return APIResponse data key")
    void testGetPayoutStatus_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(payoutStatusRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(payoutStatus));

        APIResponse result = payoutStatusService.getPayoutStatus(id);

        assertEquals(result.getData(), payoutStatus);
        verify(payoutStatusRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetPayoutStatus: If payoutStatus was soft deleted or id not exists should thrown NotFoundException")
    void testGetPayoutStatus_whenIdPayoutStatusWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(payoutStatusRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> payoutStatusService.getPayoutStatus(id));

        verify(payoutStatusRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetPayoutStatus: If id is null should thrown NotFoundException")
    void testGetPayoutStatus_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(payoutStatusRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> payoutStatusService.getPayoutStatus(id));

        verify(payoutStatusRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetPayoutStatuses: Should return list of PayoutStatuses")
    void testGetSpecialties_ShouldReturnListOfSpecialties(){
        when(payoutStatusRepository.findByDeletedAtIsNull()).thenReturn(payoutStatusList);

        List<PayoutStatus> result = payoutStatusService.getPayoutStatuses();

        assertEquals(payoutStatusList, result);

        verify(payoutStatusRepository,times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("GetPayoutStatuses: If not Specialties exists should return empty list")
    void testGetSpecialties_whenNotSpecialtiesExists_ShouldReturnEmptyList(){
        when(payoutStatusRepository.findByDeletedAtIsNull()).thenReturn(new ArrayList<>());

        List<PayoutStatus> result = payoutStatusService.getPayoutStatuses();

        assertEquals(0, result.size());

        verify(payoutStatusRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("newPayoutStatus: If name alreadyExists should thrown DuplicatedRecordException")
    void testNewPayoutStatus_whenNameAlreadyExists_ShouldThrownDuplicatedRecordException(){
        String name = "payoutStatus";

        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName(name);

        when(payoutStatusRepository.findPayoutStatusByName(name)).thenReturn(Optional.of(new PayoutStatus()));

        assertThrows(DuplicateRecordException.class, () -> payoutStatusService.newPayoutStatus(payoutStatusDTO));

        verify(payoutStatusRepository, times(1)).findPayoutStatusByName(name);
    }

    @Test
    @DisplayName("newPayoutStatus: Valid PayoutStatusDTO should return APIResponse")
    void testNewPayoutStatus_whenPayoutStatusDTOIsValid_ShouldReturnAPIResponse() {
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName("Valid PayoutStatus");

        assertDoesNotThrow(() -> payoutStatusService.newPayoutStatus(payoutStatusDTO));

        verify(payoutStatusRepository, times(1)).findPayoutStatusByName(payoutStatusDTO.getName());
    }

    @Test
    @DisplayName("newPayoutStatus: If name is null should throw NotValidException")
    void testNewPayoutStatus_whenNameIsNull_ShouldThrownNotValidException() {
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PayoutStatusDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(payoutStatusDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> payoutStatusService.newPayoutStatus(payoutStatusDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(payoutStatusRepository, never()).findPayoutStatusByName(anyString());
    }

    @Test
    @DisplayName("newPayoutStatus: If name is blank should throw NotValidException")
    void testNewPayoutStatus_whenNameIsBlank_ShouldThrownNotValidException() {
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PayoutStatusDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(payoutStatusDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> payoutStatusService.newPayoutStatus(payoutStatusDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(payoutStatusRepository, never()).findPayoutStatusByName(anyString());
    }

    @Test
    @DisplayName("newPayoutStatus: If name is much size should throw NotValidException")
    void testNewPayoutStatus_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PayoutStatusDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(payoutStatusDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> payoutStatusService.newPayoutStatus(payoutStatusDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(payoutStatusRepository, never()).findPayoutStatusByName(anyString());
    }

    @Test
    @DisplayName("EditPayoutStatus: Valid PayoutStatusDTO should return APIResponse")
    void testEditPayoutStatus_whenPayoutStatusDTOIsValid_ShouldReturnAPIResponse() {
        Long id = 1L;
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName("Valid PayoutStatus");

        PayoutStatus existingPayoutStatus = new PayoutStatus();
        existingPayoutStatus.setId(id);
        existingPayoutStatus.setName("Existing PayoutStatus");

        when(payoutStatusRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingPayoutStatus));
        when(payoutStatusRepository.findPayoutStatusByName(payoutStatusDTO.getName())).thenReturn(Optional.empty());
        when(payoutStatusRepository.save(existingPayoutStatus)).thenReturn(existingPayoutStatus);

        APIResponse result = payoutStatusService.editPayoutStatus(id, payoutStatusDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(payoutStatusRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(payoutStatusRepository, times(1)).findPayoutStatusByName(payoutStatusDTO.getName());
        verify(payoutStatusRepository, times(1)).save(existingPayoutStatus);
    }

    @Test
    @DisplayName("EditPayoutStatus: If name is null should throw NotValidException")
    void testEditPayoutStatus_whenNameIsNull_ShouldThrownNotValidException() {
        Long id = 1L;
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PayoutStatusDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(payoutStatusDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        PayoutStatusDTO existingPayoutStatus = new PayoutStatusDTO();
        existingPayoutStatus.setName(null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> payoutStatusService.editPayoutStatus(id,existingPayoutStatus), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(payoutStatusRepository, never()).findPayoutStatusByName(anyString());
    }

    @Test
    @DisplayName("EditPayoutStatus: If name is blank should throw NotValidException")
    void testEditPayoutStatus_whenNameIsBlank_ShouldThrownNotValidException() {
        Long id = 1L;
        PayoutStatusDTO existingPayoutStatus = new PayoutStatusDTO();
        existingPayoutStatus.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PayoutStatusDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingPayoutStatus, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> payoutStatusService.editPayoutStatus(id,existingPayoutStatus), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(payoutStatusRepository, never()).findPayoutStatusByName(anyString());
    }
    @Test
    @DisplayName("EditPayoutStatus: If name is much size should throw NotValidException")
    void testEditPayoutStatus_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        Long id = 1L;
        PayoutStatusDTO existingPayoutStatus = new PayoutStatusDTO();
        existingPayoutStatus.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PayoutStatusDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingPayoutStatus, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> payoutStatusService.editPayoutStatus(id,existingPayoutStatus), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(payoutStatusRepository, never()).findPayoutStatusByName(anyString());
    }

    @Test
    @DisplayName("EditPayoutStatus: If name Exists Should return DuplicatedRecordException")
    void testEditPayoutStatus_whenNameExists_ShouldThrownDuplicatedRecordException(){
        Long id = 1L;
        String newName = "Existing PayoutStatus";
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName(newName);

        PayoutStatus existingPayoutStatus = new PayoutStatus();
        existingPayoutStatus.setId(id);
        existingPayoutStatus.setName("Existing PayoutStatus");

        when(payoutStatusRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingPayoutStatus));
        when(payoutStatusRepository.findPayoutStatusByName(newName)).thenReturn(Optional.of(existingPayoutStatus));

        assertThrows(DuplicateRecordException.class, () -> payoutStatusService.editPayoutStatus(id, payoutStatusDTO));

        verify(payoutStatusRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(payoutStatusRepository, times(1)).findPayoutStatusByName(newName);
        verify(payoutStatusRepository, never()).save(existingPayoutStatus);
    }

    @Test
    @DisplayName("EditPayoutStatus: If record not found should throw NotFoundException")
    void testEditPayoutStatus_whenRecordNotFound_ShouldThrowNotFoundException() {
        Long id = 1L;
        PayoutStatusDTO payoutStatusDTO = new PayoutStatusDTO();
        payoutStatusDTO.setName("Valid PayoutStatus");

        when(payoutStatusRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> payoutStatusService.editPayoutStatus(id, payoutStatusDTO));

        verify(payoutStatusRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(payoutStatusRepository, never()).findPayoutStatusByName(anyString());
        verify(payoutStatusRepository, never()).save(any());
    }
}