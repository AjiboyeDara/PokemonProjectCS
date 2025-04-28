import java.io.*;
import java.util.*;
import java.nio.file.*;

public class PokemonQueryImpl implements PokemonDataInterface {

    private final List<Pokemon> pokemonList = new ArrayList<>();
    private final Map<String, Map<Object, List<Pokemon>>> attributeIndex = new HashMap<>();

    @Override
    public int loadDataset(String filePath) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(filePath));
        String line;

        // This is to skip the header - Dara
        line = reader.readLine();
        if (line == null) {
            throw new IOException("File is empty");
        }

        // Initialize supported attribute index maps - Dara
        Set<String> attributes = Set.of("name", "id", "type", "hp", "attack", "defense",
                "spattack", "sp_attack", "spdefense", "sp_defense",
                "speed", "basestats", "base_stats", "grass_weakness");
        for (String attr : attributes) {
            attributeIndex.put(attr.toLowerCase(), new HashMap<>());
        }

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");

            // Basic parsing: This is to adjust indices based on our CSV column order - Dara
            int id = Integer.parseInt(tokens[0].trim());
            Pokemon p = getPokemon(tokens, id);
            pokemonList.add(p);

            // Index each Pokémon for faster exact match query - Dara
            indexPokemon(p);
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

        return new Pokemon(id, name, hp, attack, defense, spAttack, spDefense, speed, baseStats, grassWeakness, type);
    }

    private void indexPokemon(Pokemon p) {
        // This handles multi-key attributes like sp_attack/spattack - Dara
        addToIndex("name", p.getName().toLowerCase(), p);
        addToIndex("id", p.getId(), p);
        addToIndex("type", p.getType().toLowerCase(), p);
        addToIndex("hp", p.getHp(), p);
        addToIndex("attack", p.getAttack(), p);
        addToIndex("defense", p.getDefense(), p);
        addToIndex("spattack", p.getSpAttack(), p);
        addToIndex("sp_attack", p.getSpAttack(), p);
        addToIndex("spdefense", p.getSpDefense(), p);
        addToIndex("sp_defense", p.getSpDefense(), p);
        addToIndex("speed", p.getSpeed(), p);
        addToIndex("basestats", p.getBaseStats(), p);
        addToIndex("base_stats", p.getBaseStats(), p);
        addToIndex("grass_weakness", p.getGrassWeakness(), p);
    }

    private void addToIndex(String attribute, Object key, Pokemon p) {
        attributeIndex.get(attribute.toLowerCase()).computeIfAbsent(key, k -> new ArrayList<>()).add(p);
    }

    @Override
    public List<Pokemon> exactMatchQuery(String attribute, Object value) {
        List<Pokemon> matching = new ArrayList<>();
        String lowerAttr = attribute.toLowerCase();

        // This should validate attribute first - Dara
        Set<String> validAttributes = Set.of("name", "id", "type", "hp", "attack", "defense",
                "spattack", "sp_attack", "spdefense", "sp_defense", "speed", "basestats",
                "base_stats", "grass_weakness");

        if (!validAttributes.contains(lowerAttr)) {
            System.out.println("Unsupported attribute: " + attribute);
            return matching; // return empty list early
        }

        // Normalize String keys to lowercase for string-based attributes - Dara
        if (value instanceof String && Set.of("name", "type").contains(lowerAttr)) {
            value = ((String) value).toLowerCase();
        }

        // This now uses the indexed map for O(1) lookup - Dara
        return new ArrayList<>(attributeIndex.get(lowerAttr).getOrDefault(value, new ArrayList<>()));
    }

    // returns, in order from highest to lowest, a list of Pokemon that have attributes that fall in
    // the given (numerical) range, with a limit of limit pokemon in the list.
    // only queries (non-binary) numerical attributes
    @Override
    public List<Pokemon> rangeQuery(String attribute, int lowerBound, int upperBound, int limit) {
        List<Pokemon> matching = new ArrayList<>();
        String lowerAttr = attribute.toLowerCase();

        //valid attributes to query
        Set<String> validAttributes = Set.of("id", "hp", "attack", "defense", "spattack", "sp_attack", "spdefense",
                "sp_defense", "speed", "basestats", "base_stats");

        //checks if (param) attribute is valid
        if (!validAttributes.contains(lowerAttr)) {
            System.out.println("Unsupported attribute: " + attribute);
            return matching;
        }

        //create comparator for pokemon
        Comparator<Pokemon> comparator = (p1, p2) -> {
            int val1 = getAttributeValue(p1, lowerAttr);
            int val2 = getAttributeValue(p2, lowerAttr);
            if (val2 == val1) {
                return Integer.compare(p1.getId(), p2.getId());
            }
            return Integer.compare(val2, val1); // Sort from highest to lowest
        };
        TreeMap<Pokemon, Integer> map = new TreeMap<>(comparator);
        for (Pokemon p : pokemonList) {
            int val = getAttributeValue(p, lowerAttr);
            if (val >= lowerBound && val <= upperBound) {
                map.put(p, val);
            }
        }
        for (Pokemon p : map.keySet()) {
            if (matching.size() >= limit) break;
            matching.add(p);
        }
        return matching;
    }

    //helper function to get attribute values within function
    private int getAttributeValue(Pokemon p, String attr) {
        switch (attr) {
            case "id":
                return p.getId();
            case "hp":
                return p.getHp();
            case "attack":
                return p.getAttack();
            case "defense":
                return p.getDefense();
            case "spattack":
            case "sp_attack":
                return p.getSpAttack();
            case "spdefense":
            case "sp_defense":
                return p.getSpDefense();
            case "speed":
                return p.getSpeed();
            case "basestats":
            case "base_stats":
                return p.getBaseStats();
            default:
                throw new IllegalArgumentException("Unsupported attribute: " + attr);
        }
    }

    @Override
    public double averageQuery(String attributeToAverage, String filterAttribute, double threshold) {
        String lowerAttrAvg = attributeToAverage.toLowerCase();
        String lowerAttrFilter = filterAttribute.toLowerCase();

        // valid numeric attributes for querying (including synonyms for special names)
        Set<String> validAttributes = Set.of(
                "id", "hp", "attack", "defense", "spattack", "sp_attack",
                "spdefense", "sp_defense", "speed", "basestats", "base_stats",
                "grass_weakness"
        );

        // validate that both attributes are supported numeric fields
        if (!validAttributes.contains(lowerAttrAvg)) {
            System.out.println("Unsupported attribute: " + attributeToAverage);
            return 0.0;
        }
        if (!validAttributes.contains(lowerAttrFilter)) {
            System.out.println("Unsupported attribute: " + filterAttribute);
            return 0.0;
        }

        // compute the average of the specified attribute for records meeting the filter condition
        double sum = 0.0;
        int count = 0;
        for (Pokemon p : pokemonList) {
            double filterVal = getNumericAttributeValue(p, lowerAttrFilter);
            if (filterVal < threshold) {  // include only Pokémon with filterAttribute value below threshold (exclusive)
                double value = getNumericAttributeValue(p, lowerAttrAvg);
                sum += value;
                count++;
            }
        }

        if (count == 0) {
            return 0.0;  // no Pokémon met the filter condition
        }
        return sum / count;
    }

    // helper function to get numeric attribute values (int or double)
    private double getNumericAttributeValue(Pokemon p, String attr) {
        switch (attr) {
            case "id":
                return p.getId();
            case "hp":
                return p.getHp();
            case "attack":
                return p.getAttack();
            case "defense":
                return p.getDefense();
            case "spattack":
            case "sp_attack":
                return p.getSpAttack();
            case "spdefense":
            case "sp_defense":
                return p.getSpDefense();
            case "speed":
                return p.getSpeed();
            case "basestats":
            case "base_stats":
                return p.getBaseStats();
            case "grass_weakness":
                return p.getGrassWeakness();
            default:
                throw new IllegalArgumentException("Unsupported attribute: " + attr);
        }
    }

    // Optional getter for testing/debugging - Dara
    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
}
