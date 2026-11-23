package use_case.create_listing;

import Entities.Listing;
import Entities.ListingBuilder;
import Entities.ListingImages;
import Entities.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public final class CreateListingInteractor implements CreateListingInputBoundary {
    private final CreateListingDataAccessInterface dataAccess;
    private final ImageUploadGateway imageUploader;
    private final CreateListingOutputBoundary presenter;

    public CreateListingInteractor(CreateListingDataAccessInterface dataAccess,
                                   ImageUploadGateway imageUploader,
                                   CreateListingOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.imageUploader = imageUploader;
        this.presenter = presenter;
    }

    @Override
    public void execute(CreateListingInputData inputData) {
        User seller = dataAccess.getCurrentUser().orElse(null);
        if (seller == null) {
            presenter.prepareFailView("Log in to publish a listing.");
            return;
        }

        double price;
        try {
            price = validate(inputData);
        } catch (IllegalArgumentException exception) {
            presenter.prepareFailView(exception.getMessage());
            return;
        }

        List<String> uploadedImages;
        try {
            uploadedImages = imageUploader.uploadImages(inputData.getImagePaths());
        } catch (IOException exception) {
            presenter.prepareFailView("Couldn't upload photos. Check that each photo is a readable JPG or PNG and try again.");
            return;
        }

        Listing listing = new ListingBuilder()
                .name(inputData.getTitle().strip())
                .description(inputData.getDescription().strip())
                .price(price)
                .categories(List.of(inputData.getCategory().strip()))
                .condition(inputData.getCondition().strip())
                .campus(inputData.getCampus().strip())
                .status(Listing.Status.Active)
                .seller(seller)
                .imageGallery(uploadedImages)
                .build();

        try {
            dataAccess.saveListing(listing);
        } catch (IOException exception) {
            imageUploader.discardImages(uploadedImages);
            presenter.prepareFailView("Couldn't save the listing. Please try again.");
            return;
        }

        presenter.prepareSuccessView(new CreateListingOutputData(listing));
    }

    private static double validate(CreateListingInputData input) {
        requireField(input.getTitle(), "Enter a title.");
        requireField(input.getDescription(), "Enter a description.");
        requireField(input.getPrice(), "Enter a price.");
        requireField(input.getCategory(), "Choose a category.");
        requireField(input.getCondition(), "Choose a condition.");
        requireField(input.getCampus(), "Choose a campus.");

        if (input.getImagePaths().size() > ListingImages.MAX_PHOTOS) {
            throw new IllegalArgumentException("Maximum " + ListingImages.MAX_PHOTOS + " photos.");
        }
        for (String path : input.getImagePaths()) {
            requireField(path, "Remove the invalid photo and try again.");
        }

        return parsePrice(input.getPrice());
    }

    private static void requireField(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    private static double parsePrice(String input) {
        String value = input.strip();
        String message = "Enter a price of $0 or more with up to two decimal places.";
        // Accept a plain decimal amount, such as 10, 10.50, or .50.
        if (!value.matches("(?:[0-9]+(?:\\.[0-9]{0,2})?|\\.[0-9]{1,2})")) {
            throw new IllegalArgumentException(message);
        }
        double price = new BigDecimal(value).doubleValue();
        if (!Double.isFinite(price)) {
            throw new IllegalArgumentException(message);
        }
        return price;
    }
}
