package hect.preciosapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hect.preciosapp.Models.FirebaseReferences;
import hect.preciosapp.Models.Product;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name_product;
    private String category_product;
    private EditText price_product;
    private Button btnAdd;
    private Spinner spn;
    private ProgressDialog mProgressDialog;

    private final int PHOTO_CODE = 100;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        spn = findViewById(R.id.spn_category);
        spn.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Bebidas");
        categories.add("Snacks");
        categories.add("Embutidos");
        categories.add("Galletas y Dulces");
        categories.add("Útiles Escolares");
        categories.add("Uso personal");
        categories.add("Consumo personal");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);
        name_product = findViewById(R.id.edt_name);
        price_product = findViewById(R.id.edt_price);
        mProgressDialog = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        btnAdd = findViewById(R.id.btnAddProduct);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gallery();
            }
        });
    }

    public void Gallery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Imagen")
                .setMessage("Escoge metodo de captura de imagen")
                .setCancelable(false)
                .setNeutralButton("Galeria",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, GALLERY_INTENT);
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Cámara", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openCamara();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void openCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PHOTO_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GALLERY_INTENT:
                if(resultCode == RESULT_OK){
                    mProgressDialog.setTitle("Subiendo foto");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    Uri uri = data.getData();
                    String[] urlPartes = uri.getPath().split("/");
                    StorageReference filePath = mStorage.child("photos").child(urlPartes[urlPartes.length - 1]);
                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadphoto = taskSnapshot.getDownloadUrl();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = database.getReference().getRoot();
                            Product product = new Product(name_product.getText().toString(),
                                    category_product, downloadphoto.toString(), price_product.getText().toString());
                            ref.push().setValue(product);

                            name_product.setText("");
                            price_product.setText("");
                            mProgressDialog.dismiss();

                            Toast.makeText(AddProductActivity.this, "Se subio correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            case PHOTO_CODE:
                if (resultCode == RESULT_OK){
                    
                }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        category_product = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
