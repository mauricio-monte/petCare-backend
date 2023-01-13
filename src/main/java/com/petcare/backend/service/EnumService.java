package com.petcare.backend.service;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EnumService {

    public List<String> getSpecies() throws IOException {
        JSONArray jsonArray = readJsonArray("json/domesticAnimalsSpecies.json");
        return parseJsonArrayToListOfStrings(jsonArray);
    }

    public List<String> getVaccinesDescriptions() throws IOException {
        JSONArray jsonArray = readJsonArray("json/vaccinesDescriptions.json");
        return parseJsonArrayToListOfStrings(jsonArray);
    }

    private JSONArray readJsonArray(String resourcePath) throws IOException {
        Resource resource = new ClassPathResource(resourcePath);
        File file = resource.getFile();
        String content = new String(Files.readAllBytes(file.toPath()));
        return new JSONArray(content);
    }

    private List<String> parseJsonArrayToListOfStrings(JSONArray jsonArray){
        List<String> result = new ArrayList<>();

        for (int i = 0; i <jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }

        return result;
    }
}
