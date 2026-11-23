package use_case.searchListings;

public class SearchListingFilters {
    private final Double minimumPrice;
    private final Double maximumPrice;
    private final String category;
    private final String condition;
    private final String campus;

    public SearchListingFilters(Double minimumPrice, Double maximumPrice,
                                String category, String condition, String campus) {
        if (minimumPrice != null && (!Double.isFinite(minimumPrice) || minimumPrice < 0)) {
            throw new IllegalArgumentException("minimumPrice must be a finite non-negative value");
        }
        if (maximumPrice != null && (!Double.isFinite(maximumPrice) || maximumPrice < 0)) {
            throw new IllegalArgumentException("maximumPrice must be a finite non-negative value");
        }
        if (minimumPrice != null && maximumPrice != null && minimumPrice > maximumPrice) {
            throw new IllegalArgumentException("minimumPrice cannot exceed maximumPrice");
        }

        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.category = normalize(category);
        this.condition = normalize(condition);
        this.campus = normalize(campus);
    }

    public static SearchListingFilters none() {
        return new SearchListingFilters(null, null, null, null, null);
    }

    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public Double getMaximumPrice() {
        return maximumPrice;
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

    private static String normalize(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}
