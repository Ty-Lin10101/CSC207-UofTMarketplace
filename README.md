# UofT Marketplace

CSC207 Fall 2026 Group Project

## Team Information

### Team Members
- Owen Zhang
- Houssam Kadri (GitHub: House-House)
- Jeffery Chan (GitHub: jycxn)
- Millie Bay (GitHub: millieb-code)
- Tyler Lin (GitHub: Ty-Lin10101)
- Tony Lin (GitHub: ToniSucks)

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

#### Use Case 1: Register an Account
- **Name:** Register an Account
- **Primary Actor:** User
- **Precondition:** The user does not have an account and is not logged in.
- **Trigger:** The user chooses to sign up.
- **Main Success Scenario:**
  1. User enters their name, UofT email, username, password, and campus.
  2. System checks that the email is a UofT email and the username is not taken.
  3. System creates the account.
  4. System logs the user in and presents the home page.
- **Extensions:**
  - **1a.** User cancels → no account is created → use case ends.
  - **2a.** Email is not a UofT email → system shows an error → user re-enters email (back to step 1).
  - **2b.** Username or email is already taken → system shows an error → back to step 1.
  - **2c.** Password is too weak → system shows the password requirements → back to step 1.
- **Success Outcome:** A new account exists and the user is logged in.

#### Use Case 2: Log In
- **Name:** Log In
- **Primary Actor:** User
- **Precondition:** The user has an account and is not logged in.
- **Trigger:** The user chooses to log in.
- **Main Success Scenario:**
  1. User enters their email and password.
  2. System checks the credentials.
  3. System logs the user in.
  4. System presents the home page.
- **Extensions:**
  - **1a.** User cancels → user stays logged out → use case ends.
  - **2a.** Email or password is wrong → system shows "Incorrect email or password" → back to step 1.
- **Success Outcome:** The user is logged in.

### User Story 2 — Public profile

#### Use Case 3: View Profile
- **Name:** View Profile
- **Primary Actor:** User
- **Precondition:** The profile to view belongs to an existing account.
- **Trigger:** The user clicks a seller's name or opens "My profile".
- **Main Success Scenario:**
  1. User chooses the profile to view.
  2. System retrieves the profile.
  3. System presents the display name, profile picture, bio, campus, rating, and active listings.
- **Extensions:**
  - **2a.** The account no longer exists → system shows "This user is no longer available" → use case ends.
- **Success Outcome:** The user sees the chosen profile.

#### Use Case 4: Edit Profile
- **Name:** Edit Profile
- **Primary Actor:** User
- **Precondition:** The user is logged in.
- **Trigger:** The user chooses to edit their profile.
- **Main Success Scenario:**
  1. User opens their profile and chooses "Edit".
  2. System provides the profile's current information.
  3. User provides updated display name, profile picture, bio, or campus.
  4. System updates the profile.
  5. System presents the updated profile.
- **Extensions:**
  - **3a.** User cancels → profile remains unchanged → use case ends.
  - **4a.** Input is invalid (e.g. empty display name) → system shows an error → back to step 3.
- **Success Outcome:** The profile contains the updated information.

### User Story 3 — Create a listing

#### Use Case 5: Create Listing
- **Name:** Create Listing
- **Primary Actor:** User (seller)
- **Precondition:** The user is logged in.
- **Trigger:** The user chooses to create a new listing.
- **Main Success Scenario:**
  1. System provides an empty listing form.
  2. User provides a title, description, price, category, condition, campus, and up to 10 photos.
  3. System validates the information and uploads the photos.
  4. System saves the listing with status Active.
  5. System presents the new listing page.
- **Extensions:**
  - **2a.** User cancels → no listing is created → use case ends.
  - **3a.** A required field is missing or the price is invalid → system shows which field is wrong → back to step 2.
  - **3b.** More than 10 photos → system shows "Maximum 10 photos" → back to step 2.
  - **3c.** Photo upload fails → system keeps the form filled in and asks the user to retry → back to step 2.
- **Success Outcome:** A new Active listing exists and is visible to other users.

### User Story 4 — Search and filter

#### Use Case 6: Search Listings by Keyword
- **Name:** Search Listings by Keyword
- **Primary Actor:** User (buyer)
- **Precondition:** None.
- **Trigger:** The user types a keyword in the search bar and submits.
- **Main Success Scenario:**
  1. User provides a keyword.
  2. System finds Active listings whose title or description match the keyword.
  3. System presents the matching listings.
- **Extensions:**
  - **1a.** Keyword is empty → system presents all Active listings.
  - **2a.** No listings match → system shows "No listings found" → use case ends.
- **Success Outcome:** The user sees the listings that match the keyword.

#### Use Case 7: Filter and Sort Listings
- **Name:** Filter and Sort Listings
- **Primary Actor:** User (buyer)
- **Precondition:** The user is viewing a list of listings.
- **Trigger:** The user changes a filter or the sort order.
- **Main Success Scenario:**
  1. User chooses a category, price range, and/or condition, and a sort order (newest or price).
  2. System finds the listings that match the filters.
  3. System sorts them in the chosen order.
  4. System presents the updated results.
- **Extensions:**
  - **1a.** Minimum price is higher than maximum price → system shows an error → back to step 1.
  - **2a.** No listings match → system shows "No listings found" → use case ends.
- **Success Outcome:** The user sees only listings that match the filters, in the chosen order.

### User Story 5 — Item page

#### Use Case 8: View Listing Details
- **Name:** View Listing Details
- **Primary Actor:** User (buyer)
- **Precondition:** The listing exists.
- **Trigger:** The user clicks a listing.
- **Main Success Scenario:**
  1. User chooses a listing.
  2. System retrieves the listing and its seller.
  3. System presents the photos, title, description, price, condition, campus, status, and the seller's name and rating.
- **Extensions:**
  - **2a.** The listing was deleted → system shows "This listing is no longer available" → use case ends.
- **Success Outcome:** The user sees the full details of the listing.

### User Story 6 — Bookmarks

#### Use Case 9: Bookmark a Listing
- **Name:** Bookmark a Listing
- **Primary Actor:** User (buyer)
- **Precondition:** The user is logged in and viewing a listing.
- **Trigger:** The user clicks "Bookmark" on a listing page.
- **Main Success Scenario:**
  1. User chooses to bookmark the listing.
  2. System adds the listing to the user's bookmarks.
  3. System shows the listing as bookmarked.
- **Extensions:**
  - **2a.** The listing is already bookmarked → system removes it from bookmarks → system shows it as not bookmarked → use case ends.
- **Success Outcome:** The listing is in the user's bookmarks.

#### Use Case 10: View Bookmarked Listings
- **Name:** View Bookmarked Listings
- **Primary Actor:** User (buyer)
- **Precondition:** The user is logged in.
- **Trigger:** The user opens "My bookmarks".
- **Main Success Scenario:**
  1. User chooses to view their bookmarks.
  2. System retrieves the user's bookmarked listings.
  3. System presents the bookmarked listings.
- **Extensions:**
  - **2a.** The user has no bookmarks → system shows "You haven't saved any listings yet" → use case ends.
  - **2b.** A bookmarked listing was deleted → system removes it from the bookmarks and presents the rest.
- **Success Outcome:** The user sees all their saved listings.

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

Note: Location is shown only at neighbourhood/campus level, never as a street address.

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
