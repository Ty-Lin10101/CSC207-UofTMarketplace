package use_case.searchListings;

import java.util.ArrayList;

import Entities.Listing;

public class searchListingsInteractor implements searchListingInputBoundary {
    private final searchListingDataAccessInterface listingDataAccess;
    private final searchListingOutputBoundary searchPresenter;

    public searchListingsInteractor(searchListingDataAccessInterface listingDataAccess,
                                    searchListingOutputBoundary searchPresenter) {
        this.listingDataAccess = listingDataAccess;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public searchListingOutputData execute(searchListingInputData inputData) {
        String keyword = inputData.getUserInput() == null ? "" : inputData.getUserInput().trim();
        ArrayList<Listing> activeListings = new ArrayList<>();

        String[] keywords = keyword.isEmpty() ? new String[]{""} : keyword.split("\\s+");
        for (String searchTerm : keywords) {
            ArrayList<Listing> matchingListings = listingDataAccess.findListingsByKeyword(searchTerm);
            for (Listing listing : matchingListings) {
                if (listing.getStatus() == Listing.Status.Active && !activeListings.contains(listing)) {
                    activeListings.add(listing);
                }
            }
        }

        searchListingOutputData outputData = new searchListingOutputData(keyword, activeListings);
        searchPresenter.prepareSuccessView(outputData);
        return new searchListingOutputData(keyword, activeListings);
    }
}
