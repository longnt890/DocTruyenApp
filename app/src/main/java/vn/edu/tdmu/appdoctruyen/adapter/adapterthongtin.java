package vn.edu.tdmu.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.tdmu.appdoctruyen.R;
import vn.edu.tdmu.appdoctruyen.model.TaiKhoan;

public class adapterthongtin extends BaseAdapter {
    private Context context;
    private int layout;
    //Tạo mảng
    private List<TaiKhoan> taiKhoanList;

    public adapterthongtin(Context context, int layout, List<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.layout = layout;
        this.taiKhoanList = taiKhoanList;
    }

    @Override
    public int getCount() {
        return taiKhoanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //chuyển layout thành java
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout,null);


        TextView txtTenTaiKhoan = (TextView) convertView.findViewById(R.id.Text_Name);
        TextView txtGmail = (TextView) convertView.findViewById(R.id.Text_Gmail);

        TaiKhoan taiKhoan = taiKhoanList.get(position);

        //Hiển thị lên màn hình
        txtTenTaiKhoan.setText(taiKhoan.getmTenTaiKhoan());
        txtGmail.setText(taiKhoan.getmEmail());

        return convertView;
    }
}
