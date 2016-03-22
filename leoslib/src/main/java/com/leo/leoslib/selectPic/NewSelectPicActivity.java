package com.leo.leoslib.selectPic;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leo.leoslib.R;
import com.leo.leoslib.selectPic.util.IntentConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * @author spring sky<br>
 *         Email :vipa1888@163.com<br>
 *         QQ: 840950105<br>
 * @version 创建时间：2012-11-22 上午9:20:03
 *          说明：主要用于选择文件操作
 *          在AndroidManifest.xml声明activity时添加
 *          android:theme="@style/DialogStyleBottom" 属性:
 *          <style name="DialogStyleBottom" parent="android:Theme.Dialog">
 *          <item name="android:windowAnimationStyle">@style/AnimBottom</item>
 *          <item name="android:windowFrame">@null</item>
 *          <!-- 边框 -->
 *          <item name="android:windowIsFloating">false</item>
 *          <!-- 是否浮现在activity之上 -->
 *          <item name="android:windowIsTranslucent">true</item>
 *          <!-- 半透明 -->
 *          <item name="android:windowNoTitle">true</item>
 *          <!-- 无标题 -->
 *          <item name="android:windowBackground">@android:color/transparent</item>
 *          <!-- 背景透明 -->
 *          <item name="android:backgroundDimEnabled">true</item>
 *          <!-- 模糊 -->
 *          </style>
 */


public class NewSelectPicActivity extends Activity {

    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    public static final String PICK_MODE = "pickMode";
    /**
     * 单选模式
     */
    public static final int SINGLE_PICK = 1;
    /**
     * 多选模式
     */
    public static final int MULTI_PICK = 2;


    private LinearLayout dialogLayout;
    private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
    private String path = "";
    public static final String KEY_PHOTO_PATH = "photo_path";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            path=savedInstanceState.getString("temp");
        }
        setContentView(R.layout.select_pic_layout);
        initView();
    }

    /**
     * 初始化加载View
     */
    private void initView() {
        dialogLayout = (LinearLayout) findViewById(R.id.dialog_layout);
        dialogLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
        takePhotoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
        pickPhotoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPicture();
            }
        });
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.dialog_layout:
//                finish();
//                break;
//            case R.id.btn_take_photo:
//                takePhoto();
//                break;
//            case R.id.btn_pick_photo:
//                pickPicture();
//                break;
//            default:
//                finish();
//                break;
//        }
//    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File vFile = new File(Environment.getExternalStorageDirectory()
                + "/myimage/", String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, SELECT_PIC_BY_TACK_PHOTO);

    }

    private void pickPicture() {
        Intent intent = new Intent(this, ImageBucketChooseActivity.class);
        intent.putExtra(PICK_MODE, getIntent().getIntExtra(PICK_MODE, SINGLE_PICK));
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<String> paths = new ArrayList<>();
            switch (requestCode) {
                case SELECT_PIC_BY_TACK_PHOTO:
                    if (!TextUtils.isEmpty(path)) {
                        paths.add(path);
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra(KEY_PHOTO_PATH, paths);
                        setResult(RESULT_OK, intent);
                        this.finish();
                    } else {
                        Toast.makeText(this, "拍照出错", Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case SELECT_PIC_BY_PICK_PHOTO:
                    ArrayList<String> items = data.getStringArrayListExtra(IntentConstants.EXTRA_IMAGE_LIST);
                    paths.addAll(items);
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(KEY_PHOTO_PATH, paths);
                    setResult(RESULT_OK, intent);
                    this.finish();
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("temp",path);
    }
}
