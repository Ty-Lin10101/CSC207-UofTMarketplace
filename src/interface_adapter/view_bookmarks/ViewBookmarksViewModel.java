package interface_adapter.view_bookmarks;

import use_case.view_bookmarks.BookmarkedListingData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ViewBookmarksViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private List<BookmarkedListingData> bookmarks = new ArrayList<>();
    private String message = "";

    public List<BookmarkedListingData> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<BookmarkedListingData> bookmarks) {
        this.bookmarks = new ArrayList<>(bookmarks);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void notifyViewOfChange() {
        support.firePropertyChange("bookmarks", null, this);
    }
}