import java.util.ArrayList;
import javax.sql.DataSource;

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
