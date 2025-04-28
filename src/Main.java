import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PokemonQueryImpl query = new PokemonQueryImpl();
        try {
            int count = query.loadDataset("data/pokemon_data.csv");
            // this loads all Pokémon into the project - Dara
            System.out.println("Successfully loaded " + count + " Pokémon!");

            /* Remove Comment if you want to test the Exact Match Query and Play around with it if you want to - Dara
            // This is to run exact match query for type = "Water"
            List<Pokemon> waterTypes = query.exactMatchQuery("type", "Water");
            System.out.println("Water-type Pokémon found: " + waterTypes.size());

            for (Pokemon p : waterTypes) {
                System.out.println(p.getName() + " - Type: " + p.getType());
            }
            */

            /* range query - delete comment if you want to test:
            List<Pokemon> hp = query.rangeQuery("hp", 100, 200, 1000);
            System.out.println("Pokemon with hp between 100 and 200: " + hp.size());

            for (Pokemon p : hp) {
                System.out.println(p.getName() + " - Type: " + p.getType() + " - hp: " + p.getHp());
            }
            */

            /* average query - delete comment if you want to test:
            // This is to run average query for attack with HP filter
            double avgAttack = query.averageQuery("attack", "hp", 100.0);
            System.out.println("Average attack for Pokémon with HP >= 100: " + avgAttack);
            */

        } catch (IOException e) {
            System.out.println("Error loading dataset: " + e.getMessage());
        }
    }
}
