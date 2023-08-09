package com.example.assigment_tuannaph35325_and102.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assigment_tuannaph35325_and102.R;
import com.example.assigment_tuannaph35325_and102.dao.SanPhamDAO;
import com.example.assigment_tuannaph35325_and102.model.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> list;

    private SanPhamDAO sanPhamDAO;

    public ProductAdapter(Context context, ArrayList<Product> list, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.list = list;
        this.sanPhamDAO = sanPhamDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).getTensp());

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = list.get(position).getGiaban();
        String formattedNumber = formatter.format(myNumber);

        holder.txtPrice.setText(formattedNumber + " VNĐ");
        holder.txtQuantity.setText("SL: " + list.get(position).getSoluong());

        //Sua san pham

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });

        //Xoa SP

        holder.txtDell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDell(list.get(holder.getAdapterPosition()).getTensp(),
                        list.get(holder.getAdapterPosition()).getMasp()
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice, txtQuantity, txtEdit, txtDell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            txtDell = itemView.findViewById(R.id.txtDell);

        }
    }

    private void showDialogUpdate(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.diaglog_update, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtSLSP = view.findViewById(R.id.edtSLSP);
        Button btnSuaSP = view.findViewById(R.id.btnSuaSP);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        //Dua du lieu sp can sua len ediText
        edtTenSP.setText(product.getTensp());
        edtGiaSP.setText(String.valueOf(product.getGiaban()));
        edtSLSP.setText(String.valueOf(product.getSoluong()));

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int masp = product.getMasp();
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGiaSP.getText().toString();
                String slsp = edtSLSP.getText().toString();

                if (tensp.length() == 0 || giasp.length() == 0 || slsp.length() == 0) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Product productChinhSua = new Product(masp, tensp, Integer.parseInt(giasp), Integer.parseInt(slsp));
                    boolean check = sanPhamDAO.chinhSuaSP(productChinhSua);

                    if (check) {
                        Toast.makeText(context, "Chỉnh sửa thành công!", Toast.LENGTH_SHORT).show();

                        loadData();

                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Chỉnh sửa thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showDialogDell(String tensp, int
            masp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo !");
        builder.setIcon(R.drawable.ic_warning);
        builder.setMessage("Bạn có muốn xóa sản phẩm \"" + tensp + "\" không?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check = sanPhamDAO.xoaSP(masp);
                if (check) {
                    Toast.makeText(context, "Bạn đã xóa thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadData() {
        list.clear();
        list = sanPhamDAO.getDS();
        notifyDataSetChanged();
    }
}
