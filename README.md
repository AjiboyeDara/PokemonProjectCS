# Pokemon Dataset Explorer  
Group Members: Oluwadarasimi Temitope Ajiboye, Nathan Levine, Zijan Li  
Course: CompSci 201 – Spring 2025 Group Project  

## Project Overview  
We built a queryable interface over a large Pokémon dataset. The goal was to explore Pokémon data by supporting efficient exact-match and range-based queries. We achieved fast, indexed lookups over attributes such as type, stats, and weaknesses.

## Current Files  
- `Pokemon.java`: Class representing a Pokémon, with all relevant attributes like name, stats, and weaknesses.  
- `PokemonDataInterface.java`: Interface for loading and querying Pokémon data.  
- `PokemonQueryImpl.java`: Implementation of the dataset interface, optimized with attribute indexing.  
- `PokemonQueryImplTest.java`: JUnit test class for validating query implementations.  
- `Main.java`: Demo class for loading the dataset and running sample queries.  
- `pokemon_data_pdfversion.pdf`: Source dataset with Pokémon base stats and weaknesses.

## What We've Done  
- Defined the `Pokemon` class to encapsulate all relevant attributes (ID, name, stats, type, grass weakness).  
- Fully implemented the `PokemonQueryImpl` backend:
  - Fast exact match queries using HashMap-based indexing for O(1) lookups.
  - Range queries using a custom heap-based filter with limit support.
  - Average query to calculate mean attribute values based on filtering criteria.
- Loaded and parsed the CSV-converted Pokémon dataset from PDF.  
- Indexed attributes including name, type, ID, stats, and grass weakness.  
- Wrote unit tests for each method to validate correctness and handle edge cases.  
- Built a clean interface (`PokemonDataInterface`) for modularity and testability.

## Final Goals (Achieved)  
- [x] Create `Pokemon` class  
- [x] Implement exact match queries with HashMap indexing  
- [x] Implement range query with limit and sorted output  
- [x] Implement average query with dynamic filtering  
- [x] Write comprehensive unit tests (JUnit)  
- [x] Write this project documentation

## Design Notes  
- Data is indexed in `Map<String, Map<Object, List<Pokemon>>>` format to allow constant-time retrieval on supported fields.  
- Multi-key aliasing (e.g., `sp_attack`, `spattack`) is supported for attribute flexibility.  
- Input normalization ensures case-insensitive and robust string matching.  
- Defensive programming used for unsupported attributes and bad input.  

## Sample Usage  
```java
PokemonDataInterface data = new PokemonQueryImpl();
data.loadDataset("pokemon.csv");

List<Pokemon> fireTypes = data.exactMatchQuery("type", "Fire");
List<Pokemon> speedy = data.rangeQuery("speed", 100, 150, 10);
double avgDef = data.averageQuery("defense", "grass_weakness", 1.0);
