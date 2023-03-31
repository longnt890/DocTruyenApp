package vn.edu.tdmu.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.tdmu.appdoctruyen.adapter.adaptertruyen;
import vn.edu.tdmu.appdoctruyen.data.DatabaseDocTruyen;
import vn.edu.tdmu.appdoctruyen.model.Truyen;

public class Admin extends Activity {

    ListView listView;
    Button buttonThem;

    ArrayList<Truyen> TruyenArrayList;
    vn.edu.tdmu.appdoctruyen.adapter.adaptertruyen adaptertruyen;
    DatabaseDocTruyen databaseDocTruyen;

    int posselected = -1;
    public static  final int ADD = 1;
    public static final int EDIT = 2;
    public static  final int SAVE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listView = findViewById(R.id.listviewAdmin);
        buttonThem = findViewById(R.id.buttonAddTruyen);

        initList();
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                return false;
            }


        });


        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                int id = intent.getIntExtra("Id", 0);
                Intent intent1 = new Intent(Admin.this, ThemTruyen.class);
                intent.putExtra("Id", id);
                adaptertruyen.notifyDataSetChanged();
                startActivityForResult(intent1,ADD);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Admin.this,NoiDung.class);
                String tent =   TruyenArrayList.get(position).getTenTruyen();
                String noidungt = TruyenArrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                //Log.e("Tên truyện : ",tent);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuDelete:
                confirmDelete();
                return true;
            case R.id.menuUpdate:
                Truyen truyen = TruyenArrayList.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(Admin.this,SuaTruyen.class);
                bundle.putSerializable("truyen",truyen);

                intent.putExtra("data",bundle);
                startActivityForResult(intent,EDIT);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD:{
                if(resultCode == SAVE){
                    Bundle bundle = data.getBundleExtra("data");
                    Truyen truyen = (Truyen) bundle.getSerializable("truyen");
                    TruyenArrayList.add(truyen);
                    adaptertruyen.notifyDataSetChanged();
                }
                break;
            }
            case EDIT:{
                if(resultCode == SAVE){
                    Bundle bundle = data.getBundleExtra("data");
                    Truyen truyen = (Truyen) bundle.getSerializable("truyen");
                    TruyenArrayList.set(posselected,truyen);
                    adaptertruyen.notifyDataSetChanged();
                }
                break;
            }
        }
    }

    public void confirmDelete(){
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setMessage("Bạn có chắc muốn xóa truyện này?");
        al.setCancelable(false);
        al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int idtruyen = TruyenArrayList.get(posselected).getID();
                //Xóa trong SQL
                databaseDocTruyen.Delete(idtruyen);
                //Cập nhật lại listview
                TruyenArrayList.remove(posselected);
                adaptertruyen.filterList(TruyenArrayList);
                Toast.makeText(Admin.this,"Xóa truyện thành công",Toast.LENGTH_SHORT).show();
            }
        });
        al.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = al.create();
        alertDialog.show();

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu,menu);
    }

    //Gán DL vào listview
    public void initList(){
        TruyenArrayList = new ArrayList<>();
        databaseDocTruyen = new DatabaseDocTruyen(this);

        Cursor cursor1 = databaseDocTruyen.getData2();

        while (cursor1.moveToNext()){

            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);
            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adaptertruyen = new adaptertruyen(getApplicationContext(),TruyenArrayList);
            listView.setAdapter(adaptertruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();
    }
}