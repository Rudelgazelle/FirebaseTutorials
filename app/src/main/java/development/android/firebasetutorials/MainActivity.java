package development.android.firebasetutorials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button mFirebaseBtn;
    private EditText editTextData;

    // Add Firebase Database reference
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseBtn = (Button) findViewById(R.id.btnFirebase);
        editTextData = (EditText) findViewById(R.id.editTextData);

        //Setting the Database reference to the root-URL of the application project
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1 - Create child in the root object of the database
                // 2 - Assign some value to the child object
                mDatabase.child("Name").setValue(editTextData.getText().toString().trim());

            }
        });
    }
}
