package interface_adapter.signup;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

    public class SignupViewModel {
        public static final String VIEW_NAME = "sign up";
        private SignupState state = new SignupState();
        private final PropertyChangeSupport support = new PropertyChangeSupport(this);

        public SignupState getState() { return state; }
        public void setState(SignupState state) { this.state = state; }

        public void firePropertyChanged() {
            support.firePropertyChange("state", null, this.state);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }
    }
}