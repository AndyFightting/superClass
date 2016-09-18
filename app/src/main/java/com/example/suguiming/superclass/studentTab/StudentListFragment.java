package com.example.suguiming.superclass.studentTab;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.suguiming.superclass.MainActivity;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseFragment;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.utils.CommonUtil;
import com.example.suguiming.superclass.utils.OttoUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentListFragment extends BaseFragment implements View.OnClickListener {

    public MainActivity mainActivity;

    private ImageView addImage;

    private List<StudentModel> studentModelList = new ArrayList<>();
    private StudentAdapter adapter;
    private ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        addImage = (ImageView) view.findViewById(R.id.student_add_image);
        addImage.setOnClickListener(this);

        listView = (ListView) view.findViewById(R.id.student_list);
        listView.setAdapter(new StudentAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), StudentInfoActivity.class);
                startActivity(intent);
            }
        });

        OttoUtil.getInstance().register(this);

        return view;
    }

    @Override
    public void onDestroy() {
        OttoUtil.getInstance().unregister(this);
        super.onDestroy();
    }

    private void refreshListView(){
        studentModelList.clear();
        studentModelList.addAll(StudentModel.getAllStudents());




    }


    private class StudentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return studentModelList.size();
        }

        @Override
        public Object getItem(int i) {
            return new Integer(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(StudentListFragment.this.getActivity());
                view = inflater.inflate(R.layout.item_student_list, null);

                viewHolder = new ViewHolder();
                viewHolder.nameTv = (TextView) view.findViewById(R.id.name_tv);
                viewHolder.infoTv = (TextView) view.findViewById(R.id.info_tv);
                viewHolder.phoneImg = (ImageView) view.findViewById(R.id.phone_img);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            //-------设置值-------


            return view;
        }

        private class ViewHolder {
            ImageView phoneImg;
            TextView nameTv;
            TextView infoTv;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_add_image://添加学员
                Intent addIntent = new Intent(mainActivity, StudentAddActivity.class);
                mainActivity.startActivity(addIntent);
                break;
        }
    }

    @Subscribe
    public void ottoReceiveNotify(StudentModel model) {
        if (model.notifyType.equals(StudentModel.ADD)){
            refreshListView();
        }else if (model.notifyType.equals(StudentModel.UPDATE)){
            refreshListView();
        }else if (model.notifyType.equals(StudentModel.DELETE)){
            refreshListView();
        }
    }
}
