package use_case.bookmark_listing;

public class BookmarkListingInputData {
    private final int listingId;

    public BookmarkListingInputData(int listingId) {
        this.listingId = listingId;
    }

    public int getListingId() {
        return listingId;
    }

}