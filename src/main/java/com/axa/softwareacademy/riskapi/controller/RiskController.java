package com.axa.softwareacademy.riskapi.controller;

import com.axa.softwareacademy.riskapi.enums.RiskType;
import com.axa.softwareacademy.riskapi.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk")
@RequiredArgsConstructor
@CrossOrigin
public class RiskController {

    private final RiskService riskService;

    /**
     * This endpoint is aimed to calculate a risk of a patient
     * @param id is the unique id number of the patient
     * @return a risk
     */
    @GetMapping("patientId")
    public RiskType calculateRiskWithPatientId(@RequestParam String id) {
        return riskService.calculateRiskWithPatientId(id);
    }

    /**
     * This endpoint is aimed to calculate a risk of a patient
     * @param familyName is the value of last name of a patient
     * @return a risk
     */
    @GetMapping("patientFamilyName")
    public RiskType calculateRiskWithFamilyName(@RequestParam String familyName) {
        return riskService.calculateRiskWithFamilyName(familyName);
    }

}
