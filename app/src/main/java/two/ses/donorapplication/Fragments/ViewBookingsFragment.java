package two.ses.donorapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import two.ses.donorapplication.Model.Event;
import two.ses.donorapplication.R;

/**
 * Class: ViewBookingsFragment
 * Extends: {@link Fragment}
 * Description:
 * <p>
 * This fragment's job will be that to display bookings, and be able to edit that
 * information (either edit it in this fragment or a new fragment, up to you!)
 * <p>

 */
public class ViewBookingsFragment extends Fragment {
    // Note how Butter Knife also works on Fragments, but here it is a little different
    @BindView(R.id.userTV)
    TextView userTV;
    @BindView(R.id.charityTV)
    TextView charityTV;
    @BindView(R.id.timeTV)
    TextView timeTV;
    @BindView(R.id.dateTV)
    TextView dateTV;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference uEventsRef,eventsRef;
    private FirebaseAuth mAuth;
    private ArrayList<Event> events = new ArrayList<Event>();
    private ArrayList<String> eventIDS = new ArrayList<String>();

    public ViewBookingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle("View Bookings");
        //Get database instances
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uEventsRef = mFirebaseDatabase.getReference("User").child(mAuth.getCurrentUser().getUid()).child("events");
        eventsRef = mFirebaseDatabase.getReference("Events");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_bookings, container, false);

        // Note how we are telling butter knife to bind during the on create view method
        ButterKnife.bind(this, v);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Now that the view has been created, we can use butter knife functionality
        getEventID();
        getEvents();
    }

    private void getEventID() {
        uEventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Boolean eventID = ds.getValue(Boolean.class);
                    int i = 0;
                    if (eventID) {
                        eventIDS.add(ds.getKey());
                        Log.i("Tag", eventIDS.get(i));
                        i++;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getEvents() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Log.i("TAG", ds.getKey());
                    Log.i("TAG", ds.child("date").getValue(String.class));
                    Log.i("TAG", ds.child("time").getValue(String.class));
                    Log.i("TAG", ds.child("userID").getValue(String.class));
                   Event event = new Event(ds.child("date").getValue(String.class),
                           ds.child("time").getValue(String.class),
                           ds.child("userID").getValue(String.class),
                           mAuth.getCurrentUser().getUid());
                   events.add(event);
                   createPacket();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createPacket(){
        int i = 0;
        dateTV.setText(events.get(i).getDate());
        timeTV.setText(events.get(i).getTime());
        userTV.setText(events.get(i).getUserID());
        charityTV.setText(events.get(i).getCharityID());
    }
}
