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

        generateRSS(pokemonData, "src/main/resources/generated/rss.xml", name, description);

    }

    public static void writeHtml(String htmlContent, String fileName) {

        try {
            Files.createDirectories(Paths.get("src/main/resources/generated"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(htmlContent);
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR: writeHtml");
        }

    }

    public static void generateRSS(PokemonData pokemonData, String path, String name, String description) {

        StringBuilder rssContent = new StringBuilder();
        rssContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        rssContent.append("<rss version=\"2.0\">\n");
        rssContent.append("<channel>\n");
        rssContent.append("<title>").append(name).append("</title>\n");
        rssContent.append("<link>src/main/resources/generated/index.html</link>\n");
        rssContent.append("<description>").append(description).append("</description>\n");

        for (Generation generation : pokemonData.getPokemonGenerations()) {
            rssContent.append("<item>\n");
            rssContent.append("<title>").append(generation.getGenerationName()).append("</title>\n");
            rssContent.append("<link>src/main/resources/generated/details_").append(generation.getGenerationNum()).append(".html</link>\n");
            rssContent.append("<description>Starters: ");
            for (Starter starter : generation.getStarters()) {
                rssContent.append(starter.getStarterName()).append(" (").append(starter.getStarterType()).append("), ");
            }
            rssContent.setLength(rssContent.length() - 2); // Remove last comma and space
            rssContent.append("</description>\n");
            rssContent.append("</item>\n");
        }

        rssContent.append("</channel>\n");
        rssContent.append("</rss>");

        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(rssContent.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: generateRSS");
        }

    }

}