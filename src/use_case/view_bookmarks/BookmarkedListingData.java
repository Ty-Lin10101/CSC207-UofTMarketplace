package use_case.view_bookmarks;

public class BookmarkedListingData {
    private final int listingId;
    private final String name;
    private final double price;

    public BookmarkedListingData(int listingId, String name, double price) {
        this.listingId = listingId;
        this.name = name;
        this.price = price;
    }

    public int getListingId() {
        return listingId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}