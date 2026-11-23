package use_case.view_bookmarks;

import java.util.ArrayList;
import java.util.List;

public class ViewBookmarksOutputData {
    private final List<BookmarkedListingData> bookmarks;

    public ViewBookmarksOutputData(List<BookmarkedListingData> bookmarks) {
        this.bookmarks = new ArrayList<>(bookmarks);
    }

    public List<BookmarkedListingData> getBookmarks() {
        return bookmarks;
    }
}