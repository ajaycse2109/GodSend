package com.oodi.godsendapp.fragment.records;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.AmbulanceActivity;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Records;
import com.oodi.godsendapp.util.OnBackPressListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddHealthRecordFragment extends RootFragment {
    Activity mContext;
    View view ;
    String id,id2,id3, flagPref;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.changeimg)
    ImageView mfilename ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.btnUpload)
    Button mBtnUpload;
    @BindView(R.id.recordfile)
    EditText mrecordfile;
    @BindView(R.id.desc)
    EditText mdesc;
    List<Records> mRecordsList = new ArrayList<>();
    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;
    public AddHealthRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_allrecordslist, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        SharedPreferences pref = getActivity().getSharedPreferences("MY" , Context.MODE_PRIVATE);
        id = pref.getString("file_name", "");
        id2 = pref.getString("record_type", "");
        id3 = pref.getString("des", "");
        Log.d("PARAM::", id);
        Log.d("PARAM::", id2);
        Log.d("PARAM::", id3);
        mrecordfile.setText(id);
        mTxtHeaderName.setText(id2);
        mdesc.setText(id3);
        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
        mfilename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //    intent.setType("file/*");
                startActivityForResult(intent, RQS_OPEN_IMAGE);
                Toast.makeText(mContext, "please choose file", Toast.LENGTH_LONG).show();
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == RQS_OPEN_IMAGE ||
                    requestCode == RQS_GET_IMAGE ||
                    requestCode == RQS_PICK_IMAGE) {

                //imageView.setImageBitmap(null);
                // textInfo1.setText("");
                mrecordfile.setText(id);

                Uri mediaUri = data.getData();
                // File myFile = new File(String.valueOf((mediaUri)));
                String FileName = mediaUri.getLastPathSegment();
                //FileName.substring(FileName.lastIndexOf("."));
                //textInfo1.setText(mediaUri.toString());
                String mediaPath = mediaUri.getPath();
                mrecordfile.setText("File Name: \n" + FileName +"\n");
                //display the image
                try {
                    InputStream inputStream = mContext.getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    //imageView.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
