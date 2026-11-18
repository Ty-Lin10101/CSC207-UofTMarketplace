package use_case.ViewListingDetails;

public interface ViewListingDetailsOutputBoundary {
    void prepareFailView(String errorMessage);
    void prepareView(ViewListingDetailsOutputData outputData);
}
