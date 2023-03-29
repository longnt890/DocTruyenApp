    package vn.edu.tdmu.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import vn.edu.tdmu.appdoctruyen.data.DatabaseDocTruyen;
import vn.edu.tdmu.appdoctruyen.model.Truyen;

    public class SuaTruyen extends AppCompatActivity {

        EditText edtTieuDe,edtNoiDung,edtAnh;
        Button btnSave;
        DatabaseDocTruyen databaseDocTruyen;
        Truyen truyen;
        ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_truyen);
        init();
        getData();

        edtAnh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Picasso.get().load(edtAnh.getText().toString()).into(img);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtTieuDe.equals("") || edtNoiDung.equals("") || edtAnh.equals("")){
                    Toast.makeText(SuaTruyen.this,"Yêu cầu nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                else {
                    truyen.setTenTruyen(edtTieuDe.getText().toString());
                    truyen.setAnh(edtAnh.getText().toString());
                    truyen.setNoiDung(edtNoiDung.getText().toString());
                    databaseDocTruyen.UpdateTruyen(truyen);
                    Intent intent = new Intent(SuaTruyen.this, Admin.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }



    public void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        truyen = (Truyen) bundle.getSerializable("truyen");

        edtTieuDe.setText(truyen.getTenTruyen());
        edtNoiDung.setText(truyen.getNoiDung());
        edtAnh.setText(truyen.getAnh());
        Picasso.get().load(truyen.getAnh()).into(img);
    }

    public void init(){
        edtTieuDe = findViewById(R.id.tentruyen);
        edtNoiDung = findViewById(R.id.noidung);
        btnSave = findViewById(R.id.btnSaveTr);
        edtAnh = findViewById(R.id.imgtruyen);
        img = findViewById(R.id.img);
        databaseDocTruyen = new DatabaseDocTruyen(this);


    }
}