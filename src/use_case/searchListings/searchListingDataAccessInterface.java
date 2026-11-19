
import java.util.ArrayList;

public interface searchListingDataAccessInterface {
    ArrayList<Listing> findListingsByKeyword(String userInput);
}