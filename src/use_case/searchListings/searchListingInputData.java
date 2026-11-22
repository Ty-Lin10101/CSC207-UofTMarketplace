package use_case.searchListings;

import java.util.Objects;

public class searchListingInputData {
    private final String userInput;
    private final SearchListingFilters filters;

    public searchListingInputData(String userInput) {
        this(userInput, SearchListingFilters.none());
    }

    public searchListingInputData(String userInput, SearchListingFilters filters) {
        this.userInput = userInput;
        this.filters = Objects.requireNonNull(filters, "filters cannot be null");
    }

    public String getUserInput() {
        return userInput;
    }

    public SearchListingFilters getFilters() {
        return filters;
    }
}
