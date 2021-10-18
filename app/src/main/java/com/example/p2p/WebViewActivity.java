package com.example.p2p;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.p2p.util.OtherUtils;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private final int REQUEST_CAMERA=1;
    private WebView webView;
    private RxPermissions rxPermissions;
    private File mTmpFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                //网页加载完之后，android调用js方法
//                webView.loadUrl("javascript:showFromAndroid()");
                String msg ="展示内容";
                webView.loadUrl("javascript:showFromAndroid('"+msg+"')");
                super.onPageFinished(view, url);

            }
        });
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //设置编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);  //开启js
        webView.addJavascriptInterface(new AndroidForJs(this),
                "ObjForJs");
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA).
                subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now

                    } else {
                        // Oups permission denied
                        this.finish();
                    }
                });
        webView.loadUrl("file:///android_asset/demo.html");
    }

    public void takeP(View view) {
        showCamera();
    }

    public class AndroidForJs {
        private Context context;

        public AndroidForJs(Context context) {
            this.context = context;
        }

        //api17以后，只有public且添加了 @JavascriptInterface 注解的方法才能被调用
        @JavascriptInterface
        public void takePhoto(String toast) {
            showCamera();
        }

    }


    private void showCamera() {

        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = OtherUtils.createFile(getApplicationContext());


            //Android 7.0 系统共享文件需要通过 FileProvider 添加临时权限，否则系统会抛出 FileUriExposedException .
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
                cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this,"com.example.p2p.fileprovider", mTmpFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            }else {
                cameraIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            }
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(getApplicationContext(), "系统没有相机", Toast.LENGTH_SHORT).show();
        }

    }

    private void runWebView(final String url) {
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            webView.loadUrl(url);
        }
    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA:
                //请求相机
                if (mTmpFile != null) {
                    String absolutePath = "file://"+mTmpFile.getAbsolutePath();

                    Log.d(TAG, "onActivityResult: 请求相机： " + absolutePath);
                    runWebView("javascript:showFromAndroid('" + absolutePath + "')");
//                    Picasso.with(this).load(mTmpFile).centerCrop().resize(OtherUtils.dip2px(this,100),OtherUtils.dip2px(this,100))
//                            .error(R.mipmap.pictures_no).into(mIvDispaly);
                }
                break;

        }
    }

    //js 调用原生方法   window.ObjForJs.takePhoto(toast);
//    <!doctype html>
//<html>
//...
//    function showAndroidToast(toast) {
//        window.ObjForJs.takePhoto(toast);
//    }
//...
//</html>

}