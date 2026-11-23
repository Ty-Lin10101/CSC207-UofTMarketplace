package profile.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import profile.interface_adapter.ProfileController;
import profile.interface_adapter.ProfileViewModel;
import profile.use_case.ProfileResponse;

/** Swing owns draft text and picture rendering, but no business rules or persistence. */
public final class ProfilePanel extends JPanel {
    private final ProfileViewModel model;
    private final ProfileController controller;
    private final JTextField name = new JTextField();
    private final JTextField picture = new JTextField();
    private final JTextArea bio = new JTextArea(5, 30);
    private final JComboBox<String> campus = new JComboBox<>(
            new String[]{"St. George", "Scarborough", "Mississauga"});
    private final JLabel avatar = new JLabel("No profile picture", SwingConstants.CENTER);
    private final JLabel rating = new JLabel();
    private final JLabel message = new JLabel(" ");
    private final DefaultListModel<String> listings = new DefaultListModel<>();
    private final JButton edit = new JButton("Edit profile");
    private final JButton save = new JButton("Save");
    private final JButton cancel = new JButton("Cancel");
    private final JButton choosePicture = new JButton("Choose picture");
    private final JButton clearPicture = new JButton("Remove picture");
    private final JButton myProfile = new JButton("My profile");
    private final JTextField target = new JTextField(12);
    private final JButton open = new JButton("View seller profile");

    public ProfilePanel(ProfileViewModel model, ProfileController controller) {
        super(new BorderLayout(12, 12));
        this.model = model;
        this.controller = controller;
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navigation.add(myProfile);
        navigation.add(new JLabel("Seller ID:"));
        navigation.add(target);
        navigation.add(open);
        add(navigation, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridLayout(0, 1, 4, 4));
        fields.add(new JLabel("Display name")); fields.add(name);
        fields.add(new JLabel("Profile picture (local file)")); fields.add(picture);
        JPanel pictureButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pictureButtons.add(choosePicture); pictureButtons.add(clearPicture); fields.add(pictureButtons);
        fields.add(new JLabel("Campus")); fields.add(campus);
        bio.setLineWrap(true); bio.setWrapStyleWord(true);
        JPanel details = new JPanel(new BorderLayout(8, 8));
        details.add(fields, BorderLayout.NORTH);
        JScrollPane bioScroll = new JScrollPane(bio);
        bioScroll.setBorder(BorderFactory.createTitledBorder("Bio"));
        details.add(bioScroll, BorderLayout.CENTER);
        details.add(rating, BorderLayout.SOUTH);
        JPanel content = new JPanel(new BorderLayout(12, 12));
        avatar.setPreferredSize(new java.awt.Dimension(150, 150));
        content.add(avatar, BorderLayout.WEST);
        content.add(details, BorderLayout.CENTER);
        JList<String> listingList = new JList<>(listings);
        listingList.setVisibleRowCount(5);
        JScrollPane listingScroll = new JScrollPane(listingList);
        listingScroll.setBorder(BorderFactory.createTitledBorder("Active listings"));
        content.add(listingScroll, BorderLayout.SOUTH);
        add(content, BorderLayout.CENTER);
        JPanel footer = new JPanel(new BorderLayout());
        JPanel actions = new JPanel();
        actions.add(edit); actions.add(save); actions.add(cancel);
        footer.add(actions, BorderLayout.NORTH); footer.add(message, BorderLayout.SOUTH);
        add(footer, BorderLayout.SOUTH);

        myProfile.addActionListener(event -> controller.myProfile());
        open.addActionListener(event -> controller.viewProfile(target.getText().trim()));
        edit.addActionListener(event -> controller.edit(model.getProfile().userId));
        cancel.addActionListener(event -> controller.cancel(model.getProfile().userId));
        save.addActionListener(event -> controller.save(model.getProfile().userId, name.getText(),
                picture.getText(), bio.getText(), (String) campus.getSelectedItem()));
        choosePicture.addActionListener(event -> choosePicture());
        clearPicture.addActionListener(event -> { picture.setText(""); showPicture(""); });
        model.addListener(event -> {
            if ("profile".equals(event.getPropertyName())) { render(); }
            message.setText(model.getMessage().isEmpty() ? " " : model.getMessage());
        });
        setEditing(false);
        edit.setEnabled(false);
    }

    private void render() {
        ProfileResponse profile = model.getProfile();
        name.setText(profile.displayName); picture.setText(profile.picture);
        bio.setText(profile.bio); campus.setSelectedItem(profile.campus);
        rating.setText(profile.rating == 0 ? "No ratings yet"
                : String.format(Locale.CANADA, "Rating: %.1f / 5", profile.rating));
        listings.clear();
        for (ProfileResponse.ListingData item : profile.listings) {
            listings.addElement(String.format(Locale.CANADA, "%s — $%.2f", item.title, item.price));
        }
        if (listings.isEmpty()) { listings.addElement("No active listings."); }
        setEditing(model.isEditing());
        edit.setEnabled(profile.editable && !model.isEditing());
        showPicture(profile.picture);
    }

    private void setEditing(boolean editing) {
        name.setEditable(editing); bio.setEditable(editing); campus.setEnabled(editing);
        picture.setEditable(false);
        choosePicture.setEnabled(editing); clearPicture.setEnabled(editing);
        save.setVisible(editing); cancel.setVisible(editing);
        myProfile.setEnabled(!editing); open.setEnabled(!editing); target.setEnabled(!editing);
    }

    private void choosePicture() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images (PNG, JPG, GIF)", "png", "jpg", "jpeg", "gif"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.isFile() || file.length() > 5 * 1024 * 1024) {
                message.setText("Choose an image file of at most 5 MB.");
                return;
            }
            try {
                BufferedImage image = ImageIO.read(file);
                if (image == null) { throw new IOException("Unsupported image"); }
                picture.setText(file.getAbsolutePath());
                setAvatar(image);
                message.setText("Picture selected. Choose Save to keep it.");
            } catch (IOException exception) {
                message.setText("Unable to read this image. Choose another file.");
            }
        }
    }

    private void showPicture(String path) {
        avatar.setIcon(null);
        avatar.setText(path.isEmpty() ? "No profile picture" : "Picture unavailable");
        if (!path.isEmpty()) {
            File file = new File(path);
            if (file.isFile() && file.length() <= 5 * 1024 * 1024) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) { setAvatar(image); }
                } catch (IOException exception) {
                    // Missing/unreadable images do not prevent viewing or editing a profile.
                }
            }
        }
    }

    private void setAvatar(BufferedImage image) {
        double scale = Math.min(140.0 / image.getWidth(), 140.0 / image.getHeight());
        avatar.setText("");
        avatar.setIcon(new ImageIcon(image.getScaledInstance(Math.max(1, (int) (image.getWidth() * scale)),
                Math.max(1, (int) (image.getHeight() * scale)), Image.SCALE_SMOOTH)));
    }
}
