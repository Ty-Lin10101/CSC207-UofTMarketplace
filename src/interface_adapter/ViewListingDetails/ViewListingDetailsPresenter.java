package interface_adapter.ViewListingDetails;

import use_case.ViewListingDetails.ViewListingDetailsOutputBoundary;
import use_case.ViewListingDetails.ViewListingDetailsOutputData;

public class ViewListingDetailsPresenter
        implements ViewListingDetailsOutputBoundary {

    private final ViewListingDetailsViewModel viewModel;

    public ViewListingDetailsPresenter(
            ViewListingDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(ViewListingDetailsOutputData data) {
        viewModel.setId(data.getId());
        viewModel.setTitle(data.getTitle());
        viewModel.setDescription(data.getDescription());
        viewModel.setPriceText(String.format("$%.2f", data.getPrice()));
        viewModel.setCondition(data.getCondition());
        viewModel.setCategories(data.getCategories());
        viewModel.setCampus(data.getCampus());
        viewModel.setStatusText(data.getStatus().name());
        viewModel.setSellerName(data.getSellerName());
        viewModel.setSellerRatingText(
                String.format("%.1f / 5", data.getSellerRating()));
        viewModel.setMainImage(data.getMainImage());
        viewModel.setImageGallery(data.getImageGallery());
        viewModel.setErrorMessage(null);

        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.clearListing();
        viewModel.setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}