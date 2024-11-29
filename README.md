# Pokemon Generations & Starters

> Luis Miguel Benítez, 2º DAM

## Índice

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Ficheros de entrada](#ficheros-de-entrada)
- [Librerías, clases y dependencias utilizadas](#librerías-clases-y-dependencias-utilizadas)
- [Plantillas Thymeleaf](#plantillas-thymeleaf)
- [Ficheros de salida](#ficheros-de-salida)
- [Problemas resueltos y no resueltos](#problemas-resueltos-y-no-resueltos)
- [Recursos y blibliografía](#recursos-y-bibliografía)

## Descripción del proyecto

Este proyecto genera una página web que muestra las generaciones de Pokémon y sus respectivos Pokémon iniciales.
Utiliza archivos JSON para almacenar los datos de las generaciones y los iniciales, y archivos INI para la configuración.
Las plantillas Thymeleaf se utilizan para generar las páginas HTML.

## Ficheros de entrada

Los archivos utilizados como fuente de datos para la generación de las páginas estáticas son los siguientes:

**config.ini**
```ini
[Pokemon Generations & Starters]
name = Pokémon Generations and Starters
description = This webpage shows Pokémon Generations and its respective starters
```

**pokemon-generations.json**
```
{
  "pokemon-generations": [
    {
      "generation-num": 1,
      "generation-name": "Pokémon: Red and Blue",
      "generation-map": "Kanto",
      "starters": [
        {
          "starter-num": 1,
          "starter-name": "Bulbasaur",
          "starter-type": "Grass"
        },
        {
          "starter-num": 2,
          "starter-name": "Charmander",
          "starter-type": "Fire"
        },
        {
          "starter-num": 3,
          "starter-name": "Squirtle",
          "starter-type": "Water"
        }
      ]
    },
    {
      "generation-num": 2,
      "generation-name": "Pokémon: Gold and Silver",
      "generation-map": "Johto",
      "starters": [
        {
          "starter-num": 4,
          "starter-name": "Chikorita",
          "starter-type": "Grass"
        },
        {
          "starter-num": 5,
          "starter-name": "Cyndaquil",
          "starter-type": "Fire"
        },
        {
          "starter-num": 6,
          "starter-name": "Totodile",
          "starter-type": "Water"
        }
      ]
    },
    {
      "generation-num": 3,
      "generation-name": "Pokémon: Ruby and Sapphire",
      "generation-map": "Hoenn",
      "starters": [
        {
          "starter-num": 7,
          "starter-name": "Treecko",
          "starter-type": "Grass"
        },
        {
          "starter-num": 8,
          "starter-name": "Torchic",
          "starter-type": "Fire"
        },
        {
          "starter-num": 9,
          "starter-name": "Mudkip",
          "starter-type": "Water"
        }
      ]
    },
    {
      "generation-num": 4,
      "generation-name": "Pokémon: Diamond and Pearl",
      "generation-map": "Sinnoh",
      "starters": [
        {
          "starter-num": 10,
          "starter-name": "Turtwig",
          "starter-type": "Grass"
        },
        {
          "starter-num": 11,
          "starter-name": "Chimchar",
          "starter-type": "Fire"
        },
        {
          "starter-num": 12,
          "starter-name": "Piplup",
          "starter-type": "Water"
        }
      ]
    },
    {
      "generation-num": 5,
      "generation-name": "Pokémon: Black and White",
      "generation-map": "Unova",
      "starters": [
        {
          "starter-num": 13,
          "starter-name": "Snivy",
          "starter-type": "Grass"
        },
        {
          "starter-num": 14,
          "starter-name": "Tepig",
          "starter-type": "Fire"
        },
        {
          "starter-num": 15,
          "starter-name": "Oshawott",
          "starter-type": "Water"
        }
      ]
    }
  ]
}
```

**pokemon-generations-schema**
```
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "type": "object",
  "properties": {
    "pokemon-generations": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "generation-num": {
            "type": "integer"
          },
          "generation-name": {
            "type": "string"
          },
          "generation-map": {
            "type": "string"
          },
          "starters": {
            "type": "array",
            "items": {
              "type": "object",
              "properties": {
                "starter-num": {
                  "type": "integer"
                },
                "starter-name": {
                  "type": "string"
                },
                "starter-type": {
                  "type": "string"
                }
              }
            }
          }
        }
      }
    }
  }
}
```

## Librerías, clases y dependencias utilizadas

### Librerías

- **Spring Boot Starter Web**: Utilizada para crear aplicaciones web basadas en Spring.
- **Spring Boot Starter Thymeleaf**: Utilizada para integrar Thymeleaf como motor de plantillas.
- **Jackson Databind**: Utilizada para la serialización y deserialización de objetos JSON.

### Clases

- `PokemonData` representa los datos de las generaciones de Pokémon.
- `Generation` representa cada generación de Pokémon.
- `Starter` representa cada Pokémon inicial.

### Dependencias

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-thymeleaf`
- `com.fasterxml.jackson.core:jackson-databind`
- `org.springframework.boot:spring-boot-starter-test`

## Plantillas Thymeleaf

Thymeleaf es un motor de plantillas para Java que permite crear archivos HTML.
En este proyecto existen dos plantillas utilizadas para la generación de páginas estáticas:

`templateGenerations.html` para la página principal que muestra la lista de generaciones Pokémon:
```
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Generations</title>
    <link rel="stylesheet" type="text/css" href="/styles/styleTemplateGenerations.css">
</head>
<body>
    <header>
        <h1 th:text="${name}"></h1>
        <p th:text="${description}"></p>
    </header>
    <main>
        <table>
            <thead>
            <tr>
                <th>Num</th>
                <th>Name</th>
                <th>Map</th>
                <th>Starters</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="generation : ${generations}">
                <td th:text="${generation.generationNum}"></td>
                <td th:text="${generation.generationName}"></td>
                <td th:text="${generation.generationMap}"></td>
                <td><a th:href="@{'details_' + ${generation.generationNum} + '.html'}">See Starters</a></td>
            </tr>
            </tbody>
        </table>
    </main>
</body>
</html>
```

`templateStarters.html` generará una página para los Pokémon iniciales por cada generación:
```
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Generations</title>
    <link rel="stylesheet" type="text/css" href="/styles/styleTemplateStarters.css">
</head>
<body>
    <header>
        <h1 th:text="${name}"></h1>
        <p th:text="${description}"></p>
    </header>
    <main>
        <table>
            <thead>
            <tr>
                <th>Num</th>
                <th>Name</th>
                <th>Type</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="starter : ${starters}">
                <td th:text="${starter.starterNum}"></td>
                <td th:text="${starter.starterName}"></td>
                <td th:text="${starter.starterType}"></td>
            </tr>
            </tbody>
        </table>
    </main>
</body>
</html>
```

## Ficheros de salida

Los archivos se crean dentro de resources, en una carpeta llamada "generated", incluyendo la página principal (index.html) y una pagina para cada una de las generaciones (details_[generation-num]).
Además de lo anterior, se crea también un archivo RSS (rss.xml).

La página principal que muestra las generaciones queda así:
![image](images/index-page.png)
La página para los iniciales de cada generación queda así:
![image](images/details-page.png)

## Problemas resueltos y no resueltos

### Problemas resueltos

el siguiente mensaje se mostraba en la consola al generar las páginas:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```
Esto deja de suceder imoortando la siguiente dependencia:
```
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.6</version>
</dependency>
```

### Problemas por resolver

La salida de `rss.xml` no es identada.

## Recursos y bibliografía

https://aules.edu.gva.es/fp/pluginfile.php/7965809/mod_resource/content/4/Exemple%20Thymeleaf.pdf
https://es.wikipedia.org/wiki/Thymeleaf
https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html
https://github.com/martinwojtus/tutorials
https://es.wikipedia.org/wiki/RSS