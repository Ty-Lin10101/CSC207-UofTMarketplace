package view;

import use_case.create_listing.CreateListingOutputData;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public final class PublishedListingView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int PREVIEW_WIDTH = 420;
    private static final int PREVIEW_HEIGHT = 200;

    private final JTextArea title = readOnlyText(1);
    private final JTextArea details = readOnlyText(6);
    private final JComboBox<String> photoChoice = new JComboBox<>();
    private final JLabel photoPreview = new JLabel("", SwingConstants.CENTER);
    private List<String> photos = List.of();

    public PublishedListingView(Runnable showHome) {
        super(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        photoPreview.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        photoChoice.addActionListener(event -> showPhoto());
        JPanel gallery = new JPanel(new BorderLayout(0, 8));
        gallery.add(photoPreview, BorderLayout.CENTER);
        gallery.add(photoChoice, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.add(new JScrollPane(details), BorderLayout.CENTER);
        content.add(gallery, BorderLayout.SOUTH);
        add(content, BorderLayout.CENTER);

        JButton back = new JButton("Back to Marketplace");
        back.addActionListener(event -> showHome.run());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actions.add(back);
        add(actions, BorderLayout.SOUTH);
    }

    public void setListing(CreateListingOutputData listing) {
        title.setText(listing.getTitle());
        String price = NumberFormat.getCurrencyInstance(Locale.CANADA).format(listing.getPrice());
        details.setText("Listing #" + listing.getListingId() + "  |  " + listing.getStatus() + "  |  " + price
                + "\n" + listing.getCategory() + "  |  " + listing.getCondition() + "  |  " + listing.getCampus()
                + "\nSeller: " + listing.getSellerName() + "\n\n" + listing.getDescription());
        details.setCaretPosition(0);

        photoChoice.removeAllItems();
        photos = listing.getPhotos();
        for (int index = 0; index < photos.size(); index++) {
            photoChoice.addItem("Photo " + (index + 1));
        }
        photoChoice.setVisible(photos.size() > 1);
        showPhoto();
    }

    private void showPhoto() {
        photoPreview.setIcon(null);
        int index = photoChoice.getSelectedIndex();
        if (index < 0) {
            photoPreview.setText("No photos");
            return;
        }

        ImageIcon image = new ImageIcon(photos.get(index));
        if (image.getIconWidth() <= 0 || image.getIconHeight() <= 0) {
            photoPreview.setText("Photo unavailable");
            return;
        }

        double scale = Math.min(1.0, Math.min((double) PREVIEW_WIDTH / image.getIconWidth(),
                (double) PREVIEW_HEIGHT / image.getIconHeight()));
        int width = Math.max(1, (int) (scale * image.getIconWidth()));
        int height = Math.max(1, (int) (scale * image.getIconHeight()));
        photoPreview.setText("");
        photoPreview.setIcon(new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
    }

    private static JTextArea readOnlyText(int rows) {
        JTextArea text = new JTextArea(rows, 40);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        return text;
    }
}
