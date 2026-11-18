package profile.data_access;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import profile.entity.ProfileDetails;
import profile.entity.PublicProfile;
import profile.use_case.ProfileReader;
import profile.use_case.ProfileWriter;

/** Demo adapter. Replace with a shared-account/Supabase adapter at composition time. */
public final class InMemoryProfileRepository implements ProfileReader, ProfileWriter {
    private final Map<String, PublicProfile> profiles = new HashMap<>();

    public InMemoryProfileRepository(Collection<PublicProfile> initialProfiles) {
        for (PublicProfile profile : initialProfiles) {
            profiles.put(profile.userId, profile);
        }
    }

    public synchronized Optional<PublicProfile> findById(String id) {
        return Optional.ofNullable(profiles.get(id));
    }

    public synchronized Optional<PublicProfile> updateDetails(String id, ProfileDetails details) {
        PublicProfile current = profiles.get(id);
        if (current == null) {
            return Optional.empty();
        }
        PublicProfile updated = current.withDetails(details);
        profiles.put(id, updated);
        return Optional.of(updated);
    }
}
