package use_case.bookmark_listing;

public interface BookmarkListingOutputBoundary {
    void prepareSuccessView(BookmarkListingOutputData outputData);
    void prepareFailView(String errorMessage);
}
