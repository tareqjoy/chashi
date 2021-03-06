package com.example.chashi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Login_activity extends AppCompatActivity {
    LinearLayout linearLayout_login, linearLayout_pin;
    EditText editText_phone_no, editText_pin1, editText_pin2, editText_pin3, editText_pin4, editText_pin5, editText_pin6;
    String string_phone_no, string_pin, TAG = "log in page", mVerificationId,phon_no_str;
    TextView textView ;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Animation animation_top_to_down;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        intent = new Intent(this, LandingPage.class);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editText_pin1 = findViewById(R.id.editText2);
        editText_pin2 = findViewById(R.id.editText3);
        editText_pin3 = findViewById(R.id.editText4);
        editText_pin4 = findViewById(R.id.editText5);
        editText_pin5 = findViewById(R.id.editText6);
        editText_pin6 = findViewById(R.id.editText7);
        editText_phone_no = findViewById(R.id.editText);

        textView = findViewById(R.id.login_button);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_button();
            }
        });


        editText_pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //editText_pin1.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_pin1.getText().toString().trim().length() == 1) {
                    editText_pin1.clearFocus();
                    editText_pin2.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_pin2.setText("");
            }
        });

        editText_pin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_pin2.getText().toString().trim().length() == 1) {
                    editText_pin2.clearFocus();
                    editText_pin3.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_pin3.setText("");
            }
        });

        editText_pin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_pin3.getText().toString().trim().length() == 1) {
                    editText_pin3.clearFocus();
                    editText_pin4.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_pin4.setText("");
            }
        });

        editText_pin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_pin4.getText().toString().trim().length() == 1) {
                    editText_pin4.clearFocus();
                    editText_pin5.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_pin5.setText("");
            }
        });

        editText_pin5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_pin5.getText().toString().trim().length() == 1) {
                    editText_pin5.clearFocus();

                    editText_pin6.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_pin6.setText("");
            }
        });


        linearLayout_login = findViewById(R.id.login_side);
        linearLayout_pin = findViewById(R.id.pin_layout);
        animation_top_to_down = AnimationUtils.loadAnimation(this, R.anim.toptodown);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //verification finished
                Toast.makeText(getApplicationContext(), "Verification Completed!", Toast.LENGTH_LONG).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
                // startActivity(intent_map);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "onVerificationFailed: " + e.getMessage());
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    Toast.makeText(getApplicationContext(), "Invalid Phone number!", Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Log.d(TAG, "onVerificationFailed: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong! :(", Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(getApplicationContext(), "Verification Code(OTP) sent!", Toast.LENGTH_LONG).show();

                //update UI
                // [END_EXCLUDE]
            }

        };
    }

    public void go_without_login(View view) {
        // directly go to next intent or activity
        Intent k = new Intent(this, LandingPage.class);
        startActivity(k);
        finish();
    }

    public void login_button() {
        String phone_no = editText_phone_no.getText().toString().trim();

        if (!phone_no.isEmpty()) {
            if (phone_no.charAt(2) != '3' && phone_no.charAt(2) != '7'){
                Toast.makeText(getApplicationContext(), "গ্রামীনফোন নম্বর ব্যাবহার জরে", Toast.LENGTH_SHORT).show();
                return;
            }
            string_phone_no = "+88" + phone_no;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    string_phone_no,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
            linearLayout_login.setVisibility(View.GONE);
            linearLayout_pin.setVisibility(View.VISIBLE);
            linearLayout_pin.setAnimation(animation_top_to_down);
        }
    }

    public void submit_pin(View view) {
        string_pin = editText_pin1.getText().toString().trim() + editText_pin2.getText().toString().trim() + editText_pin3.getText().toString().trim() + editText_pin4.getText().toString().trim() + editText_pin5.getText().toString().trim() + editText_pin6.getText().toString().trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, string_pin);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);

    }

    public void resend_pin(View view) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                string_phone_no,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                mResendToken);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //startActivity(intent_sign_up);
                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            // [END_EXCLUDE]
                            FirebaseUtilClass.getDatabaseReference().child("profile").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.hasChild(string_phone_no)) {
                                        ProfileClass profileClass = new ProfileClass("Anonymous", string_phone_no, "empty");
                                        FirebaseUtilClass.getDatabaseReference().child("profile").child(string_phone_no).setValue(profileClass);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Verification Completed!", Toast.LENGTH_LONG).show();
                            //startActivity(intent_map);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                                Toast.makeText(getApplicationContext(), "Something stopped verification process!", Toast.LENGTH_SHORT).show();
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            //updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
