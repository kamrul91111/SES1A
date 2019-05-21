package two.ses.donorapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import two.ses.donorapplication.R;

public class SelectTime extends AppCompatActivity {

    Spinner timeSpinner;
    String selectedTime;
    Button selectTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_time);
        final Spinner timeSpinner = findViewById(R.id.timeSpinner);
        final String[] times = new String[]{"9:00am", "10:00am", "11:00am", "12:00pm", "1:00pm", "2:00pm",
                "3:00pm", "4:00pm", "5:00pm"};
        super.onCreate(savedInstanceState);
        selectTimeBtn = (Button) findViewById(R.id.selectTimeBtn);
        ArrayAdapter<String> adaptor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times);
        timeSpinner.setAdapter(adaptor);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = times[position];
                Log.d("selected_Time Variable:", selectedTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                Log.d("Time on click:", selectedTime);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Events");
                myRef.child(id).child("Date").setValue(AddBooking.getDate());
                myRef.child(id).child("Time").setValue(selectedTime);
                myRef.child(id).child("UserID").setValue("UsersID");
                Intent intent = new Intent(SelectTime.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}