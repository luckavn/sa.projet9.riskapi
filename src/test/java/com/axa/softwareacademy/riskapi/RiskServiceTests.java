package com.axa.softwareacademy.riskapi;

import com.axa.softwareacademy.riskapi.client.NoteClient;
import com.axa.softwareacademy.riskapi.client.PatientClient;
import com.axa.softwareacademy.riskapi.enums.RiskType;
import com.axa.softwareacademy.riskapi.model.Note;
import com.axa.softwareacademy.riskapi.model.Patient;
import com.axa.softwareacademy.riskapi.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RiskServiceTests {
    private Patient patientReturned = new Patient(1, "Doe", "John", "1 boulevard rd", new Date(1990 - 02 - 10), "M", "01010101010", 31);
    private Patient patientReturned2 = new Patient(2, "Doe", "John", "1 boulevard rd", new Date(1990 - 02 - 10), "M", "01010101010", 28);
    private Patient patientReturned3 = new Patient(2, "Doe", "John", "1 boulevard rd", new Date(1990 - 02 - 10), "F", "01010101010", 28);
    private static RiskService riskService;

    @Mock
    PatientClient patientClient;

    @Mock
    NoteClient noteClient;

    @BeforeEach
    void setUp() {
        try {
            riskService = new RiskService(patientClient, noteClient);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test mock objects");
        }
    }

    @Test
    public void calculateRiskWithPatientIdTest() {
        String patientId = "1";
        Note noteReturned = new Note("1", "Microalbumine", "1");
        Note noteReturned2 = new Note("2", "Taille", "1");
        List<Note> notesReturned = new ArrayList<>();
        notesReturned.add(noteReturned);
        notesReturned.add(noteReturned2);

        when(patientClient.getPatientById(anyInt())).thenReturn(patientReturned);
        when(noteClient.getNoteByPatientId(anyString())).thenReturn(notesReturned);

        RiskType riskTypeCalculated = riskService.calculateRiskWithPatientId(patientId);
        assertEquals(riskTypeCalculated.toString(), "BORDERLINE");
    }

    @Test
    public void calculateRiskWithFamilyName() {
        String familyName = "Doe";
        Note noteReturned = new Note("1", "Microalbumine", "1");
        Note noteReturned2 = new Note("2", "Taille", "1");
        List<Note> notesReturned = new ArrayList<>();
        notesReturned.add(noteReturned);
        notesReturned.add(noteReturned2);

        when(patientClient.getPatientByFamilyName(anyString())).thenReturn(patientReturned);
        when(noteClient.getNoteByPatientId(anyString())).thenReturn(notesReturned);

        RiskType riskTypeCalculated = riskService.calculateRiskWithFamilyName(familyName);
        assertEquals(riskTypeCalculated.toString(), "BORDERLINE");
    }

    @Test
    public void calculateTypeRiskTest() {
        RiskType riskTypeCalculated0 = riskService.calculateRiskType(patientReturned, 0);
        assertEquals(riskTypeCalculated0.toString(), "NONE");

        RiskType riskTypeCalculated = riskService.calculateRiskType(patientReturned2, 3);
        assertEquals(riskTypeCalculated.toString(), "IN_DANGER");

        RiskType riskTypeCalculated2 = riskService.calculateRiskType(patientReturned3, 4);
        assertEquals(riskTypeCalculated2.toString(), "IN_DANGER");

        RiskType riskTypeCalculated3 = riskService.calculateRiskType(patientReturned, 6);
        assertEquals(riskTypeCalculated3.toString(), "IN_DANGER");

        RiskType riskTypeCalculated4 = riskService.calculateRiskType(patientReturned2, 5);
        assertEquals(riskTypeCalculated4.toString(), "EARLY_ONSET");

        RiskType riskTypeCalculated5 = riskService.calculateRiskType(patientReturned3, 7);
        assertEquals(riskTypeCalculated5.toString(), "EARLY_ONSET");

        RiskType riskTypeCalculated6 = riskService.calculateRiskType(patientReturned, 8);
        assertEquals(riskTypeCalculated6.toString(), "EARLY_ONSET");
    }
}
