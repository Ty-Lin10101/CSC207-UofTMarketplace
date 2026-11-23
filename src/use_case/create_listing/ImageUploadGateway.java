package use_case.create_listing;

import java.io.IOException;
import java.util.List;

public interface ImageUploadGateway {
    /** Returns stored locations in selection order, or throws and rolls back the batch. */
    List<String> uploadImages(List<String> imagePaths) throws IOException;

    /** Discards the images from a successful upload when the listing cannot be saved. */
    void discardImages(List<String> imageLocations);
}
