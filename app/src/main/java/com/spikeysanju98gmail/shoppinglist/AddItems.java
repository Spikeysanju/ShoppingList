package com.spikeysanju98gmail.shoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spikeysanju98gmail.shoppinglist.fragments.ItemCatalogFragment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class AddItems extends AppCompatActivity {

    private ImageButton itemImage;
    private EditText itemTitle;
    private EditText itemQuantity;
    private Button uploadBtn;
    private DatabaseReference mTask;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private String menuID;
    String listID="";

    Uri saveURI;
    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        itemImage = (ImageButton)findViewById(R.id.itemImg);
        itemTitle = (EditText)findViewById(R.id.itemTitleTv);
        itemQuantity = (EditText)findViewById(R.id.itemQuantityTv);
        uploadBtn = (Button)findViewById(R.id.uploadBtn);


        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        mTask = FirebaseDatabase.getInstance().getReference().child("Items");
        mStorage = FirebaseStorage.getInstance().getReference().child("Item_Images");


        if (getIntent()!=null){
            listID = getIntent().getStringExtra("listID");
            if (!listID.isEmpty() && listID!=null){
                uploadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadItem(listID);
                    }
                });




            }
        }



    }


    private void uploadItem(final String listID) {
        final String item = itemTitle.getText().toString();
        final String quantity = itemQuantity.getText().toString();

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Uploading Task...");
        mProgress.show();

        if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(quantity) && saveURI != null){

            String randomUID = UUID.randomUUID().toString();

            StorageReference filepath = mStorage.child("Item_Images" +randomUID).child(saveURI.getLastPathSegment());
            filepath.putFile(saveURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadURL = taskSnapshot.getDownloadUrl();

                    DatabaseReference newTask = mTask.push();
                    newTask.child("item").setValue(item);
                    newTask.child("quantity").setValue(quantity);
                    newTask.child("image").setValue(downloadURL.toString());
                    newTask.child("menuID").setValue(listID);
                    mProgress.dismiss();

                    startActivity(new Intent(AddItems.this,ItemCatalogFragment.class));


                }
            });

        }
    }

    private void selectImage() {
        Intent select = new Intent();
        select.setType("image/*");
        select.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(select,"Select Image"),GALLERY_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null){



            saveURI= data.getData();

            CropImage.activity(saveURI)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                itemImage.setImageURI(resultUri);
                saveURI = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
