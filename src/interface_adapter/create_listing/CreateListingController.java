package interface_adapter.create_listing;

import use_case.create_listing.CreateListingInputBoundary;
import use_case.create_listing.CreateListingInputData;

import java.util.List;

public final class CreateListingController {
    private final CreateListingInputBoundary interactor;

    public CreateListingController(CreateListingInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String title, String description, String price, String category,
                        String condition, String campus, List<String> imagePaths) {
        interactor.execute(new CreateListingInputData(
                title, description, price, category, condition, campus, imagePaths));
    }
}
