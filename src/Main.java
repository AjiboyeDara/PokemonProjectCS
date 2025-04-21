import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PokemonQueryImpl query = new PokemonQueryImpl();
        try {
            int count = query.loadDataset("data/pokemon_data.csv");
            System.out.println("Successfully loaded " + count + " Pokémon!");
        } catch (IOException e) {
            System.out.println("Error loading dataset: " + e.getMessage());
        }
    }
}
