# Pokemon Dataset Explorer
Group Members: Oluwadarasimi Temitope Ajiboye, Nathan Levine, Zijan Li  
Course: CompSci 201 – Spring 2025 Group Project  

## Project Overview
We are building a queryable interface over a Pokémon dataset. The goal is to explore Pokémon data by supporting exact-match and range-based queries, 
allowing us to search based on type, stats, weaknesses, and other attributes.

## Current Files
- PokemonDataInterface.java: Interface for interacting with the Pokémon dataset.
- DatasetQuery.java: Generic query interface for filtering datasets.
- pokemon_data_pdfversion.pdf: Source dataset with Pokémon name, types, base stats, weaknesses, and more.

## What We Have So Far
- Defined an interface (PokemonDataInterface) for loading and querying Pokémon data.
- Defined DatasetQuery with methods:
  - exactMatchQuery(String attribute, Object value)
  - rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound)
- Method stub for loading data:
  ```java
  int loadDataset(String filePath) throws IOException;
  
## Goals
- [ ] Create a Pokemon class that holds all relevant attributes (name, type(s), base stats, etc.)
- [ ] Implement PokemonDataBackend to store Pokémon and handle queries
- [ ] Use efficient data structures (e.g., HashMap, TreeMap) for fast lookups
- [ ] Parse the Pokémon dataset and load it into objects
- [ ] Add unit tests to verify accuracy of queries and data loading

## Design Ideas
- Keep the design modular using interfaces for flexibility and easier testing
- Make it scalable to support different datasets beyond Pokémon
- Stretch Goal: Support query chaining and sorting, e.g.:
  filter("Type", "Fire").range("Attack", 80, 120).sortBy("Speed");

## Sample Usage
PokemonDataInterface data = new PokemonDataBackend();
data.loadDataset("pokemon.csv");

List<Pokemon> fireTypes = data.exactMatchQuery("Type", "Fire");
List<Pokemon> strongPokemons = data.rangeQuery("Attack", 100, 150);

