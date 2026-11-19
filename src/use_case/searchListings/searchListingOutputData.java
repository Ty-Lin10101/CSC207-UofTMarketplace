import java.util.ArrayList;
import java.util.List;

public class searchListingOutputData {
    private final String userInput;
    private final ArrayList<Listing> listings;

    public searchListingOutputData(String userInput, List<Listing> listings) {
        this.userInput = userInput;
        this.listings = new ArrayList<>(listings);
    }

    public String getUserInput() {
        return userInput;
    }

    public ArrayList<Listing> getListings() {
        return new ArrayList<>(listings);
    }
}
