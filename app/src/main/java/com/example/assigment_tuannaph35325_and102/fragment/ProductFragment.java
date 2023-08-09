package com.example.assigment_tuannaph35325_and102.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assigment_tuannaph35325_and102.R;
import com.example.assigment_tuannaph35325_and102.adapter.ProductAdapter;
import com.example.assigment_tuannaph35325_and102.dao.SanPhamDAO;
import com.example.assigment_tuannaph35325_and102.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerProduct;
    private FloatingActionButton floatAdd;

    private SanPhamDAO sanPhamDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        //anh xa

        recyclerProduct = view.findViewById(R.id.recyclerProduct);
        floatAdd = view.findViewById(R.id.floatAdd);

        sanPhamDAO = new SanPhamDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

        return view;
    }

    private void loadData() {
        ArrayList<Product> list = sanPhamDAO.getDS();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter(getContext(), list, sanPhamDAO);
        recyclerProduct.setAdapter(adapter);
    }

    private void showDialogAdd () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Su ly chuc nang
        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtSLSP = view.findViewById(R.id.edtSLSP);
        Button btnThemSP = view.findViewById(R.id.btnThemSP);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGiaSP.getText().toString();
                String slsp = edtSLSP.getText().toString();
                
                if (tensp.length() == 0 || giasp.length() == 0 || slsp.length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    Product product = new Product(tensp, Integer.parseInt(giasp), Integer.parseInt(slsp));
                    boolean check = sanPhamDAO.themSPMoi(product);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}