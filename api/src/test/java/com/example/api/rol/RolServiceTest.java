package com.example.api.rol;

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

class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rol;
    private Rol rol2;

    private List<Rol> rolList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.rol = new Rol(1L, "rol");
        this.rol2 = new Rol(2L, "rol2");
        this.rolList = Arrays.asList(rol, rol2);
    }

    @Test
    @DisplayName("GetRol: If id exists should return APIResponse data key")
    void testGetRol_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(rolRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(rol));

        APIResponse result = rolService.getRol(id);

        assertEquals(result.getData(), rol);
        verify(rolRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetRol: If rol was soft deleted or id not exists should thrown NotFoundException")
    void testGetRol_whenIdRolWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(rolRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> rolService.getRol(id));

        verify(rolRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetRol: If id is null should thrown NotFoundException")
    void testGetRol_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(rolRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> rolService.getRol(id));

        verify(rolRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetRoles: Should return list of Specialities")
    void testGetSpecialties_ShouldReturnListOfSpecialties(){
        when(rolRepository.findByDeletedAtIsNull()).thenReturn(rolList);

        List<Rol> result = rolService.getRoles();

        assertEquals(rolList, result);

        verify(rolRepository,times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("GetRoles: If not Specialties exists should return empty list")
    void testGetSpecialties_whenNotSpecialtiesExists_ShouldReturnEmptyList(){
        when(rolRepository.findByDeletedAtIsNull()).thenReturn(new ArrayList<>());

        List<Rol> result = rolService.getRoles();

        assertEquals(0, result.size());

        verify(rolRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("newRol: If name alreadyExists should thrown DuplicatedRecordException")
    void testNewRol_whenNameAlreadyExists_ShouldThrownDuplicatedRecordException(){
        String name = "rol";

        RolDTO rolDTO = new RolDTO();
        rolDTO.setName(name);

        when(rolRepository.findRolByName(name)).thenReturn(Optional.of(new Rol()));

        assertThrows(DuplicateRecordException.class, () -> rolService.newRol(rolDTO));

        verify(rolRepository, times(1)).findRolByName(name);
    }

    @Test
    @DisplayName("newRol: Valid RolDTO should return APIResponse")
    void testNewRol_whenRolDTOIsValid_ShouldReturnAPIResponse() {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName("Valid Rol");

        assertDoesNotThrow(() -> rolService.newRol(rolDTO));

        verify(rolRepository, times(1)).findRolByName(rolDTO.getName());
    }

    @Test
    @DisplayName("newRol: If name is null should throw NotValidException")
    void testNewRol_whenNameIsNull_ShouldThrownNotValidException() {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<RolDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(rolDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> rolService.newRol(rolDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(rolRepository, never()).findRolByName(anyString());
    }

    @Test
    @DisplayName("newRol: If name is blank should throw NotValidException")
    void testNewRol_whenNameIsBlank_ShouldThrownNotValidException() {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<RolDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(rolDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> rolService.newRol(rolDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(rolRepository, never()).findRolByName(anyString());
    }

    @Test
    @DisplayName("newRol: If name is much size should throw NotValidException")
    void testNewRol_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<RolDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(rolDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> rolService.newRol(rolDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(rolRepository, never()).findRolByName(anyString());
    }

    @Test
    @DisplayName("EditRol: Valid RolDTO should return APIResponse")
    void testEditRol_whenRolDTOIsValid_ShouldReturnAPIResponse() {
        Long id = 1L;
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName("Valid Rol");

        Rol existingRol = new Rol();
        existingRol.setId(id);
        existingRol.setName("Existing Rol");

        when(rolRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingRol));
        when(rolRepository.findRolByName(rolDTO.getName())).thenReturn(Optional.empty());
        when(rolRepository.save(existingRol)).thenReturn(existingRol);

        APIResponse result = rolService.editRol(id, rolDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(rolRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(rolRepository, times(1)).findRolByName(rolDTO.getName());
        verify(rolRepository, times(1)).save(existingRol);
    }

    @Test
    @DisplayName("EditRol: If name is null should throw NotValidException")
    void testEditRol_whenNameIsNull_ShouldThrownNotValidException() {
        Long id = 1L;
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<RolDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(rolDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        RolDTO existingRol = new RolDTO();
        existingRol.setName(null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> rolService.editRol(id,existingRol), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(rolRepository, never()).findRolByName(anyString());
    }

    @Test
    @DisplayName("EditRol: If name is blank should throw NotValidException")
    void testEditRol_whenNameIsBlank_ShouldThrownNotValidException() {
        Long id = 1L;
        RolDTO existingRol = new RolDTO();
        existingRol.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<RolDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingRol, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> rolService.editRol(id,existingRol), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(rolRepository, never()).findRolByName(anyString());
    }
    @Test
    @DisplayName("EditRol: If name is much size should throw NotValidException")
    void testEditRol_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        Long id = 1L;
        RolDTO existingRol = new RolDTO();
        existingRol.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<RolDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingRol, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> rolService.editRol(id,existingRol), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(rolRepository, never()).findRolByName(anyString());
    }

    @Test
    @DisplayName("EditRol: If name Exists Should return DuplicatedRecordException")
    void testEditRol_whenNameExists_ShouldThrownDuplicatedRecordException(){
        Long id = 1L;
        String newName = "Existing Rol";
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName(newName);

        Rol existingRol = new Rol();
        existingRol.setId(id);
        existingRol.setName("Existing Rol");

        when(rolRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingRol));
        when(rolRepository.findRolByName(newName)).thenReturn(Optional.of(existingRol));

        assertThrows(DuplicateRecordException.class, () -> rolService.editRol(id, rolDTO));

        verify(rolRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(rolRepository, times(1)).findRolByName(newName);
        verify(rolRepository, never()).save(existingRol);
    }

    @Test
    @DisplayName("EditRol: If record not found should throw NotFoundException")
    void testEditRol_whenRecordNotFound_ShouldThrowNotFoundException() {
        Long id = 1L;
        RolDTO rolDTO = new RolDTO();
        rolDTO.setName("Valid Rol");

        when(rolRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> rolService.editRol(id, rolDTO));

        verify(rolRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(rolRepository, never()).findRolByName(anyString());
        verify(rolRepository, never()).save(any());
    }
}