package hect.preciosapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import hect.preciosapp.models.Product;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name_product;
    private String category_product;
    private EditText price_product;
    private Button btnAdd;
    private Spinner spn;

    private ProgressDialog mProgressDialog;

    private StorageReference mStorage;

    private static String APP_DIRECTORY = "PreciosApp";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Images";
    private String mPath;


    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private static final int GALLERY_INTENT = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        if (mayRequestStoragePermission()){
            btnAdd.setEnabled(true);
        }else
            btnAdd.setEnabled(false);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(name_product.getText().toString())){
                    name_product.setError("Escribir nombre de producto");
                    name_product.requestFocus();
                }else if ("".equals(price_product.getText().toString())){
                    price_product.setError("Escribir precio de producto");
                    price_product.requestFocus();
                }else
                    OpcionImageSelect();
            }
        });
    }

    private boolean mayRequestStoragePermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;
        return false;
    }

    public void OpcionImageSelect(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Imagen")
                .setMessage("Escoge metodo de captura de imagen")
                .setCancelable(false);
        builder.setNegativeButton("Escoger de Galería", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PHOTO_CODE);

                dialogInterface.cancel();
            }
        });
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mProgressDialog.setTitle("Subiendo foto");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_INTENT:
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
                        }
                    });
                    break;
                case PHOTO_CODE:
                    StorageReference mountainsRef = mStorage.child("photos")
                            .child(name_product.getText().toString() + ".jpg");
                    Bitmap  bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] d = baos.toByteArray();
                    UploadTask uploadTask = mountainsRef.putBytes(d);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = database.getReference().getRoot();
                            Product product = new Product(name_product.getText().toString(),
                                    category_product, downloadUrl.toString(), price_product.getText().toString());
                            ref.push().setValue(product);
                        }
                    });
                    break;
            }
            name_product.setText("");
            price_product.setText("");
            mProgressDialog.dismiss();
            Toast.makeText(AddProductActivity.this, "Se subio correctamente", Toast.LENGTH_SHORT).show();
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
