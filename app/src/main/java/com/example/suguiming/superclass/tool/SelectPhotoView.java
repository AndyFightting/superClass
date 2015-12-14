package com.example.suguiming.superclass.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.suguiming.superclass.R;

/**
 * Created by suguiming on 15/11/17.
 */
public class SelectPhotoView extends RelativeLayout implements View.OnClickListener {

    private RelativeLayout backView;
    private Button cameraBt;
    private Button photoBt;
    private Button cancelBt;
    private PhotoItemClickListener itemClickListener;

    public SelectPhotoView(Context context, PhotoItemClickListener listener) {
        super(context);
        this.itemClickListener = listener;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView =  inflater.inflate(R.layout.select_photo_layout, this);

        backView = (RelativeLayout)mView.findViewById(R.id.back_view);
        cameraBt = (Button)mView.findViewById(R.id.camera_bt);
        photoBt = (Button)mView.findViewById(R.id.photo_bt);
        cancelBt= (Button)mView.findViewById(R.id.cancel_bt);

        backView.setOnClickListener(this);
        cameraBt.setOnClickListener(this);
        photoBt.setOnClickListener(this);
        cancelBt.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (this.itemClickListener != null){
            switch (view.getId()) {
                case R.id.back_view:
                    this.itemClickListener.cancelTap();
                    break;
                case R.id.camera_bt:
                    this.itemClickListener.cameraTap();
                    break;
                case R.id.photo_bt:
                    this.itemClickListener.photoTap();
                    break;
                case R.id.cancel_bt:
                    this.itemClickListener.cancelTap();
                    break;
            }
        }
    }

    public interface PhotoItemClickListener{
        void cameraTap();
        void photoTap();
        void cancelTap();
    }
}
