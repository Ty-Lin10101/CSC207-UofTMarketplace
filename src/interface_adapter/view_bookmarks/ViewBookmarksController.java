package interface_adapter.view_bookmarks;

import use_case.view_bookmarks.ViewBookmarksInputBoundary;

public class ViewBookmarksController {
    private final ViewBookmarksInputBoundary interactor;

    public ViewBookmarksController(ViewBookmarksInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}