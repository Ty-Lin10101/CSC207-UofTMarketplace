package interface_adapter.searchListings;

import use_case.searchListings.searchListingInputBoundary;
import use_case.searchListings.searchListingInputData;
import use_case.searchListings.searchListingOutputData;

public class searchListingsController {
    private final searchListingInputBoundary searchListingsInteractor;

    public searchListingsController(searchListingInputBoundary searchListingsInteractor) {
        this.searchListingsInteractor = searchListingsInteractor;
    }

    public searchListingOutputData execute(String userInput) {
        searchListingInputData inputData = new searchListingInputData(userInput);
        return searchListingsInteractor.execute(inputData);
    }
}