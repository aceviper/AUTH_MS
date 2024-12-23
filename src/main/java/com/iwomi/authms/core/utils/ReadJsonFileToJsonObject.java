package com.iwomi.authms.core.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJsonFileToJsonObject {
    public JSONObject read() {
        String file = "src/main/resources/openapi/response.json";
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException ex) {

        }
        return new JSONObject(content);
    }
}
