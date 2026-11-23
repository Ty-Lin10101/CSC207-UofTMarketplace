package use_case.bookmark_listing;

public class BookmarkListingOutputData {
    private final int listingId;
    private final boolean bookmarked;

    public BookmarkListingOutputData(int listingId, boolean bookmarked) {
        this.listingId = listingId;
        this.bookmarked = bookmarked;
    }

    public int getListingId() {
        return listingId;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }
}
