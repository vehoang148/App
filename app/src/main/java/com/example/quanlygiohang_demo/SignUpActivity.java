package com.example.quanlygiohang_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.quanlygiohang_demo.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = binding.signupFullname.getText().toString();
                String username = binding.signupUsername.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmpassword = binding.signupConfirm.getText().toString();
                String createdAt = getCurrentDateTime(); // Lấy ngày giờ hiện tại lúc tạo tài khoản

                if (fullName.equals("") || username.equals("") || password.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if (fullName.length() > 4 || username.length() > 6 || password.length() > 8) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập các thông tin phải đúng yêu cầu", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirmpassword)) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }else {
                    boolean checkUsernameExists = databaseHelper.checkUsernamePassword(username,password);
                    if (checkUsernameExists) {
                        Toast.makeText(SignUpActivity.this, "Tên này đã có người dùng", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean insertResult = databaseHelper.insertData(username, password, fullName, createdAt);

                        if (insertResult) {
                            Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    // Phương thức để lấy ngày giờ hiện tại
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}