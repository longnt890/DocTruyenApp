package vn.edu.tdmu.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import vn.edu.tdmu.appdoctruyen.data.DatabaseDocTruyen;
import vn.edu.tdmu.appdoctruyen.model.Truyen;

public class ThemTruyen extends AppCompatActivity {

    EditText edtTieuDe,edtNoiDung,edtAnh;
    Button btnThemTruyen;
    DatabaseDocTruyen databaseDocTruyen;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_truyen);

        edtTieuDe = findViewById(R.id.dbtentruyen);
        edtNoiDung = findViewById(R.id.dbnoidung);
        btnThemTruyen = findViewById(R.id.btnAdd);
        edtAnh = findViewById(R.id.dbimg);
        img = findViewById(R.id.img1);
        databaseDocTruyen = new DatabaseDocTruyen(this);

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

        btnThemTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentruyen = edtTieuDe.getText().toString();
                String noidung = edtNoiDung.getText().toString();
                String img = edtAnh.getText().toString();

                Truyen truyen = CreatTruyen();

                if(tentruyen.equals("") || noidung.equals("") || img.equals("")){
                    Toast.makeText(ThemTruyen.this,"Yêu cầu nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseDocTruyen.AddTruyen(truyen);
                    Intent intent = new Intent(ThemTruyen.this,Admin.class);
                    finish();
                    startActivity(intent);
                    //Toast.makeText(ThemTruyen.this,"Thêm truyện thành công",Toast.LENGTH_SHORT).show();
                    //Log.e("Thêm truyện : ","Thành công");
                }
            }
        });
    }


    private Truyen CreatTruyen(){
        String tentruyen = edtTieuDe.getText().toString();
        String noidung = edtNoiDung.getText().toString();
        String img = edtAnh.getText().toString();

        Intent intent = getIntent();
        int id = intent.getIntExtra("Id",0);

        Truyen truyen = new Truyen(tentruyen,noidung,img,id);
        return truyen;
    }
}