import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PokemonQueryImplTest {

    private PokemonQueryImpl query;

    @BeforeEach
    void setUp() throws IOException {
        query = new PokemonQueryImpl();
        int loaded = query.loadDataset("data/pokemon_data.csv");
        assertTrue(loaded > 0, "No Pokémon loaded from dataset");
    }

    @Test
    void loadDataset() throws IOException {
        PokemonQueryImpl tempQuery = new PokemonQueryImpl();
        int count = tempQuery.loadDataset("data/pokemon_data.csv");
        assertTrue(count > 0, "Expected at least 1 Pokémon to be loaded");
    }

    @Test
    void exactMatchQuery() {
        List<Pokemon> results = query.exactMatchQuery("name", "Pikachu");
        assertFalse(results.isEmpty(), "Expected to find at least one Pikachu");
        assertEquals("Pikachu", results.get(0).getName(), "Name doesn't match");

        List<Pokemon> idMatch = query.exactMatchQuery("id", 25);
        assertFalse(idMatch.isEmpty(), "Expected to find Pokémon with ID 25");

        List<Pokemon> attackMatch = query.exactMatchQuery("attack", 55);
        assertTrue(attackMatch.stream().anyMatch(p -> p.getName().equalsIgnoreCase("Pikachu")),
                "Pikachu should be among Pokémon with attack 55");

        List<Pokemon> unknown = query.exactMatchQuery("banana", "yellow");
        assertTrue(unknown.isEmpty(), "Unsupported attributes should return an empty list");
    }

    @Test
    void rangeQuery() {
        // normal range query on HP
        List<Pokemon> hpResults = query.rangeQuery("hp", 70, 100, 5);
        assertNotNull(hpResults, "Result should not be null");
        assertTrue(hpResults.size() <= 5, "Should return at most 5 Pokémon");
        assertTrue(hpResults.stream().allMatch(p -> p.getHp() >= 70 && p.getHp() <= 100),
                "All Pokémon should have HP between 70 and 100");

        // range query on Attack with no matches
        List<Pokemon> attackResults = query.rangeQuery("attack", 500, 600, 5);
        assertNotNull(attackResults, "Result should not be null even if no matches");
        assertTrue(attackResults.isEmpty(), "No Pokémon should have attack between 500 and 600");

        // limit smaller than possible results
        List<Pokemon> speedResults = query.rangeQuery("speed", 80, 150, 2);
        assertNotNull(speedResults, "Result should not be null");
        assertTrue(speedResults.size() <= 2, "Should return at most 2 Pokémon");

        // check sorting order (speed descending)
        if (speedResults.size() > 1) {
            assertTrue(speedResults.get(0).getSpeed() >= speedResults.get(1).getSpeed(),
                    "Pokémon should be ordered from highest to lowest speed");
        }

        // unsupported attribute (should return empty)
        List<Pokemon> invalidAttribute = query.rangeQuery("banana", 0, 100, 5);
        assertNotNull(invalidAttribute, "Result should not be null even with invalid attribute");
        assertTrue(invalidAttribute.isEmpty(), "Unsupported attributes should return an empty list");
    }


    @Test
    void averageQuery() {
    }

    @Test
    void getPokemonList() {
        List<Pokemon> list = query.getPokemonList();
        assertNotNull(list);
        assertTrue(list.size() > 0, "Expected non-empty Pokémon list");
    }
}
