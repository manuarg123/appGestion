package com.example.api.speciality;

import com.example.api.common.APIResponse;
import com.example.api.common.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyService specialtyService;

    private Speciality speciality;
    private Speciality speciality2;

    private SpecialtyDTO specialtyDTO;
    private SpecialtyDTO specialtyDTO2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.speciality = new Speciality(1L, "specialty");
        this.speciality2 = new Speciality(2L, "specialty2");
    }

    @Test
    @DisplayName("GetSpecialty: If id exists should return APIResponse data key")
    void testGetSpecialty_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(specialtyRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(speciality));

        APIResponse result = specialtyService.getSpeciality(id);

        assertEquals(result.getData(), speciality);
        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetSpecialty: If specialty was soft deleted should thrown NotFounException")
    void testGetSpecialty_whenIdSpecialtyWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(specialtyRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialtyService.getSpeciality(id));

        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetSpecialty: If id is null should thrown NotFoundException")
    void testGetSpecialty_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(specialtyRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialtyService.getSpeciality(id));

        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }
}