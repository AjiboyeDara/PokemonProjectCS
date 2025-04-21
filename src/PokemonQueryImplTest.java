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
