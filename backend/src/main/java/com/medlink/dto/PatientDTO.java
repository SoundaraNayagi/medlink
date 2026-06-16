package com.medlink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private UUID id;
    private String patientUniqueId;
    private String aadhaarNumber;
    private String bloodGroup;
    private String gender;
    private String dateOfBirth;
    private String emergencyContact;
    private String emergencyContactName;
    private String address;
    private UserDTO user;
}