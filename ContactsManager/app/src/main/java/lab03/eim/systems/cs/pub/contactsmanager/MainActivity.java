package lab03.eim.systems.cs.pub.contactsmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                EditText phoneNumber = findViewById(R.id.phoneNumber);
                phoneNumber.setText(phone);
            } else {
                Toast.makeText(this, "phone number empty", Toast.LENGTH_LONG).show();
            }
        }

        final Button showFields = findViewById(R.id.showAdditionalFields);
        showFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = showFields.getText().toString();
                if (btnText.equals("Show additional fields")) {
                    showFields.setText("Hide additional fields");
                    EditText jobTitle = findViewById(R.id.jobTitle);
                    jobTitle.setVisibility(View.VISIBLE);

                    EditText company = findViewById(R.id.company);
                    company.setVisibility(View.VISIBLE);

                    EditText website = findViewById(R.id.website);
                    website.setVisibility(View.VISIBLE);

                    EditText im = findViewById(R.id.im);
                    im.setVisibility(View.VISIBLE);
                } else if (btnText.equals("Hide additional fields")) {
                    showFields.setText("Show additional fields");
                    EditText jobTitle = findViewById(R.id.jobTitle);
                    jobTitle.setVisibility(View.INVISIBLE);

                    EditText company = findViewById(R.id.company);
                    company.setVisibility(View.INVISIBLE);

                    EditText website = findViewById(R.id.website);
                    website.setVisibility(View.INVISIBLE);

                    EditText im = findViewById(R.id.im);
                    im.setVisibility(View.INVISIBLE);
                }

            }
        });

        final Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        });

        final Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(intent, 5);

                EditText nameTxt = findViewById(R.id.name);
                CharSequence name = nameTxt.getText();

                EditText phoneTxt = findViewById(R.id.phoneNumber);
                CharSequence phone = phoneTxt.getText();

                EditText emailTxt = findViewById(R.id.email);
                CharSequence email = emailTxt.getText();

                EditText addressTxt = findViewById(R.id.address);
                CharSequence address = addressTxt.getText();

                EditText jobTxt = findViewById(R.id.jobTitle);
                CharSequence jobTitle = jobTxt.getText();

                EditText companyTxt = findViewById(R.id.company);
                CharSequence company = companyTxt.getText();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }

                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();

                EditText websiteTxt = findViewById(R.id.website);
                String website = websiteTxt.getText().toString();

                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }

                EditText imTxt = findViewById(R.id.im);
                String im = imTxt.getText().toString();

                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 5:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}