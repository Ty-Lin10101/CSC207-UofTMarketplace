package use_case.ViewListingDetails;

public class ViewListingDetailsInputData {
    private final int ListingID;


    public ViewListingDetailsInputData(int listingID) {
        ListingID = listingID;
    }

    public int getListingID() {
        return ListingID;
    }
}
