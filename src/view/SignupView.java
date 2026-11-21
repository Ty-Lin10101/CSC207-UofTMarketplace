package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements PropertyChangeListener {
    private final SignupViewModel signupViewModel;
    private final SignupController signupController;

    private final JTextField firstNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField usernameField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JPasswordField repeatPasswordField = new JPasswordField(15);
    private final JComboBox<String> campusBox = new JComboBox<>(new String[]{"Select a campus...", "St. George",
                                                                            "Scarborough", "Mississauga"});
    private final JLabel errorLabel = new JLabel(" ");

    public SignupView(SignupViewModel signupViewModel, SignupController signupController) {
        this.signupViewModel = signupViewModel;
        this.signupController = signupController;
        this.signupViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("UofT Marketplace — Register");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        addRow("First Name:", firstNameField, gbc, 1);
        addRow("Last Name:", lastNameField, gbc, 2);
        addRow("Username:", usernameField, gbc, 3);
        addRow("UofT Email:", emailField, gbc, 4);
        addRow("Password:", passwordField, gbc, 5);
        addRow("Confirm Password:", repeatPasswordField, gbc, 6);
        addRow("Campus:", campusBox, gbc, 7);

        errorLabel.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        add(errorLabel, gbc);

        JPanel buttonPanel = new JPanel();
        JButton signUpButton = new JButton("Sign Up");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(signUpButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 9;
        add(buttonPanel, gbc);

        signUpButton.addActionListener(e -> signupController.execute(
                firstNameField.getText(),
                lastNameField.getText(),
                usernameField.getText(),
                emailField.getText(),
                new String(passwordField.getPassword()),
                new String(repeatPasswordField.getPassword()),
                (String) campusBox.getSelectedItem()
        ));

        cancelButton.addActionListener(e -> clearFields());
    }

    private void addRow(String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
        errorLabel.setText(" ");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            errorLabel.setText(state.getErrorMessage());
        } else {
            errorLabel.setText(" ");
            JOptionPane.showMessageDialog(this, "Success! Welcome to UofT Marketplace.");
        }
    }
}