package com.example.archer.thethirdlibraryuse;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn= (Button) findViewById(R.id.permission);
    }




    //绑定Btn的点击事件，并且调用permissionwithcheck方法，传入上下文
    public void RequestPermission(View view) {
        requestPermission();

    }


//该方法用于申请权限
    private void requestPermission() {
        //申请权限
        MainActivityPermissionsDispatcher.permissonWithCheck(this);

    }


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void permisson() {
        Toast.makeText(this,"读写权限已经申请",Toast.LENGTH_SHORT);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void Rationale(final PermissionRequest request) {

        new AlertDialog.Builder(this)
                .setMessage("申请SD卡读写程序")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //再次执行请求
                        request.proceed();
                    }
                })
                .show();

    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void Denied() {
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();

    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void Ask() {
        Toast.makeText(this, "不再询问", Toast.LENGTH_SHORT).show();

    }



}
