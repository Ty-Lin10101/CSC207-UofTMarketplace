package view;

import Entities.ListingImages;
import interface_adapter.create_listing.CreateListingController;
import interface_adapter.create_listing.CreateListingViewModel;
import use_case.create_listing.CreateListingOutputData;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class CreateListingView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CreateListingView.class.getName());
    private static final int MAX_PHOTOS = ListingImages.MAX_PHOTOS;

    private final CreateListingController controller;
    private final JTextField title = new JTextField(30);
    private final JTextArea description = new JTextArea(4, 30);
    private final JTextField price = new JTextField(10);
    private final JComboBox<String> category = new JComboBox<>(new String[]{
            "", "Books", "Electronics", "Furniture", "Clothing", "Bikes & gear", "Home & kitchen", "Other"});
    private final JComboBox<String> condition = new JComboBox<>(new String[]{
            "", "New", "Like new", "Good", "Fair", "Poor"});
    private final JComboBox<String> campus = new JComboBox<>(new String[]{
            "", "St. George", "Mississauga", "Scarborough"});
    private final DefaultListModel<File> selectedPhotos = new DefaultListModel<>();
    private final JList<File> photos = new JList<>(selectedPhotos);
    private final JLabel photoCount = new JLabel();
    private final JLabel error = new JLabel(" ");
    private final JButton addPhotos = new JButton("Add Photos");
    private final JButton removePhoto = new JButton("Remove Selected");
    private final JButton publish = new JButton("Publish");
    private final JButton cancel = new JButton("Cancel");
    private boolean submitting;

    public CreateListingView(CreateListingController controller, CreateListingViewModel viewModel,
                             Runnable showHome, Consumer<CreateListingOutputData> showListing) {
        super(new BorderLayout(0, 12));
        this.controller = controller;
        setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        JPanel form = new JPanel(new GridBagLayout());
        addField(form, 0, "Title", title, title);
        addField(form, 1, "Description", new JScrollPane(description), description);
        addField(form, 2, "Price (CAD)", price, price);
        addField(form, 3, "Category", category, category);
        addField(form, 4, "Condition", condition, condition);
        addField(form, 5, "Campus", campus, campus);
        addField(form, 6, "Photos", buildPhotoPicker(), photos);

        JPanel footer = new JPanel(new BorderLayout(0, 8));
        error.setForeground(new Color(166, 20, 30));
        footer.add(error, BorderLayout.CENTER);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.add(cancel);
        actions.add(publish);
        footer.add(actions, BorderLayout.SOUTH);
        add(new JLabel("Create a Listing"), BorderLayout.NORTH);
        add(new JScrollPane(form), BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        cancel.addActionListener(event -> {
            reset();
            showHome.run();
        });
        publish.addActionListener(event -> publishListing());
        viewModel.addListener(event -> SwingUtilities.invokeLater(() -> {
            if (CreateListingViewModel.ERROR.equals(event.getPropertyName())) {
                error.setText((String) event.getNewValue());
            } else if (CreateListingViewModel.PUBLISHED_LISTING.equals(event.getPropertyName())) {
                reset();
                showListing.accept((CreateListingOutputData) event.getNewValue());
            }
        }));
    }

    private JPanel buildPhotoPicker() {
        photos.setVisibleRowCount(3);
        photos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        photos.setCellRenderer((list, value, index, selected, focused) ->
                renderer.getListCellRendererComponent(list, value.getName(), index, selected, focused));
        photos.addListSelectionListener(event -> updatePhotoControls());
        addPhotos.addActionListener(event -> choosePhotos());
        removePhoto.addActionListener(event -> {
            int selected = photos.getSelectedIndex();
            if (selected >= 0) {
                selectedPhotos.remove(selected);
                error.setText(" ");
                updatePhotoControls();
            }
        });

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        controls.add(addPhotos);
        controls.add(removePhoto);
        controls.add(photoCount);
        JPanel picker = new JPanel(new BorderLayout(0, 6));
        picker.add(new JScrollPane(photos), BorderLayout.CENTER);
        picker.add(controls, BorderLayout.SOUTH);
        updatePhotoControls();
        return picker;
    }

    private void choosePhotos() {
        JFileChooser photoChooser = new JFileChooser();
        photoChooser.setMultiSelectionEnabled(true);
        photoChooser.setAcceptAllFileFilterUsed(false);
        photoChooser.setFileFilter(new FileNameExtensionFilter("JPG and PNG photos", "jpg", "jpeg", "png"));
        if (photoChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Set<File> combined = new LinkedHashSet<>();
        for (int i = 0; i < selectedPhotos.size(); i++) {
            combined.add(selectedPhotos.get(i));
        }
        combined.addAll(Arrays.asList(photoChooser.getSelectedFiles()));
        if (combined.size() > MAX_PHOTOS) {
            error.setText("Maximum " + MAX_PHOTOS + " photos.");
            return;
        }

        selectedPhotos.clear();
        combined.forEach(selectedPhotos::addElement);
        error.setText(" ");
        updatePhotoControls();
    }

    private void publishListing() {
        if (submitting) {
            return;
        }

        List<String> paths = Collections.list(selectedPhotos.elements()).stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());
        String enteredTitle = title.getText();
        String enteredDescription = description.getText();
        String enteredPrice = price.getText();
        String enteredCategory = (String) category.getSelectedItem();
        String enteredCondition = (String) condition.getSelectedItem();
        String enteredCampus = (String) campus.getSelectedItem();

        error.setText("Publishing...");
        setSubmitting(true);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                controller.execute(enteredTitle, enteredDescription, enteredPrice,
                        enteredCategory, enteredCondition, enteredCampus, paths);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    error.setText("Publishing was interrupted. Please try again.");
                } catch (ExecutionException exception) {
                    LOGGER.log(Level.SEVERE, "Unexpected error while publishing a listing.", exception.getCause());
                    error.setText("Couldn't finish publishing the listing. Please try again.");
                } finally {
                    setSubmitting(false);
                }
            }
        }.execute();
    }

    private void setSubmitting(boolean submitting) {
        this.submitting = submitting;
        for (JComponent control : List.of(title, description, price, category, condition, campus,
                photos, addPhotos, publish, cancel)) {
            control.setEnabled(!submitting);
        }
        updatePhotoControls();
    }

    private void updatePhotoControls() {
        photoCount.setText(selectedPhotos.size() + " / " + MAX_PHOTOS + " photos");
        removePhoto.setEnabled(!submitting && photos.getSelectedIndex() >= 0);
    }

    private void reset() {
        title.setText("");
        description.setText("");
        price.setText("");
        category.setSelectedIndex(0);
        condition.setSelectedIndex(0);
        campus.setSelectedIndex(0);
        selectedPhotos.clear();
        error.setText(" ");
        updatePhotoControls();
    }

    private static void addField(JPanel form, int row, String name, JComponent wrapper, JComponent input) {
        JLabel label = new JLabel(name);
        label.setLabelFor(input);
        GridBagConstraints labelPosition = new GridBagConstraints();
        labelPosition.gridy = row;
        labelPosition.anchor = GridBagConstraints.FIRST_LINE_START;
        labelPosition.insets = new Insets(5, 4, 5, 12);
        form.add(label, labelPosition);

        GridBagConstraints fieldPosition = new GridBagConstraints();
        fieldPosition.gridx = 1;
        fieldPosition.gridy = row;
        fieldPosition.weightx = 1;
        fieldPosition.fill = GridBagConstraints.HORIZONTAL;
        fieldPosition.insets = new Insets(5, 4, 5, 4);
        form.add(wrapper, fieldPosition);
    }
}
