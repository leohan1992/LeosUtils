package com.leo.leoslib.selectPic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.leo.leoslib.R;
import com.leo.leoslib.selectPic.adapter.ImageBucketAdapter;
import com.leo.leoslib.selectPic.model.ImageBucket;
import com.leo.leoslib.selectPic.util.ImageFetcher;
import com.leo.leoslib.selectPic.util.IntentConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择相册
 */

public class ImageBucketChooseActivity extends Activity {
    private ImageFetcher mHelper;
    private List<ImageBucket> mDataList = new ArrayList<>();
    private ListView mListView;
    private ImageBucketAdapter mAdapter;
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_bucket_choose);

        mHelper = ImageFetcher.getInstance(getApplicationContext());
        initData();
        initView();
    }

    private void initData() {
        mDataList = mHelper.getImagesBucketList(false);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new ImageBucketAdapter(this, mDataList);
        mListView.setAdapter(mAdapter);
        TextView titleTv = (TextView) findViewById(R.id.title);
        titleTv.setText("相册");
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                selectOne(position);

                Intent intent = new Intent(ImageBucketChooseActivity.this,
                        ImageChooseActivity.class);
                intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST,
                        (Serializable) mDataList.get(position).imageList);
                intent.putExtra(IntentConstants.EXTRA_BUCKET_NAME,
                        mDataList.get(position).bucketName);
                intent.putExtra(NewSelectPicActivity.PICK_MODE, getIntent().getIntExtra(NewSelectPicActivity.PICK_MODE, NewSelectPicActivity.SINGLE_PICK));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        TextView cancelTv = (TextView) findViewById(R.id.action);
        cancelTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void selectOne(int position) {
        int size = mDataList.size();
        for (int i = 0; i != size; i++) {
            if (i == position) mDataList.get(i).selected = true;
            else {
                mDataList.get(i).selected = false;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            ArrayList<String> paths=data.getStringArrayListExtra(IntentConstants.EXTRA_IMAGE_LIST);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
