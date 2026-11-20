package interface_adapter.bookmark_listing;

import use_case.bookmark_listing.BookmarkListingOutputBoundary;
import use_case.bookmark_listing.BookmarkListingOutputData;

public class BookmarkListingPresenter implements BookmarkListingOutputBoundary {
    private final BookmarkListingViewModel viewModel;

    public BookmarkListingPresenter(BookmarkListingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(BookmarkListingOutputData outputData) {
        viewModel.setBookmarked(outputData.isBookmarked());
        viewModel.setErrorMessage(null);
        viewModel.notifyViewOfChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setErrorMessage(errorMessage);
        viewModel.notifyViewOfChange();
    }
}