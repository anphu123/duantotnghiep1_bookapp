package com.example.duan1bookapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.PopupMenu;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;
import com.example.duan1bookapp.Constants;
import com.example.duan1bookapp.HttpStatus;
import com.example.duan1bookapp.R;
import com.example.duan1bookapp.models.Customer;
import com.example.duan1bookapp.models.CustomerDataManager;
import com.example.duan1bookapp.retrofit.CustomerApi;
import com.example.duan1bookapp.retrofit.RetrofitService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileEditActivity extends AppCompatActivity {
    private CustomerDataManager customerDataManager;

    private FirebaseAuth firebaseAuth;
    //progress dialog
    private ProgressDialog progressDialog;
    private static final int REQUEST_PICK_IMAGE = 1;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private Button uploadButton;
    private ImageButton imageButton;
    private ShapeableImageView shapeableImageView;
    private static final String TAG = "PROFILE_EDIT_TAG";

    private Uri imageUrl = null;

    private String name_url="";
    private String uploadedImageUrl= "";
    private Customer customer = null;
    RetrofitService retrofitService = new RetrofitService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        customerDataManager = new CustomerDataManager(this);
        customer = customerDataManager.getUser();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(true);//don't dismiss while clicking outside of progress dialog
        //setup firebase auth
        firebaseAuth=FirebaseAuth.getInstance();
        initUI(customer);

        imageButton.setOnClickListener(v -> onBackPressed());
//        shapeableImageView.setOnClickListener(v -> showImageAttachMenu());
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update(customer);
            }
        });

    }
    private void initUI(Customer customer){

        imageButton = findViewById(R.id.Edit_cs_backBtn);
        editText1 = findViewById(R.id.cs_update_edit_name);
        editText2 = findViewById(R.id.cs_update_edit_password);
        editText3 = findViewById(R.id.cs_update_edit_password_confirm);
        editText4 = findViewById(R.id.cs_update_edit_birthday);
        editText5 = findViewById(R.id.cs_update_edit_Street);
        editText6 = findViewById(R.id.cs_update_edit_City);
        uploadButton = findViewById(R.id.CS_updateBtn);
        shapeableImageView = (ShapeableImageView) findViewById(R.id.profile_edit_imageView);

        editText1.setText(customer.getCustomerName());
        editText2.setText(customer.getCustomerPassword());
        editText3.setText(customer.getCustomerPassword());
        editText4.setText(customer.getCustomerbirthDate());
        editText5.setText(customer.getAddress().getStreet());
        editText6.setText(customer.getAddress().getCity());
        if(!customer.getAvatar_url().isEmpty()){
            Glide.with(ProfileEditActivity.this).load(Constants.URL_IMAGE+ customer.getAvatar_url()).timeout(500).into(shapeableImageView);
        }
    }

    private void update(Customer customer) {
        String newName = editText1.getText().toString();
        String newPassword = editText2.getText().toString();
        String newPasswordConfirm = editText3.getText().toString();
        String newBirthDate = editText4.getText().toString();
        String newStreet = editText5.getText().toString();
        String newCity = editText6.getText().toString();
        int idcustomer = customer.getId();
        int idddress = customer.getAddress().getId();
//        int length = uploadedImageUrl.length();
//        String last36Chars = uploadedImageUrl.substring(length - 36);
//        Log.d(TAG, "last36Chars: " +last36Chars);
        if (!newPassword.equals(newPasswordConfirm)) {
            Toast.makeText(this, "Save failded! password khong giong nhau", Toast.LENGTH_SHORT).show();
            return;
        }
        CustomerApi customerApi = retrofitService.getRetrofit().create(CustomerApi.class);
        customerApi.update(idcustomer,newName,newPassword,newBirthDate,"2Fuser_1.png?alt=media&token=c6c94724-bd80-4f26-82a8-5569b919f715",idddress,newStreet,newCity)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            int statusCode = response.code(); // Retrieve the HTTP status code
                            if (statusCode == HttpStatus.OK) {
                                Toast.makeText(getApplication(), "Cap Nhap Thanh Cong", Toast.LENGTH_SHORT).show();
                                editText1.setText(newName);
                                editText2.setText(newPassword);
                                editText3.setText(newPassword);
                                editText4.setText(newBirthDate);
                                editText5.setText(newStreet);
                                editText6.setText(newCity);
                            } else {
                                Toast.makeText(getApplication(), "Cap Nhap That Bai,client error", Toast.LENGTH_SHORT).show();
                                // Handle other success cases based on different status codes if necessary
                            }
                        } else {
                            Toast.makeText(getApplication(), "Cap Nhap That Bai,server error, ", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("Error occurred", "Throwable: " + t.toString()); // Log throwable as string
                        Log.e("Error occurred", "StackTrace: " + Log.getStackTraceString(t)); // Log stack trace
                    }
                });
    }

//
//    private void showImageAttachMenu() {
//        //init/setup popup menu
//        PopupMenu popupMenu = new PopupMenu(this, shapeableImageView);
//        popupMenu.getMenu().add(Menu.NONE, 0, 0, "Camera");
//        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Gallery");
//
//        popupMenu.show();
//        //handle menu item click
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                //get id of item clicked
//                int which = item.getItemId();
//                if (which == 0) {
//                    //camera clicked
//                    pickImageCamera();
//                } else if (which == 1) {
//                    //gallery clicked
//                    pickImageGallery();
//                }
//                return false;
//            }
//        });
//    }
//
//    private void pickImageCamera() {
//        //intent to pick images from camera
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "New Pick");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Image Description");
//        imageUrl = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
//        cameraActivityResultLauncher.launch(intent);
//    }
//
//    private void pickImageGallery() {
//        //intent to pick images from Gallery
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        galleryActivityResultLauncher.launch(intent);
//    }
//
//    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    //user to handle result of camera intent
//                    //get uri of image
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Log.d(TAG, "onActivityResult: Picked From camera" + imageUrl);
//                        Intent data = result.getData();//no need here as in camera case we already have images in imageUri variable
//                        shapeableImageView.setImageURI(imageUrl);
//
//                    } else {
//                        Toast.makeText(ProfileEditActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );
//    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    //user to handle result of camera intent
//                    //get uri of image
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Log.d(TAG, "onActivityResult: " + imageUrl);
//                        Intent data = result.getData();
//                        imageUrl = data.getData();
//                        Log.d(TAG, "onActivityResult: " + imageUrl);
//                        shapeableImageView.setImageURI(imageUrl);
//                        uploadImage();
//                    } else {
//                        Toast.makeText(ProfileEditActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );
//
//    private void uploadImage() {
//        Log.d(TAG, "uploadImage: Uploading profile image...");
//        progressDialog.setMessage("Updating profile image");
//        progressDialog.show();
//
//        //image path and name,use uid to replace previous
//        String filePathAndName = "ProfileImages/" + firebaseAuth.getUid();
//
//        //storage reference
//        StorageReference reference = FirebaseStorage.getInstance().getReference(filePathAndName);
//        reference.putFile(imageUrl)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Log.d(TAG, "onSuccess: Profile image uploaded");
//                        Log.d(TAG, "onSuccess: Getting url of uploaded image");
//                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                        while (!uriTask.isSuccessful()) ;
//                        uploadedImageUrl = "" + uriTask.getResult();
//
//                        Log.d(TAG, "onSuccess: Uploaded Image Url" + uploadedImageUrl);
//                        progressDialog.dismiss();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: Failed to upload image due to" + e.getMessage());
//                        progressDialog.dismiss();
//                    }
//                });
//    }
}