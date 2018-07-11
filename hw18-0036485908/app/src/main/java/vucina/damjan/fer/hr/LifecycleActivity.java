package vucina.damjan.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Main activity class shown on app start-up. Provides the user with the ability
 * of calculating the quotient of two entered numbers as well as redirecting to the
 * activity used for sending a mail.
 *
 * @author Damjan Vučina
 */
public class LifecycleActivity extends AppCompatActivity {

    /** The quotient result label. */
    private TextView labelResult;

    /**
     * Method invoked on activity start-up. Sets up the UI and processes the user's request
     * varying from performing quotient calculation and sending the result of the operation
     * to another activity to sending emails.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        Log.d("Lifecycle", "Pozvan je onCreate");
        labelResult = findViewById(R.id.label_result);

        Button btnCalculate = (Button) findViewById(R.id.btn_calculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputFirst = findViewById(R.id.input_first);
                EditText inputSecond = findViewById(R.id.input_second);
                labelResult = findViewById(R.id.label_result);

                String first = inputFirst.getText().toString();
                String second = inputSecond.getText().toString();

                double firstNumber = 0;
                try{
                    firstNumber = Double.parseDouble(first);
                } catch (NumberFormatException e){
                }

                double secondNumber = 0;
                try{
                    secondNumber = Double.parseDouble(second);
                } catch (NumberFormatException e){
                }

                if(secondNumber != 0){
                    labelResult.setText(String.valueOf(firstNumber/secondNumber));
                } else{
                    labelResult.setText("Nedozvoljena operacija");
                }
            }
        });

        Button btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifecycleActivity.this, ShowActivity.class);
                Bundle data = new Bundle();
                data.putString("rezultat", labelResult.getText().toString());
                intent.putExtras(data);
                startActivity(intent);
            }
        });

        Button composeMailButton = (Button) findViewById(R.id.composeMailButton);
        composeMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifecycleActivity.this, ComposeMailActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Helper method invoked on app pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "Pozvan je onPause");
    }

    /**
     * Helper method invoked on app resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "Pozvan je onResume");
    }
}
