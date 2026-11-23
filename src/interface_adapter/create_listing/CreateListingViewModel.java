package interface_adapter.create_listing;

import use_case.create_listing.CreateListingOutputData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class CreateListingViewModel {
    public static final String ERROR = "error";
    public static final String PUBLISHED_LISTING = "publishedListing";

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    void showError(String message) {
        changes.firePropertyChange(ERROR, null, message);
    }

    void showPublishedListing(CreateListingOutputData listing) {
        changes.firePropertyChange(PUBLISHED_LISTING, null, listing);
    }
}
