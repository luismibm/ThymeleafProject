package com.luismi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.context.Context;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");

        Properties properties = new Properties();
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream("src/main/resources/config.ini"));
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        String name = properties.getProperty("name");
        String description = properties.getProperty("description");

        Context context = new Context();

        ObjectMapper objectMapper = new ObjectMapper();
        PokemonData pokemonData = null;
        try {
            pokemonData = objectMapper.readValue(new File("src/main/resources/pokemon-generations.json"), PokemonData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        context.setVariable("generations", pokemonData.getPokemonGenerations());
        context.setVariable("name", name);
        context.setVariable("description", description);

        String htmlContent = templateEngine.process("templateGenerations", context);

        writeHtml(htmlContent, "src/main/resources/generated/index.html");

        for (Generation pokemonGeneration : pokemonData.getPokemonGenerations()) {
            Context detailsContext = new Context();
            detailsContext.setVariable("starters", pokemonGeneration.getStarters());
            detailsContext.setVariable("name", name);
            detailsContext.setVariable("description", description);
            String htmlDetails = templateEngine.process("templateStarters", detailsContext);
            String fileName = "src/main/resources/generated/details_" + pokemonGeneration.getGenerationNum() + ".html";
            writeHtml(htmlDetails, fileName);
        }

    }

    public static void writeHtml(String htmlContent, String fileName) {

        try {
            Files.createDirectories(Paths.get("src/main/resources/generated"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(htmlContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}