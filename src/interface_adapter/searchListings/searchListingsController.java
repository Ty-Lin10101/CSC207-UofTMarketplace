package interface_adapter.searchListings;

import use_case.searchListings.searchListingInputBoundary;
import use_case.searchListings.searchListingInputData;
import use_case.searchListings.searchListingOutputData;
import use_case.searchListings.SearchListingFilters;

public class searchListingsController {
    private final searchListingInputBoundary searchListingsInteractor;

    public searchListingsController(searchListingInputBoundary searchListingsInteractor) {
        this.searchListingsInteractor = searchListingsInteractor;
    }

    public searchListingOutputData execute(String userInput) {
        return execute(userInput, SearchListingFilters.none());
    }

    public searchListingOutputData execute(String userInput, SearchListingFilters filters) {
        searchListingInputData inputData = new searchListingInputData(userInput, filters);
        return searchListingsInteractor.execute(inputData);
    }
}