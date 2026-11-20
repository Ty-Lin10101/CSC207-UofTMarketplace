package profile.use_case;

import java.util.Optional;
import profile.entity.ProfileDetails;
import profile.entity.PublicProfile;

public interface ProfileWriter {
    /** Atomically updates only an existing account's editable fields; never creates an account.
     * Returns empty if it has been deleted. Throws ProfileStorageException on storage failure.
     */
    Optional<PublicProfile> updateDetails(String userId, ProfileDetails details);
}
