# UofT Marketplace

CSC207 Fall 2026 Group Project

## Team Information

### Team Members
- Owen Zhang
- Tyler Lin (GitHub: Ty-Lin10101)
- Jeffery Chan (GitHub: jycxn)
- Millie Bay (GitHub: millieb-code)
- Houssam Kadri (GitHub: House-House)
- Tony Lin

---

## Project Description

### Domain
**E-commerce / Student Marketplace**

UofT Marketplace is a buy-and-sell platform for University of Toronto students. Users sign up with a UofT email, then post items for sale (title, description, price, category, condition, photos, campus). Other users browse, search and filter listings, view item details, bookmark items, and message sellers to arrange a meetup. After a sale, buyers and sellers rate and review each other so the community can judge who is reliable.

Every account can both buy and sell; "buyer" and "seller" below are roles, not separate account types.

---

## User Stories

Each team member owns one user story and leads its use cases (see [MVP](#minimal-viable-product-mvp)).

### User Story 1 — Sign up and log in
As a user, I want to sign up and log in using my email or social accounts with a secure password, so that I can securely manage my profile, listings, and saved items.

### User Story 2 — Public profile
As a user, I want to create and edit a public profile (display name, profile picture, bio, UofT campus), so that other marketplace members know who they are interacting with.

### User Story 3 — Create a listing
As a seller, I want to create a new listing by adding a title, description, price, category, condition, and up to 10 photos, so that potential buyers have all the information they need to evaluate the item.

### User Story 4 — Search and filter
As a buyer, I want to search for items using keywords in the search bar, and filter results by category, price range, and condition, sorted by newest or price, so that I can quickly find items that fit my needs and budget.

### User Story 5 — Item page
As a buyer, I want to view a dedicated item page showing high-resolution photos, description, price, condition, and seller profile info, so that I can thoroughly evaluate the product before reaching out.

### User Story 6 — Bookmarks
As a buyer, I want to bookmark/save listings to a favorites folder, so that I can easily refer back to them later without losing track.

### Future User Stories (if time allows)
**As a user,**
- I want new accounts to go through verification (such as email or phone number confirmation), so that the marketplace is protected from automated bots and malicious spam accounts.
- I want to report suspicious listings or user profiles, so that moderators can investigate and remove malicious actors from the platform.
- I want to send and receive messages within the marketplace so that I can ask questions, negotiate prices, and arrange meetups without sharing my personal contact information.

**As a seller,**
- I want to edit my existing listing details (price, description, photos), so that I can correct mistakes or update information without recreating the listing.
- I want to change the status of my listing to Active, Pending, or Sold, so that buyers don’t waste time asking about items that are no longer available or currently on hold.
- I want to permanently delete my listing, so that it is completely removed from the marketplace if I decide not to sell.
- I want to message potential buyers through an in-app chat interface, so that we can negotiate terms and coordinate a meetup without exchanging personal phone numbers or emails.
- I want to receive push and in-app notifications when someone messages me or marks interest in my item, so that I can respond quickly and secure a sale.
- I want to rate and review a buyer after a transaction is completed, so that I can inform the rest of the community about my experience with them.

**As a buyer,**
- I want to message a seller directly from an item listing page, so that I can ask questions, make offers, or show interest privately.
- I want to receive notifications when a seller replies to my message, so that I don’t miss out on purchasing the item.
- I want to rate and review a seller after a transaction is completed, so that I can help future buyers gauge the seller's reliability.

---

## Use Cases

### User Story 1 — Sign up and log in

#### Use Case 1: Register an account
- **Actor:** User (not logged in)
- **Main flow:**
  1. User enters name, UofT email, username, password, and campus.
  2. System checks the email is a UofT email and the username is not taken.
  3. System creates the account and shows the home page.
- **Alternative flows:**
  - Email is not a UofT email, or username is taken → show an error and let the user try again.
  - Password is too weak → show password requirements.

#### Use Case 2: Log in
- **Actor:** User (not logged in)
- **Main flow:**
  1. User enters email and password.
  2. System checks the credentials.
  3. System logs the user in and shows the home page.
- **Alternative flows:**
  - Wrong email or password → show "Incorrect email or password".

### User Story 2 — Public profile

#### Use Case 3: View profile
- **Actor:** User
- **Main flow:**
  1. User clicks a seller's name or opens "My profile".
  2. System shows display name, profile picture, bio, campus, rating, and active listings.
- **Alternative flows:**
  - Account no longer exists → show "This user is no longer available".

#### Use Case 4: Edit profile
- **Actor:** Logged-in user
- **Main flow:**
  1. User opens their profile and clicks "Edit".
  2. User changes display name, profile picture, bio, or campus.
  3. System saves the changes and shows the updated profile.
- **Alternative flows:**
  - Invalid input (e.g. empty name) → show an error.

### User Story 3 — Create a listing

#### Use Case 5: Create listing
- **Actor:** Logged-in user (seller)
- **Main flow:**
  1. User opens "New listing" and enters title, description, price, category, condition, campus, and photos (up to 10).
  2. System validates the fields and uploads the photos.
  3. System saves the listing as Active and shows its listing page.
- **Alternative flows:**
  - Required field missing or price invalid → show which field is wrong.
  - More than 10 photos → show "Maximum 10 photos".
  - Photo upload fails → keep the form filled in and ask the user to retry.

### User Story 4 — Search and filter

#### Use Case 6: Search listings by keyword
- **Actor:** User (buyer)
- **Main flow:**
  1. User types a keyword in the search bar.
  2. System shows active listings whose title or description match.
- **Alternative flows:**
  - No matches → show "No listings found".

#### Use Case 7: Filter and sort listings
- **Actor:** User (buyer)
- **Main flow:**
  1. User picks category, price range, and/or condition, and a sort order (newest or price).
  2. System updates the results to show only matching listings in that order.
- **Alternative flows:**
  - Min price is higher than max price → show an error.
  - No matches → show "No listings found".

### User Story 5 — Item page

#### Use Case 8: View listing details
- **Actor:** User (buyer)
- **Main flow:**
  1. User clicks a listing.
  2. System shows photos, title, description, price, condition, campus, status, and the seller's name and rating.
- **Alternative flows:**
  - Listing was deleted → show "This listing is no longer available".

### User Story 6 — Bookmarks

#### Use Case 9: Bookmark a listing
- **Actor:** Logged-in user (buyer)
- **Main flow:**
  1. On a listing page, user clicks "Bookmark".
  2. System adds the listing to the user's bookmarks.
- **Alternative flows:**
  - Already bookmarked → button removes the bookmark instead.

#### Use Case 10: View bookmarked listings
- **Actor:** Logged-in user (buyer)
- **Main flow:**
  1. User opens "My bookmarks".
  2. System shows all saved listings.
- **Alternative flows:**
  - No bookmarks → show "You haven't saved any listings yet".
  - A saved listing was deleted → remove it from the list.

### Future Use Cases (if time allows)
- **User:** log out, reset forgotten password, change password, delete account, verify account, report listing, report profile, rate a user, view conversation
- **Buyer:** start a conversation
- **Seller:** edit listing, delete listing, change listing status, save listing draft, publish listing draft

---

## Minimal Viable Product (MVP)

Each team member owns one user story and leads its use cases: they write most of the interactor and interfaces, controller, presenter, and the UI that triggers each use case and shows the result.

| Team Member | User Story | MVP Use Cases |
|-------------|------------|---------------|
| TODO | 1 — Sign up and log in | UC1 Register an account, UC2 Log in |
| TODO | 2 — Public profile | UC3 View profile, UC4 Edit profile |
| TODO | 3 — Create a listing | UC5 Create listing |
| TODO | 4 — Search and filter | UC6 Search listings, UC7 Filter and sort listings |
| TODO | 5 — Item page | UC8 View listing details |
| TODO | 6 — Bookmarks | UC9 Bookmark a listing, UC10 View bookmarked listings |

---

## Entities

### User
One account can both buy and sell.
- **Fields:** `firstName`, `lastName`, `username` (unique), `email` (UofT only), `password` (hashed), `profileImage`, `bio`, `campus`, `listings: List<Listing>`, `bookmarks: List<Listing>`, `reviews: List<Review>`, `userRating`, `accountCreatedDate`
- **Methods:** getters/setters, `addListing(Listing)`, `addBookmark(Listing)`, `removeBookmark(Listing)`, `addReview(Review)`, `getUserRating()` (average of review stars)

### Listing
- **Fields:** `id` (unique), `name`, `description`, `price`, `condition`, `categories`, `campus`, `status` (Active / Pending / Sold), `seller: User`, `mainImage`, `imageGallery` (max 10), `timePosted`
- **Methods:** getters/setters, `setStatus(Status)`, `addImage(image)` (rejects more than 10)

### Review
The reviewer reviews the reviewee.
- **Fields:** `id` (unique), `reviewer: User`, `reviewee: User`, `listingId`, `starRating` (1–5), `description`, `isItemBought`, `timeReviewed`
- **Methods:** getters

Not in MVP: `Conversation` / `Message` (for chat), `Report`.

---

## API Usage

**API Name:** Supabase (still comparing with Firebase)
**Documentation:** [https://supabase.com/docs](https://supabase.com/docs)
**How it is used in the program:** Sign up / log in, storing users and listings (PostgreSQL), and storing listing images.
**Status:** Not called yet

**API Name:** Imgur
**Documentation:** [https://apidocs.imgur.com/](https://apidocs.imgur.com/)
**How it is used in the program:** Image hosting for listing photos (alternative to Supabase storage).
**Status:** Not called yet

**API Name:** Google Maps Embed
**Documentation:** [https://developers.google.com/maps/documentation/embed/get-started](https://developers.google.com/maps/documentation/embed/get-started)
**How it is used in the program:** Show an approximate meetup area on listing pages.
**Status:** Not called yet

**API Name:** Google Geolocation
**Documentation:** [https://developers.google.com/maps/documentation/geolocation/overview](https://developers.google.com/maps/documentation/geolocation/overview)
**How it is used in the program:** Detect the user's general area (10,000 free requests/month).
**Status:** Not called yet

**API Name:** Nominatim (OpenStreetMap)
**Documentation:** [https://nominatim.org/release-docs/latest/api/Overview/](https://nominatim.org/release-docs/latest/api/Overview/)
**How it is used in the program:** Free alternative for turning a location into a neighbourhood name.
**Status:** Not called yet

Location is shown only at neighbourhood/campus level, never as a street address.

---

## Design Diagrams

### UML Class Diagram
TODO: add a PlantUML class diagram of the entities and use case layers.

### Sequence Diagrams
TODO: add one PlantUML sequence diagram per MVP use case.

---

## How to Run
TODO: add instructions once the Java project is set up (JDK version, how to build, how to start the Swing app).

---

## Notes

**Tech stack**
- Prototype UI: Figma, HTML/CSS
- Final UI: Java Swing
- Backend: Java, following Clean Architecture / SOLID
- Database: PostgreSQL (via Supabase)

**Milestone 1 checklist (due Fri Nov 13)**
- [x] Repo created and shared with all team members
- [ ] Team name picked
- [ ] MVP use cases finalized and each one assigned a lead
- [ ] Core entities implemented in Java
- [ ] Started each MVP use case (interactor + UI)
- [ ] At least one API called successfully

**Workflow:** every PR should update this README if it changes a use case, entity, or API.
