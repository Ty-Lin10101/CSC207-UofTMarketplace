package interface_adapter.create_listing;

import use_case.create_listing.CreateListingOutputBoundary;
import use_case.create_listing.CreateListingOutputData;

public final class CreateListingPresenter implements CreateListingOutputBoundary {
    private final CreateListingViewModel viewModel;

    public CreateListingPresenter(CreateListingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreateListingOutputData outputData) {
        viewModel.showPublishedListing(outputData);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.showError(errorMessage);
    }
}
