package com.spikeysanju98gmail.shoppinglist.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spikeysanju98gmail.shoppinglist.ItemListActivity;
import com.spikeysanju98gmail.shoppinglist.R;
import com.spikeysanju98gmail.shoppinglist.TodoItems;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemCatalogFragment extends Fragment {

    private DatabaseReference mData;
    private FirebaseRecyclerAdapter todoAdapter;
    private RecyclerView mTodoRV;
    private EditText edTitle;
    private ImageButton addImage;
    private StorageReference mStorage;
    private Button uploadBtn;
    Uri saveuri;
    private static final int GALLERY_REQUEST = 22;
    View v;
    public ItemCatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.activity_main, container, false);


        mData = FirebaseDatabase.getInstance().getReference().child("Todolist");
        mStorage = FirebaseStorage.getInstance().getReference().child("Category_Images");

        mTodoRV = (RecyclerView)v.findViewById(R.id.todoRV);


        LinearLayoutManager mLayout= new LinearLayoutManager(getActivity());
        mTodoRV.setLayoutManager(mLayout);
        mLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mTodoRV.setNestedScrollingEnabled(false);


        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploadTodo();

            }
        });

        loadTodoList();



        return v;

    }

    private void uploadTodo() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Add Title");
        alert.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_todo_popup,null);

        edTitle = (EditText)view.findViewById(R.id.edTitle);
        uploadBtn = (Button)view.findViewById(R.id.uploadBtn);
        addImage = (ImageButton)view.findViewById(R.id.addCategoryImage);



        alert.setView(view);
        alert.setIcon(R.drawable.ic_notes);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });



        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadTodoCategory();
            }
        });

        alert.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();


            }
        });
//            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    dialog.dismiss();
//                }
//
//
//
//
//            });



        alert.show();



    }



    private void selectImage() {
        Intent select = new Intent();
        select.setType("image/*");
        select.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(select,"Select Image"),GALLERY_REQUEST);

    }



    private void uploadTodoCategory() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Adding New Item");
        dialog.show();




        final String title = edTitle.getText().toString().trim();




        if (!TextUtils.isEmpty(title) && saveuri !=null){

            String randomUID = UUID.randomUUID().toString();


            StorageReference filepath = mStorage.child("Category_Images" + randomUID).child(saveuri.getLastPathSegment());

            filepath.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadURL = taskSnapshot.getDownloadUrl();

                    DatabaseReference newCategory = mData.push();
                    newCategory.child("image").setValue(downloadURL.toString());
                    newCategory.child("title").setValue(title);

                    dialog.dismiss();

                }
            });



        }



    }


    private void loadTodoList() {
        todoAdapter = new FirebaseRecyclerAdapter<TodoItems, ItemCatalogFragment.TodoViewHolder>(

                TodoItems.class,
                R.layout.todo_main,
                ItemCatalogFragment.TodoViewHolder.class,
                mData
        ) {
            @Override
            protected void populateViewHolder(TodoViewHolder viewHolder, TodoItems model, final int position) {
                Toast.makeText(getActivity(), ""+ model.getTitle(), Toast.LENGTH_SHORT).show();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(getActivity(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),ItemListActivity.class);
                        intent.putExtra("listID",todoAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }


        };
        todoAdapter.notifyDataSetChanged();
        mTodoRV.setAdapter(todoAdapter);
    }




    public static class TodoViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TodoViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title){

            TextView titleTv = (TextView)mView.findViewById(R.id.titleTV);
            titleTv.setText(title);
        }
        public void setImage(Context ctx, String image){

            ImageView categoryImage = (ImageView) mView.findViewById(R.id.categoryImage);
            categoryImage.setClipToOutline(true);
            Picasso.with(ctx).load(image).into(categoryImage);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null){



            saveuri = data.getData();

            CropImage.activity(saveuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(getActivity());

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                addImage.setImageURI(resultUri);
                saveuri = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}


