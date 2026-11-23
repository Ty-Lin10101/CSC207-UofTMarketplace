package use_case.view_bookmarks;

public interface ViewBookmarksOutputBoundary {
    void prepareSuccessView(ViewBookmarksOutputData outputData);
    void prepareFailView(String errorMessage);
}