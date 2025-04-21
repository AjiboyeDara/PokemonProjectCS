import java.io.IOException;
import java.util.List;

public interface PokemonDataInterface {

    /**
     * Loads the Pokémon dataset from the specified file path.
     * @param filePath Path to the dataset file (e.g., CSV format)
     * @return The number of records loaded into memory
     * @throws IOException If there's an error reading the file
     */
    int loadDataset(String filePath) throws IOException;


    /**
     * Returns all Pokémon that exactly match the specified attribute and value.
     * For example, you can find all Pokémon with a given name or ID.
     *
     * @param attribute The attribute to filter by (e.g., "name", "id").
     * @param value The exact value to match (e.g., "Pikachu", 25).
     * @return A list of Pokémon records matching the criteria.
     */
    List<Pokemon> exactMatchQuery(String attribute, Object value);


    /**
     * Returns all Pokémon records where the specified attribute falls within the given range.
     * For example, to find all Pokémon with Base_Stats between 400 and 500.
     *
     * @param attribute The attribute to query on (e.g., "Base_Stats", "Speed").
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (inclusive).
     * @param limit The maximum number of results to return.
     * @return A list of Pokémon records within the specified range.
     */
    List<Pokemon> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound, int limit);


    /**
     * Calculates the average value of a specified attribute across all Pokémon
     * that meet a certain condition (e.g., grass_weakness < 1.0).
     *
     * @param attributeToAverage The attribute to compute the average for (e.g., "Defense").
     * @param filterAttribute The attribute to filter on (e.g., "grass_weakness").
     * @param threshold The upper threshold for filtering (exclusive).
     * @return The average value of the target attribute among filtered Pokémon.
     */
    double averageQuery(String attributeToAverage, String filterAttribute, double threshold);
}
