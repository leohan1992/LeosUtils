package com.leo.leoslib.selectPic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.leo.leoslib.R;
import com.leo.leoslib.selectPic.adapter.ImageGridAdapter;
import com.leo.leoslib.selectPic.model.ImageItem;
import com.leo.leoslib.selectPic.util.IntentConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 图片选择
 */
public class ImageChooseActivity extends Activity {
    private List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private String mBucketName;
    private GridView mGridView;
    private TextView mBucketNameTv;
    private TextView cancelTv;
    private ImageGridAdapter mAdapter;
    private Button mFinishBtn;
    private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();
    private int pickMode = 1;
    private int lastSelectPostion = -1;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_image_choose);
        pickMode = getIntent().getIntExtra(NewSelectPicActivity.PICK_MODE, 0);
        mDataList = (List<ImageItem>) getIntent().getSerializableExtra(
                IntentConstants.EXTRA_IMAGE_LIST);
        if (mDataList == null) mDataList = new ArrayList<ImageItem>();
        mBucketName = getIntent().getStringExtra(
                IntentConstants.EXTRA_BUCKET_NAME);

        if (TextUtils.isEmpty(mBucketName)) {
            mBucketName = "请选择";
        }
        initView();
        initListener();

    }

    private void initView() {
        mBucketNameTv = (TextView) findViewById(R.id.title);
        mBucketNameTv.setText(mBucketName);

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList);
        mGridView.setAdapter(mAdapter);
        mFinishBtn = (Button) findViewById(R.id.finish_btn);
        cancelTv = (TextView) findViewById(R.id.action);
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        mFinishBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                ArrayList<String> pics = new ArrayList<>();
                ArrayList<ImageItem> imageItems = new ArrayList<>(selectedImgs
                        .values());
                for (int i = 0; i < imageItems.size(); i++) {
                    pics.add(imageItems.get(i).sourcePath);
                }
                intent.putExtra(
                        IntentConstants.EXTRA_IMAGE_LIST, pics);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ImageItem item = mDataList.get(position);
                if (pickMode == NewSelectPicActivity.MULTI_PICK) {//多选模式

                    if (item.isSelected) {
                        item.isSelected = false;
                        selectedImgs.remove(item.imageId);
                    } else {
                        item.isSelected = true;
                        selectedImgs.put(item.imageId, item);
                    }
                } else {//单选模式
                    if (item.isSelected) {
                        item.isSelected = false;
                        selectedImgs.remove(item.imageId);
                    } else {
                        item.isSelected = true;
                        if (lastSelectPostion != -1) {
                            mDataList.get(lastSelectPostion).isSelected = false;
                        }
                        lastSelectPostion = position;
                        selectedImgs.clear();
                        selectedImgs.put(item.imageId, item);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

        });

        cancelTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}