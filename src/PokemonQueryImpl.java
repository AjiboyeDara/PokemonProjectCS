import java.io.*;
import java.util.*;
import java.nio.file.*;

public class PokemonQueryImpl implements PokemonDataInterface {

    private final List<Pokemon> pokemonList = new ArrayList<>();

    @Override
    public int loadDataset(String filePath) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(filePath));
        String line;

        // This is to skip the header - Dara
        line = reader.readLine();
        if (line == null) {
            throw new IOException("File is empty");
        }

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");

            // Basic parsing: This is to adjust indices based on our CSV column order - Dara
            int id = Integer.parseInt(tokens[0].trim());
            String name = tokens[1].trim();
            int hp = Integer.parseInt(tokens[2].trim());
            int attack = Integer.parseInt(tokens[3].trim());
            int defense = Integer.parseInt(tokens[4].trim());
            int spAttack = Integer.parseInt(tokens[5].trim());
            int spDefense = Integer.parseInt(tokens[6].trim());
            int speed = Integer.parseInt(tokens[7].trim());
            int baseStats = Integer.parseInt(tokens[8].trim());
            double grassWeakness = Double.parseDouble(tokens[13].trim());

            Pokemon p = new Pokemon(id, name, hp, attack, defense, spAttack, spDefense, speed, baseStats, grassWeakness);
            pokemonList.add(p);
        }

        reader.close();
        return pokemonList.size();
    }

    @Override
    // I want to do the ExactMatchQuery - Dara Ajiboye
    public List<Pokemon> exactMatchQuery(String attribute, Object value, int topN) {
        List<Pokemon> matching = new ArrayList<>();
        // I'm going to loop through the loaded Pokémon and find all that match the given attribute and value - Dara
        for (Pokemon p : pokemonList) {
            if (attribute.equalsIgnoreCase("name")) {
                if (p.getName().equalsIgnoreCase((String) value)) {
                    matching.add(p);
                }
            } else if (attribute.equalsIgnoreCase("type")) {
                // I haven't implemented type in the Pokemon class yet, but when I do, I’ll check if p.getType().equalsIgnoreCase((String) value) - Dara
            } else if (attribute.equalsIgnoreCase("id")) {
                if (p.getId() == (int) value) {
                    matching.add(p);
                }
            }
            // I plan to support more attributes like "hp", "attack", "speed" later
            // I'll need to use reflection or a manual chain of if-else blocks to handle each attribute cleanly
        }
        // Now I want to sort the matches by Attack power in descending order
        // (I assume that’s the metric of strength I'm using for now)
        matching.sort((a, b) -> Integer.compare(b.getAttack(), a.getAttack()));
        // If the result list is longer than topN, I will trim it
        if (matching.size() > topN) {
            return matching.subList(0, topN);
        }
        return matching;
    }

    @Override
    public List<Pokemon> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound, int limit) {
        return List.of();
    }

    @Override
    public double averageQuery(String attributeToAverage, String filterAttribute, double threshold) {
        return 0;
    }

    // Optional getter for testing/debugging - Dara
    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
}
