package use_case.searchListings;

public class searchListingInputData {
    private final String userInput;

    public searchListingInputData(String userInput) {
        this.userInput = userInput;
    }

    public String getUserInput() {
        return userInput;
    }
}
