package com.amurtigerstudio.boot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Weather {

    public static String getWeat(String message, Model model) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=a2bc9ef668ab43548acdaad232ac003d");
        Scanner scan = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scan.hasNext()) {
            result.append(scan.nextLine());
        }

        String res = result.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(res);
        model.setName(String.valueOf(rootNode.path("name").asText()));
        model.setTemp(Double.valueOf(String.valueOf(rootNode.path("main").path("temp"))));
        model.setPressure(Double.valueOf(String.valueOf(rootNode.path("main").path("pressure"))));
        JsonNode node = rootNode.path("weather").get(0);
        model.setMain(node.path("main").asText());
        model.setIcon(node.path("icon").asText());

        return "Город = " + model.getName() + "\n" +
                "Температура = " + model.getTemp() + "\n" +
                "Давление = " + model.getPressure() + "\n" +
                "Погода = " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";

    }
}
