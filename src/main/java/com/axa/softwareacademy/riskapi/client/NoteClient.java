package com.axa.softwareacademy.riskapi.client;

import com.axa.softwareacademy.riskapi.model.Note;
import com.axa.softwareacademy.riskapi.configuration.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        value = "noteClient",
        url = "${note.api.url}",
        configuration = ClientConfiguration.class
)
public interface NoteClient {

    @GetMapping("/notes/patientId")
    List<Note> getNoteByPatientId(@RequestParam String id);

}
