package data_access;

import java.util.ArrayList;
import javax.sql.DataSource;

import Entities.Listing;
import use_case.searchListings.searchListingDataAccessInterface;

public class SupabaseMarketplaceDataAccessObject implements searchListingDataAccessInterface {
    public SupabaseMarketplaceDataAccessObject(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource cannot be null");
        }
    }

    @Override
    public ArrayList<Listing> findListingsByKeyword(String keyword) {
        return new ArrayList<>();
    }
}
