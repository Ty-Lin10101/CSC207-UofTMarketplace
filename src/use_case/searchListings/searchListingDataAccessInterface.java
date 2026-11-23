package use_case.searchListings;

import java.util.ArrayList;

import Entities.Listing;

public interface searchListingDataAccessInterface {
    ArrayList<Listing> findListingsByKeyword(String userInput);
}