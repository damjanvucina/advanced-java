package vucina.damjan.fer.hr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity class used for displaying the result of the quotient calculation
 * sent from the LifecycleActivity class.
 *
 * @author Damjan Vučina
 */
public class ShowActivity extends AppCompatActivity {

    /**
     * Method invoked on acivity start-up. Displays the the result of the
     * quotient calculation sent from the LifecycleActivity class.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        String rezultat = getIntent().getExtras().getString("rezultat");

        Toast.makeText(this, rezultat, Toast.LENGTH_LONG).show();

        EditText input = findViewById(R.id.input_text);
        Button btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}