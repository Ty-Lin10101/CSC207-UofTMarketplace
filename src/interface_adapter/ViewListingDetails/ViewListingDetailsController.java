package interface_adapter.ViewListingDetails;

import use_case.ViewListingDetails.ViewListingDetailsInputBoundary;
import use_case.ViewListingDetails.ViewListingDetailsInputData;

public class ViewListingDetailsController {
    private final ViewListingDetailsInputBoundary interactor;

    public ViewListingDetailsController(
            ViewListingDetailsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(int listingId) {
        ViewListingDetailsInputData inputData =
                new ViewListingDetailsInputData(listingId);
        interactor.execute(inputData);
    }
}