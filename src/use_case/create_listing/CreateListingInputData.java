package use_case.create_listing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CreateListingInputData {
    private final String title;
    private final String description;
    private final String price;
    private final String category;
    private final String condition;
    private final String campus;
    private final List<String> imagePaths;

    public CreateListingInputData(String title, String description, String price, String category,
                                  String condition, String campus, List<String> imagePaths) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.condition = condition;
        this.campus = campus;
        this.imagePaths = imagePaths == null
                ? List.of() : Collections.unmodifiableList(new ArrayList<>(imagePaths));
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public String getCampus() {
        return campus;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }
}
