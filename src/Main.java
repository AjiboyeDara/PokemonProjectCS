import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PokemonQueryImpl query = new PokemonQueryImpl();
        try {
            int count = query.loadDataset("data/pokemon_data.csv");
            // this loads all Pokémon into the project - Dara
            System.out.println("Successfully loaded " + count + " Pokémon!");

            // Run exact match query for type = "Water" - Dara
            List<Pokemon> waterTypes = query.exactMatchQuery("type", "Water");
            System.out.println("Water-type Pokémon found: " + waterTypes.size());

            for (Pokemon p : waterTypes) {
                System.out.println(p.getName() + " - Type: " + p.getType());
            }

        } catch (IOException e) {
            System.out.println("Error loading dataset: " + e.getMessage());
        }
    }
}
