public class searchListingsController {
    private final searchListingInputBoundary searchListingsInteractor;

    public searchListingsController(searchListingInputBoundary searchListingsInteractor) {
        this.searchListingsInteractor = searchListingsInteractor;
    }

    public searchListingOutputData execute(String userInput) {
        searchListingInputData inputData = new searchListingInputData(userInput);
        return searchListingsInteractor.execute(inputData);
    }
}