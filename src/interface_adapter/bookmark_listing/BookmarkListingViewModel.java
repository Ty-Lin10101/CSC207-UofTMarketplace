package interface_adapter.bookmark_listing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BookmarkListingViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean bookmarked;
    private String errorMessage;

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void notifyViewOfChange() {
        support.firePropertyChange("bookmark", null, this);
    }
}