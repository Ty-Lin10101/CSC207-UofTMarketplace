package profile.use_case;

import java.util.Optional;
import profile.entity.PublicProfile;

public interface ProfileReader {
    Optional<PublicProfile> findById(String userId);
}
