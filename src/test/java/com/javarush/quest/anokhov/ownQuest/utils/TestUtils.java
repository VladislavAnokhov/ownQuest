package com.javarush.quest.anokhov.ownQuest.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class TestUtils {
    public static void setupMockData(HttpServletRequest request) {
        // Загрузка данных из JSON файлов
        Map<String, String> correctAnswers = loadJsonData("correctAnswers.json");
        Map<String, String> incorrectAnswers = loadJsonData("incorrectAnswers.json");
        Map<String, String> otherText = loadJsonData("otherText.json");
        Map<String, String> actionChoices = loadJsonData("actionChoices.json");

        // Установка атрибутов запроса
        request.setAttribute("correctAnswers", correctAnswers);
        request.setAttribute("incorrectAnswers", incorrectAnswers);
        request.setAttribute("otherText", otherText);
        request.setAttribute("actionChoices", actionChoices);
    }

    private static Map<String, String> loadJsonData(String filename) {
        try (InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Файл не найден: " + filename);
            }
            String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении JSON файла: " + filename, e);
        }
    }
}
