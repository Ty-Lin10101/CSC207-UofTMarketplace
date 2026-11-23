package interface_adapter.view_bookmarks;

import use_case.view_bookmarks.ViewBookmarksOutputBoundary;
import use_case.view_bookmarks.ViewBookmarksOutputData;

import java.util.ArrayList;

public class ViewBookmarksPresenter implements ViewBookmarksOutputBoundary {
    private final ViewBookmarksViewModel viewModel;

    public ViewBookmarksPresenter(ViewBookmarksViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewBookmarksOutputData outputData) {
        viewModel.setBookmarks(outputData.getBookmarks());

        if (outputData.getBookmarks().isEmpty()) {
            viewModel.setMessage("You haven't saved any listings yet");
        } else {
            viewModel.setMessage("");
        }

        viewModel.notifyViewOfChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setBookmarks(new ArrayList<>());
        viewModel.setMessage(errorMessage);
        viewModel.notifyViewOfChange();
    }
}