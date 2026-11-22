package use_case.searchListings;

import java.util.ArrayList;
import java.util.List;

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
                if (listing.getStatus() == Listing.Status.Active
                        && matchesFilters(listing, inputData.getFilters())
                        && !activeListings.contains(listing)) {
                    activeListings.add(listing);
                }
            }
        }

        searchListingOutputData outputData = new searchListingOutputData(keyword, activeListings);
        searchPresenter.prepareSuccessView(outputData);
        return outputData;
    }

    private boolean matchesFilters(Listing listing, SearchListingFilters filters) {
        Double minimumPrice = filters.getMinimumPrice();
        Double maximumPrice = filters.getMaximumPrice();
        if (minimumPrice != null && listing.getPrice() < minimumPrice) {
            return false;
        }
        if (maximumPrice != null && listing.getPrice() > maximumPrice) {
            return false;
        }
        if (filters.getCategory() != null
                && !containsIgnoreCase(listing.getCategories(), filters.getCategory())) {
            return false;
        }
        if (filters.getCondition() != null
                && !equalsIgnoreCase(listing.getCondition(), filters.getCondition())) {
            return false;
        }
        return filters.getCampus() == null
                || equalsIgnoreCase(listing.getCampus(), filters.getCampus());
    }

    private boolean containsIgnoreCase(List<String> values, String expected) {
        if (values == null) {
            return false;
        }
        for (String value : values) {
            if (equalsIgnoreCase(value, expected)) {
                return true;
            }
        }
        return false;
    }

    private boolean equalsIgnoreCase(String value, String expected) {
        return value != null && value.trim().equalsIgnoreCase(expected);
    }
}
