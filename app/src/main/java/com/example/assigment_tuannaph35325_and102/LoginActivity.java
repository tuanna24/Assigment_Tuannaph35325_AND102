package com.example.assigment_tuannaph35325_and102;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assigment_tuannaph35325_and102.dao.NguoiDungDAO;
import com.example.assigment_tuannaph35325_and102.uliti.SendMail;

public class LoginActivity extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;
    private SendMail sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Anh xa
        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtForgot = findViewById(R.id.txtForgot);
        TextView txtSignUp = findViewById(R.id.txtSigUp);

        nguoiDungDAO = new NguoiDungDAO(this);
        sendMail = new SendMail();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                boolean check = nguoiDungDAO.CheckLogin(user, pass);

                if (check) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterMainActivity.class));
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForgot();
            }
        });
    }
    private void showDialogForgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtEmail = view.findViewById(R.id.edtEmail);
        Button btnSend = view.findViewById(R.id.btnSend);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String matkhau = nguoiDungDAO.ForgotPassword(email);
//                Toast.makeText(LoginActivity.this, "matkhau", Toast.LENGTH_SHORT).show();
                if (matkhau.equals("")) {
                    Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    sendMail.Send(LoginActivity.this,email, "Lấy Lại Mật Khẩu", "Mật khẩu của bạn là: " + matkhau);
                    alertDialog.dismiss();
                }
            }
        });
    }
}