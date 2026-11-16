import java.time.LocalDateTime;

public class Review {
    private String id;
    private User reviewer;
    private User reviewee;
    private int listingId;
    private float starRating;
    private String description;
    private boolean isItemBought;
    private LocalDateTime timeReviewed;
}