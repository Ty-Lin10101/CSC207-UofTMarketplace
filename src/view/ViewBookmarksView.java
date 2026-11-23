package view;

import interface_adapter.view_bookmarks.ViewBookmarksController;
import interface_adapter.view_bookmarks.ViewBookmarksViewModel;
import use_case.view_bookmarks.BookmarkedListingData;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.function.IntConsumer;

public class ViewBookmarksView extends JPanel {
    public ViewBookmarksView(ViewBookmarksController controller, ViewBookmarksViewModel model) {
        this(controller, model, null);
    }

    public ViewBookmarksView(ViewBookmarksController controller, ViewBookmarksViewModel model, IntConsumer openListing) {
        super(new BorderLayout());
        JButton refresh = new JButton("My bookmarks");
        JPanel rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        add(refresh, BorderLayout.NORTH);
        add(new JScrollPane(rows), BorderLayout.CENTER);
        refresh.addActionListener(event -> controller.execute());
        Runnable render = () -> {
            rows.removeAll();
            for (BookmarkedListingData item : model.getBookmarks()) {
                String title = String.format("%s — $%.2f", item.getName(), item.getPrice());
                if (openListing == null) {
                    rows.add(new JLabel(title));
                } else {
                    JButton open = new JButton(title);
                    open.addActionListener(e -> openListing.accept(item.getListingId()));
                    rows.add(open);
                }
            }
            rows.add(new JLabel(model.getMessage()));
            rows.revalidate();
            rows.repaint();
        };
        model.addPropertyChangeListener(event -> render.run());
        render.run();
    }
}
