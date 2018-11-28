package com.example.michael.proiectunitbv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class SpinnerChoise extends AppCompatActivity  {

    private static final String KEY_PROFILE_IAMGE_PATH = "Path poza profil: @Profile pictures/";
    private static final String KEY_NUME_PRENUME = "Nume si prenume";
    private static final String KEY_FACULTATE = "Facultate";
    private static final String KEY_SPECIALIZARE = "Specializare";
    private static final String KEY_AN = "An";
    private static final String KEY_GRUPA = "Grupa";
    private ImageView mImageView;
    private EditText editTextNume_Prenume;
    private StorageReference storageReference;
    private ProgressBar setupProgress;
    private CircleImageView setupImage;
    private Uri mainImageURI=null;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_choise);


        setupProgress =findViewById(R.id.progressBar2);
        setupImage = findViewById(R.id.setup_image);
        storageReference = FirebaseStorage.getInstance().getReference();
        mImageView = findViewById(R.id.imageView);
        editTextNume_Prenume = findViewById(R.id.edit_text_nume_prenume);
        Button save = findViewById(R.id.save);


        //persmisiuni de citire si scriere pe telefon + deschidere galerie + crop.
        setupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(SpinnerChoise.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(SpinnerChoise.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(SpinnerChoise.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(SpinnerChoise.this);
                    }
                } else {
                    Toast.makeText(SpinnerChoise.this, "Cropping failed, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //string`uri pentru autocomplete
        String[] Facultate = getResources().getStringArray(R.array.Facultate);
        String[] Specializare = getResources().getStringArray(R.array.Specializare);
        String[] An = getResources().getStringArray(R.array.An);
        String[] Grupa = getResources().getStringArray(R.array.Grupa);


        //Field-urile de autoComplete
        final  AutoCompleteTextView facultate = findViewById(R.id.facultate);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, Facultate);
        facultate.setAdapter(adapter);
        //Toast.makeText(this, getString(R.string.facultate), Toast.LENGTH_SHORT).show();

        final  AutoCompleteTextView specializare = findViewById(R.id.specializare);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, Specializare);
        specializare.setAdapter(adapter1);

        final  AutoCompleteTextView an = findViewById(R.id.an);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, An);
        an.setAdapter(adapter2);

        final AutoCompleteTextView grupa = findViewById(R.id.grupa);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, Grupa);
        grupa.setAdapter(adapter3);


        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                int i=0;
              //O mica eroaree = Daca dai register fara poza de prof + nume = crash
                    if(validateNume_Prenume()){
                        i++;

                    }if(validateFacultate()){
                        i++;

                    }if(validateSpecializare()){
                        i++;

                    }if(validateAn()){
                        i++;

                    }if(validateGrupa()){
                        i++;

                    }if((validateNume_Prenume() && validateFacultate() && validateSpecializare() && validateAn() && validateGrupa() && i==5 )) {
                             SalvareDetaliiCont();
                    }else{
                    Toast.makeText(SpinnerChoise.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
            }
        }); //apasand pe buton
    }//on create

    private void SalvareDetaliiCont() {
        final AutoCompleteTextView grupa = findViewById(R.id.grupa);
        final  AutoCompleteTextView facultate = findViewById(R.id.facultate);
        final  AutoCompleteTextView an = findViewById(R.id.an);
        final  AutoCompleteTextView specializare = findViewById(R.id.specializare);

        String nume_presume = editTextNume_Prenume.getText().toString();
        String path_image_profile = editTextNume_Prenume.getText().toString();
        String input_grupa = grupa.getText().toString();
        String input_facultate = facultate.getText().toString();
        String input_specializare = specializare.getText().toString();
        String input_an = an.getText().toString();


        Map<String, Object> note = new HashMap<>();
        note.put(KEY_PROFILE_IAMGE_PATH, path_image_profile);
        note.put(KEY_NUME_PRENUME, nume_presume);
        note.put(KEY_FACULTATE, input_facultate);
        note.put(KEY_SPECIALIZARE, input_specializare);
        note.put(KEY_AN, input_an);
        note.put(KEY_GRUPA, input_grupa);

        db.collection("Utilizatori").document(String.valueOf(nume_presume)).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SpinnerChoise.this, "Note saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SpinnerChoise.this, "Error!", Toast.LENGTH_SHORT).show();

                    }
                });
        UploudImagine();
        openMainActivity();

    }


        public void UploudImagine() {
            String nume_presume = editTextNume_Prenume.getText().toString().trim();
            StorageReference filepath = storageReference.child("Profile pictures").child(nume_presume);

            if(nume_presume.isEmpty()){
                editTextNume_Prenume.setError("Complete name to uploud image profile to database.");
            }else{

                filepath.putFile(mainImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(SpinnerChoise.this, "Done uploading", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }

    private boolean validateFacultate(){
              AutoCompleteTextView facultate = findViewById(R.id.facultate);
              String input_facultate = facultate.getText().toString();

              if(input_facultate.isEmpty()){
                  facultate.setError("Field can`t be empty.");
                    return false;
              }else {
                  facultate.setError(null);
                  return true;
              }
        }

        private boolean validateSpecializare(){
            AutoCompleteTextView specializare = findViewById(R.id.specializare);
            String input_specializare = specializare.getText().toString();

            if(input_specializare.isEmpty()){
                specializare.setError("Field can`t be empty.");
                return false;
            }else {
                specializare.setError(null);
                return true;
            }

        }

         private boolean validateAn(){
             AutoCompleteTextView an = findViewById(R.id.an);
             String input_an = an.getText().toString();

             if(input_an.isEmpty()){
                 an.setError("Field can`t be empty.");
                 return false;
             }else {
                 an.setError(null);
                 return true;
             }

         }

         private boolean validateGrupa(){
             AutoCompleteTextView grupa = findViewById(R.id.grupa);
             String input_grupa = grupa.getText().toString();

             if(input_grupa.isEmpty()){
                 grupa.setError("Field can`t be empty.");
                 return false;
             }else {
                 grupa.setError(null);
                 return true;
             }

        }

        private boolean validateNume_Prenume(){
            String nume_presume = editTextNume_Prenume.getText().toString();
            editTextNume_Prenume = findViewById(R.id.edit_text_nume_prenume);
            if(nume_presume.isEmpty()){
                editTextNume_Prenume.setError("Field can`t be empty.");
                return false;
            }else {
                editTextNume_Prenume.setError(null);
                return true;
            }

        }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI= result.getUri();
                setupImage.setImageURI(mainImageURI);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}//final




