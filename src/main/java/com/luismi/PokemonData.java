package com.luismi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonData {

    @JsonProperty("pokemon-generations")
    private List<Generation> pokemonGenerations;

    public PokemonData() {}

    public PokemonData(List<Generation> pokemonGenerationsenerations) {
        this.pokemonGenerations = pokemonGenerations;
    }

    public List<Generation> getPokemonGenerations() {
        return pokemonGenerations;
    }

    public void setPokemonGenerations(List<Generation> pokemonGenerations) {
        this.pokemonGenerations = pokemonGenerations;
    }

}

class Generation {

    @JsonProperty("generation-num")
    private int generationNum;

    @JsonProperty("generation-name")
    private String generationName;

    @JsonProperty("generation-map")
    private String generationMap;

    @JsonProperty("starters")
    private List<Starter> starters;

    public Generation() {
    }

    public Generation(int generationNum, String generationName, String generationMap, List<Starter> starters) {
        this.generationNum = generationNum;
        this.generationName = generationName;
        this.generationMap = generationMap;
        this.starters = starters;
    }

    public int getGenerationNum() {
        return generationNum;
    }

    public void setGenerationNum(int generationNum) {
        this.generationNum = generationNum;
    }

    public String getGenerationName() {
        return generationName;
    }

    public void setGenerationName(String generationName) {
        this.generationName = generationName;
    }

    public String getGenerationMap() {
        return generationMap;
    }

    public void setGenerationMap(String generationMap) {
        this.generationMap = generationMap;
    }

    public List<Starter> getStarters() {
        return starters;
    }

    public void setStarters(List<Starter> starters) {
        this.starters = starters;
    }

}

class Starter {

    @JsonProperty("starter-num")
    private int starterNum;

    @JsonProperty("starter-name")
    private String starterName;

    @JsonProperty("starter-type")
    private String starterType;

    public Starter() {
    }

    public Starter(int starterNum, String starterName, String starterType) {
        this.starterNum = starterNum;
        this.starterName = starterName;
        this.starterType = starterType;
    }

    public int getStarterNum() {
        return starterNum;
    }

    public void setStarterNum(int starterNum) {
        this.starterNum = starterNum;
    }

    public String getStarterName() {
        return starterName;
    }

    public void setStarterName(String starterName) {
        this.starterName = starterName;
    }

    public String getStarterType() {
        return starterType;
    }

    public void setStarterType(String starterType) {
        this.starterType = starterType;
    }

}