package com.axa.softwareacademy.riskapi.service;

import com.axa.softwareacademy.riskapi.client.NoteClient;
import com.axa.softwareacademy.riskapi.client.PatientClient;
import com.axa.softwareacademy.riskapi.enums.RiskType;
import com.axa.softwareacademy.riskapi.model.Note;
import com.axa.softwareacademy.riskapi.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskService {
    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private static final List<String> termsList = Arrays.asList(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Anormal",
            "Cholestérol",
            "Vertige",
            "Rechute",
            "Réaction",
            "Anticorps.");

    public RiskType calculateRiskWithPatientId(String id) {
        Patient patient = patientClient.getPatientById(Integer.valueOf(id));
        int patientAge = calculateAge(patient.getDob());
        patient.setAge(patientAge);
        List<Note> noteList = noteClient.getNoteByPatientId(id);
        List<Note> matchedList = noteList.stream().filter(x -> termsList.contains(x.getNoteDetail())).collect(Collectors.toList());
        return calculateRiskType(patient, matchedList.size());
    }

    public RiskType calculateRiskWithFamilyName(String familyName) {
        Patient patient = patientClient.getPatientByFamilyName(familyName);
        List<Note> noteList = noteClient.getNoteByPatientId(patient.getId().toString());
        List<Note> matchedList = noteList.stream().filter(x -> termsList.contains(x.getNoteDetail())).collect(Collectors.toList());
        return calculateRiskType(patient, matchedList.size());
    }

    public RiskType calculateRiskType(Patient patient, Integer noteListSize) {
        if (noteListSize.equals(0)) {
            return RiskType.NONE;
        }
        if (patient.getAge() > 30 && noteListSize.equals(2)) {
            return RiskType.BORDERLINE;
        }
        if (patient.getSex().equals("M") && patient.getAge() < 30 && noteListSize.equals(3)) {
            return RiskType.IN_DANGER;
        }
        if (patient.getSex().equals("F") && patient.getAge() < 30 && noteListSize.equals(4)) {
            return RiskType.IN_DANGER;
        }
        if (patient.getAge() > 30 && noteListSize.equals(6)) {
            return RiskType.IN_DANGER;
        }
        if (patient.getSex().equals("M") && patient.getAge() < 30 && noteListSize.equals(5)) {
            return RiskType.EARLY_ONSET;
        }
        if (patient.getSex().equals("F") && patient.getAge() < 30 && noteListSize.equals(7)) {
            return RiskType.EARLY_ONSET;
        }
        if (patient.getAge() > 30 && noteListSize.equals(8)) {
            return RiskType.EARLY_ONSET;
        }
        return RiskType.UNDEFINED;
    }

    public int calculateAge(Date date) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(date));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        return (d2 - d1) / 10000;
    }
}
