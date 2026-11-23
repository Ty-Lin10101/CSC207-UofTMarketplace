package data_access;

import use_case.create_listing.ImageUploadGateway;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Stores the demo session's photos in a local directory. */
public final class LocalImageUploadGateway implements ImageUploadGateway {
    private static final Logger LOGGER = Logger.getLogger(LocalImageUploadGateway.class.getName());
    private final Path directory;

    public LocalImageUploadGateway(Path directory) {
        this.directory = directory.toAbsolutePath().normalize();
    }

    @Override
    public List<String> uploadImages(List<String> imagePaths) throws IOException {
        if (imagePaths.isEmpty()) {
            return List.of();
        }

        Files.createDirectories(directory);
        directory.toFile().deleteOnExit();
        List<String> locations = new ArrayList<>();
        try {
            for (String imagePath : imagePaths) {
                Path source = Path.of(imagePath);
                if (!Files.isRegularFile(source) || !Files.isReadable(source)) {
                    throw new IOException("Photo is missing or unreadable.");
                }
                Path target = Files.createTempFile(directory, "photo-", ".image");
                target.toFile().deleteOnExit();
                locations.add(target.toString());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                validateImage(target);
            }
            return List.copyOf(locations);
        } catch (IOException | InvalidPathException exception) {
            discardImages(locations);
            throw new IOException("Couldn't store the selected listing photos.", exception);
        }
    }

    @Override
    public void discardImages(List<String> imageLocations) {
        for (String location : imageLocations) {
            Path photo = Path.of(location).toAbsolutePath().normalize();
            if (directory.equals(photo.getParent())) {
                try {
                    Files.deleteIfExists(photo);
                } catch (IOException exception) {
                    LOGGER.log(Level.WARNING, "Couldn't remove a discarded listing photo.", exception);
                }
            }
        }
    }

    private static void validateImage(Path source) throws IOException {
        try (ImageInputStream imageInput = ImageIO.createImageInputStream(source.toFile())) {
            if (imageInput == null) {
                throw new IOException("Photo could not be opened.");
            }

            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInput);
            if (!readers.hasNext()) {
                throw new IOException("Photo is not a supported image.");
            }

            ImageReader reader = readers.next();
            try {
                String format = reader.getFormatName().toLowerCase(Locale.ROOT);
                if (!format.equals("jpeg") && !format.equals("png")) {
                    throw new IOException("Choose a JPG or PNG photo.");
                }

                reader.setInput(imageInput, true, true);
                reader.read(0).flush();
            } finally {
                reader.dispose();
            }
        }
    }
}
