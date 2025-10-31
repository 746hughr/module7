package aydin.firebasedemo;

import java.io.IOException;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SecondaryController {
    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField passPF;

    @FXML
    private void switchToPrimary() throws IOException {
        DemoApp.setRoot("primary");
    }

    @FXML
    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(emailTF.getText())
                .setEmailVerified(false)
                .setPassword(passPF.getText())
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            return false;
        }

    }

    @FXML
    public void login() {
        // Check if textboxes are empty first.
        if (emailTF.getText().trim().isEmpty() || passPF.getText().trim().isEmpty()) {
            System.out.println("Email and/or Passwords fields are empty.");
            return;
        }

        // Check to make sure that the email textbox matches an account and change to primary.fxml
        try {
            UserRecord userRecord = DemoApp.fauth.getUserByEmail(emailTF.getText());
            switchToPrimary();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
