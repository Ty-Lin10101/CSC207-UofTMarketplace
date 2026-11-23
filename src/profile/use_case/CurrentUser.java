package profile.use_case;

/** Implement using the authenticated session, never an ID supplied by an edit form. */
public interface CurrentUser {
    String userId();
}
