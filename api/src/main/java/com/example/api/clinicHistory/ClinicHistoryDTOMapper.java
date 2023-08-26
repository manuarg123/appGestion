package com.example.api.clinicHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ClinicHistoryDTOMapper implements Function<ClinicHistory, ClinicHistoryDTO> {

    @Override
    public ClinicHistoryDTO apply(ClinicHistory clinicHistory) {
        return new ClinicHistoryDTO(
                clinicHistory.getId()
        );
    }
}
