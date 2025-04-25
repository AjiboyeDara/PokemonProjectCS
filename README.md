# Pokemon Dataset Explorer  
Group Members: Oluwadarasimi Temitope Ajiboye, Nathan Levine, Zijan Li  
Course: CompSci 201 – Spring 2025 Group Project  

## Project Overview  
We are building a queryable interface over a large Pokémon dataset. The goal is to explore Pokémon data by supporting efficient exact-match and range-based queries. We aim to search based on type, stats, weaknesses, and other attributes using fast, indexed lookups.

## Current Files  
- `Pokemon.java`: Class representing a Pokémon, with all relevant attributes like name, stats, and weaknesses.  
- `PokemonDataInterface.java`: Interface for loading and querying Pokémon data.  
- `PokemonQueryImpl.java`: Main backend implementation of the dataset interface, using optimized data structures.  
- `DatasetQuery.java`: Generic query interface for filtering datasets, supporting multiple query types.  
- `pokemon_data_pdfversion.pdf`: Source dataset with Pokémon attributes including base stats and weaknesses.

## What We've Done So Far  
- Created the `Pokemon` class to encapsulate all relevant attributes.  
- Designed and implemented `PokemonDataInterface` with methods for:
  - `int loadDataset(String filePath)`  
  - `List<Pokemon> exactMatchQuery(String attribute, Object value)`  
  - `List<Pokemon> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound)`  
- Fully implemented `PokemonQueryImpl` with:
  - HashMap-based indexing for exactMatchQuery, optimizing lookups to O(1)
  - Indexing logic for supported attributes (e.g., name, type, stats)
- Parsed and loaded the dataset from the provided CSV (converted from PDF)
- Added error handling for unsupported attributes and malformed input
- Began writing unit tests for core methods

## Goals (In Progress)  
- [x] Create a `Pokemon` class  
- [x] Implement `PokemonQueryImpl` with HashMap indexing  
- [x] Implement full `rangeQuery` functionality using TreeMap or similar  
- [ ] Add unit tests to cover edge cases and verify correctness  
- [ ] Write analysis documentation (time/space complexity, design choices)

## Design Notes  
- Using `HashMap<String, Map<Object, List<Pokemon>>>` to index attributes for fast querying  
- Modular design using interfaces allows for clean testing and alternative backends  
- Stretch goal: Enable query chaining and sorting  
  - e.g., `filter("Type", "Fire").range("Attack", 80, 120).sortBy("Speed")`

## Sample Usage  
```java
PokemonDataInterface data = new PokemonQueryImpl();
data.loadDataset("pokemon.csv");

List<Pokemon> fireTypes = data.exactMatchQuery("type", "Fire");
List<Pokemon> speedy = data.rangeQuery("Speed", 100, 150);
