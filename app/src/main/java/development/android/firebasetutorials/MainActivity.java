package development.android.firebasetutorials;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button mFirebaseBtn;
    private EditText editTextName;
    private EditText editTextEmail;

    private TextView textViewName;

    private ListView listViewUsers;

    // Add Firebase Database reference
    private DatabaseReference mDatabase;

    //Define an arraylist to store the multiple user data
    private ArrayList<String> mUserNames = new ArrayList<>();
    // Define a second array to store the key of the object for the onchildchanged event (Key USer 01,02,03 etc.
    private ArrayList<String> mKeys = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseBtn = (Button) findViewById(R.id.btnFirebase);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        textViewName = (TextView) findViewById(R.id.textViewName);




        //Setting the Database reference to root path
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listViewUsers= (ListView) findViewById(R.id.ListViewUsers);

        //Create an Array adapter; three values have to be assigned (Context / Ressource / Source)
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mUserNames);
        //Set the Arrayadapter to the listview
        listViewUsers.setAdapter(arrayAdapter);

        //prepare the database read aktion // Consult Firebase Documentastion to read a list of data
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //get the data from the data snapshot // The Value type will be String.Class
                String value = dataSnapshot.getValue(String.class);

                //append the data (value) to the ListView
                mUserNames.add(value);

                // get the object key within the datasnapshot
                String key = dataSnapshot.getKey();
                mKeys.add(key);

                //refresh data in data array
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                // get a refreshed datasnaphot of the changed object
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();

                //determine on what position (key) the object is changed (inside the Arraylist)
                int index = mKeys.indexOf(key); //now we know the index value in the Array, that would be the same index in the value array
                //set the Value for the specific item (index) in the Listview
                mUserNames.set(index, value);
                //refresh data in data array
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        //dont forget to add ";"
        });

        /*//Setting the Database reference to the Child Object which containes the data that has to be read
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Name");

        //Add an ValueEventlistener to the databasereference to read the data of the set path in "mDatabase"
        //This Method should not be used for huge amount of data to be read
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //when Data is changed or retrieved from database this method will fire

                //Set the value of the retrieved data of the datasnapshot to String "name"
                String name = dataSnapshot.getValue().toString();

                textViewName.setText("Name: " + name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //when Data is being deleted or could not be retrieved from database this method will fire


            }
        //dont forget to include ";"
        });

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting the Database reference to the root-URL of the application project
                mDatabase = FirebaseDatabase.getInstance().getReference();

                // 1 - Create child in the root object of the database
                // 2 - Assign some value to the child object

                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();

                //Create new instance of hashmap to store multiple values
                //< a String, and its value>
                //Sets a String for creating the Child ID and Stores a coresponding value
                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("Name", name);
                dataMap.put("Email",email);

                //push() creates a random child object incl. timestamp, in this case under the root object.
                // the additional Oncompletelistener will check if the data is stored correctly
                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // if the task is finished, show a toast that the data has been stored
                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Data stored...", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Data could not be stored...", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });*/
    }
}
