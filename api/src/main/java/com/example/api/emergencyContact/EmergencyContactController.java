package com.example.api.emergencyContact;

import com.example.api.common.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/emergencyContacts")
@RequiredArgsConstructor
public class EmergencyContactController {
    private final EmergencyContactService emergencyContactService;

    @GetMapping(path = "/show/{emergencyContactId}")
    public APIResponse getEmergencyContact(@PathVariable("emergencyContactId") Long id) { return this.emergencyContactService.getEmergencyContact(id); }
}
