package vn.edu.tdmu.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.tdmu.appdoctruyen.data.DatabaseDocTruyen;
import vn.edu.tdmu.appdoctruyen.model.TaiKhoan;

public class DangNhap extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;

    DatabaseDocTruyen databaseDocTruyen;
    TaiKhoan t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Init();

        databaseDocTruyen = new DatabaseDocTruyen(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this,DangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUser(edtTaiKhoan.getText().toString(),edtMatKhau.getText().toString())){
                    int phanquyen = t.getmPhanQuyen();
                    int idd = t.getmId();
                    String tentk = t.getmTenTaiKhoan();
                    String email = t.getmEmail();
                    Intent intent = new Intent(DangNhap.this,MainActivity.class);

                    intent.putExtra("phanq",phanquyen);
                    intent.putExtra("idd",idd);
                    intent.putExtra("email",email);
                    intent.putExtra("tentaikhoan",tentk);

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplication(),"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_LONG).show();
                }

                }
        });
    }
    public boolean isUser(String TaiKhoan, String MatKhau){
        try {
            Cursor cursor = databaseDocTruyen.getData();
            while (cursor.moveToNext()){
                String datatentaikhoan = cursor.getString(1);
                String datamatkhau = cursor.getString(2);

                if(datatentaikhoan.equals(TaiKhoan) && datamatkhau.equals(MatKhau)){
                    t = databaseDocTruyen.getTaiKhoan(cursor.getInt(0));
                    return true;
                }


            }
        }catch (Exception e){

        }
        return false;
    }
    private void Init() {
        edtTaiKhoan = findViewById(R.id.taikhoan);
        edtMatKhau = findViewById(R.id.matkhau);
        btnDangNhap = findViewById(R.id.dangnhap);
        btnDangKy = findViewById(R.id.dangky);
    }
}