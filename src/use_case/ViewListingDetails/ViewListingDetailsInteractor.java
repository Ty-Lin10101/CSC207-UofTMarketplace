package use_case.ViewListingDetails;

import Entities.Listing;

public class ViewListingDetailsInteractor implements ViewListingDetailsInputBoundary {
    private ViewListingDetailsDataAccessInterface dao;
    private ViewListingDetailsOutputBoundary presenter;

    public ViewListingDetailsInteractor(ViewListingDetailsOutputBoundary presenter,
                                        ViewListingDetailsDataAccessInterface dao) {
        this.presenter = presenter;
        this.dao = dao;
    }

    @Override
    public void execute(ViewListingDetailsInputData inputData) {

        final int listingID = inputData.getListingID();
        Listing listing = dao.getListingByID(listingID);

        if (listing == null) {
            presenter.prepareFailView("This listing is no longer available.");
        } else {
            ViewListingDetailsOutputData outputData = new ViewListingDetailsOutputData(
                    listing.getId(),
                    listing.getName(),
                    listing.getDescription(),
                    listing.getPrice(),
                    listing.getCondition(),
                    listing.getCategories(),
                    listing.getCampus(),
                    listing.getStatus(),
                    listing.getSeller().getFirstName() + " " + listing.getSeller().getLastName(),
                    listing.getSeller().getUserRating(),
                    listing.getImages().getMainImage(),
                    listing.getImages().getGallery()
            );
            presenter.prepareView(outputData);
        }


    }
}
