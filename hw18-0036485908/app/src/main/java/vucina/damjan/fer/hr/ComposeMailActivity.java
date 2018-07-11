package vucina.damjan.fer.hr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity class assigned with the task of providing the user with the
 * ability to send an e-mail on request. Having entered the recipient's
 * mail address as well as a message and its title, the user initiates the
 * process of opening the mail client app and sending the mail.
 *
 * NOTICE: Carbon copy is by default set to {"ana@baotic.org",
 * "marcupic@gmail.com "}.
 *
 * @author Damjan Vučina
 */
public class ComposeMailActivity extends AppCompatActivity {

    /** The regex used for validating the entered mail */
    private static final String MAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";// RFC 5322

    /** The default carbon copy mail addresses */
    private static final String[] MAIL_CC = new String[]{"ana@baotic.org", "marcupic@gmail.com "};

    /** The pattern. */
    private static Pattern pattern = Pattern.compile(MAIL_REGEX);

    /** The recipient's mail address. */
    private String recipient;

    /** The title of the mail. */
    private String title;

    /** The body of the mail. */
    private String message;

    /**
     * Method that delegates to helper method used for setting up the UI of the activity.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        setUp();
    }

    /**
     * Validates the mail's recipient, title and message and initiates
     * the process of sending the mail via installed mail client.
     */
    private void setUp() {
        Button sendMailButton = (Button) findViewById(R.id.sendMailButton);
        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mailInput = (EditText) findViewById(R.id.mailInput);
                EditText titleInput = (EditText) findViewById(R.id.titleInput);
                EditText messageInput = (EditText) findViewById(R.id.messageInput);

                recipient = mailInput.getText().toString();
                title = titleInput.getText().toString();
                message = messageInput.getText().toString();

                MailResult status;
                status = validateMail();
                if (status == MailResult.ABORT) {
                    return;
                }

                status = validateTitleAndMessage();
                if (status == MailResult.ABORT) {
                    return;
                }

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                emailIntent.putExtra(Intent.EXTRA_CC, MAIL_CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("INFO", "Mail successfully sent.");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ComposeMailActivity.this, "No email client installed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Helper method used for validating the title and the body of the mail
     *
     * @return the result of the validation
     */
    private MailResult validateTitleAndMessage() {
        if (title.isEmpty() || message.isEmpty()) {
            invalidInput(getString(R.string.INVALID_PROPERTY));
            return MailResult.ABORT;
        }

        return MailResult.CONTINUE;
    }

    /**
     * Validates the recipient's mail address according to the
     * RFC5322 Internet Message Format
     *
     * @return the mail result
     */
    private MailResult validateMail() {
        Matcher matcher = pattern.matcher(recipient);
        if (!matcher.matches()) {
            invalidInput(getString(R.string.INVALID_MAIL));
            return MailResult.ABORT;
        }

        return MailResult.CONTINUE;
    }

    /**
     * Helper method used for showing a toast pop-up in case of invalid input.
     *
     * @param errorMessage the error message
     */
    private void invalidInput(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
