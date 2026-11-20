package Entities;

import java.time.LocalDateTime;

public class Review {
    // Everything is final - Reviews can't be edited
    private final String id;
    private final User reviewer;
    private final User reviewee;
    private final int listingId;
    private final float starRating;
    private final String description;
    private final boolean isItemBought;
    private final LocalDateTime timeReviewed;

    public Review(String id, User reviewer, User reviewee, int listingId, float starRating, String description,
                  boolean isItemBought, LocalDateTime timeReviewed) {
        this.id = id;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.listingId = listingId;
        this.starRating = starRating;
        this.description = description;
        this.isItemBought = isItemBought;
        this.timeReviewed = timeReviewed;

    }

    public String getId() {
        return id;
    }

    public User getReviewer() {
        return reviewer;
    }

    public User getReviewee() {
        return reviewee;
    }

    public int getListingId() {
        return listingId;
    }

    public float getStarRating() {
        return starRating;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsItemBought() {
        return isItemBought;
    }

    public LocalDateTime getTimeReviewed() {
        return timeReviewed;
    }
}