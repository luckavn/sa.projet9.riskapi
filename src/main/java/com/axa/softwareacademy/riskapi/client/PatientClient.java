package com.axa.softwareacademy.riskapi.client;

import com.axa.softwareacademy.riskapi.model.Patient;
import com.axa.softwareacademy.riskapi.configuration.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "patientClient",
        url = "${patient.api.url}",
        configuration = ClientConfiguration.class
)
public interface PatientClient {

    @GetMapping("/patients/family-name")
    Patient getPatientByFamilyName(@RequestParam String familyName);

    @GetMapping("/patients/id")
    Patient getPatientById(@RequestParam Integer id);
}
