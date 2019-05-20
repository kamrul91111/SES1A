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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import two.ses.donorapplication.R;

public class SelectTime extends AppCompatActivity {

    Spinner timeSpinner;
    String selectedTime;
    Button selectTimeBtn;
    private FirebaseAuth mAuth;
    String placeHolderCharityId;
    private DatabaseReference uEventsRef;
    private String[] bookedTimes;
    String charityId = "test";

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
               // if(!isBooked()){
                    String eventId = UUID.randomUUID().toString();
                    String userId;
                    Log.d("Time on click:", selectedTime);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference eventsRef = database.getReference("Events");
                    DatabaseReference userRef = database.getReference("User");

                    eventsRef.child(eventId).child("Charity ID").setValue(placeHolderCharityId);
                    eventsRef.child(eventId).child("Date").setValue(AddBooking.getDate());
                    eventsRef.child(eventId).child("Time").setValue(selectedTime);
                    eventsRef.child(eventId).child("UserID").setValue(userId);

                    userRef.child(userId).child(eventId).setValue("true");

                    Intent intent = new Intent(SelectTime.this, LoginActivity.class);
                    startActivity(intent);
                }
           // }
        });



    }

//     private boolean isBooked() {
//     String charityId = "test"; //Need to change this to get passed in from search
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userRef = database.getReference("User");
//
//        userRef.child(charityId).child("events");
//
//        return false;
//     }
//
//    private void getBookedTimes() {
//        uEventsRef.addValueEventListener(new ValueEventListener() {
//           @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                for (DataSnapshot ds: dataSnapshot.child("users").child(charityId).child("events").getChildren()){
//                    Boolean eventID = ds.getValue(Boolean.class);
//                    int i = 0;
//                    if (eventID) {
//                        eventIDS.add(ds.getKey());
//                        Log.i("Tag", eventIDS.get(i));
//                        i++;
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
