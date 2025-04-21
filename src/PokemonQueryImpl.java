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
            Pokemon p = getPokemon(tokens, id);
            pokemonList.add(p);
        }

        reader.close();
        return pokemonList.size();
    }

    private static Pokemon getPokemon(String[] tokens, int id) {
        String name = tokens[1].trim();
        int hp = Integer.parseInt(tokens[2].trim());
        int attack = Integer.parseInt(tokens[3].trim());
        int defense = Integer.parseInt(tokens[4].trim());
        int spAttack = Integer.parseInt(tokens[5].trim());
        int spDefense = Integer.parseInt(tokens[6].trim());
        int speed = Integer.parseInt(tokens[7].trim());
        int baseStats = Integer.parseInt(tokens[8].trim());
        double grassWeakness = Double.parseDouble(tokens[13].trim());
        String type = tokens[36].trim();

        Pokemon p = new Pokemon(id, name, hp, attack, defense, spAttack, spDefense, speed, baseStats, grassWeakness, type);
        return p;
    }

    @Override
    public List<Pokemon> exactMatchQuery(String attribute, Object value) {
        List<Pokemon> matching = new ArrayList<>();
        String lowerAttr = attribute.toLowerCase();

        // This should validate attribute first
        Set<String> validAttributes = Set.of("name", "id", "type", "hp", "attack", "defense",
                "spattack", "sp_attack", "spdefense", "sp_defense", "speed", "basestats",
                "base_stats", "grass_weakness");

        if (!validAttributes.contains(lowerAttr)) {
            System.out.println("Unsupported attribute: " + attribute);
            return matching; // return empty list early
        }

        // NOw its going to proceed with matching loop
        for (Pokemon p : pokemonList) {
            switch (lowerAttr) {
                case "name":
                    if (p.getName().equalsIgnoreCase((String) value)) matching.add(p);
                    break;
                case "id":
                    if (p.getId() == (int) value) matching.add(p);
                    break;
                case "type":
                    if (p.getType().equalsIgnoreCase((String) value)) matching.add(p);
                    break;
                case "hp":
                    if (p.getHp() == (int) value) matching.add(p);
                    break;
                case "attack":
                    if (p.getAttack() == (int) value) matching.add(p);
                    break;
                case "defense":
                    if (p.getDefense() == (int) value) matching.add(p);
                    break;
                case "spattack":
                case "sp_attack":
                    if (p.getSpAttack() == (int) value) matching.add(p);
                    break;
                case "spdefense":
                case "sp_defense":
                    if (p.getSpDefense() == (int) value) matching.add(p);
                    break;
                case "speed":
                    if (p.getSpeed() == (int) value) matching.add(p);
                    break;
                case "basestats":
                case "base_stats":
                    if (p.getBaseStats() == (int) value) matching.add(p);
                    break;
                case "grass_weakness":
                    if (p.getGrassWeakness() == (double) value) matching.add(p);
                    break;
            }
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
