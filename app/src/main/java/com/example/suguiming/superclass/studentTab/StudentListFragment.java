package com.example.suguiming.superclass.studentTab;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.suguiming.superclass.studentTab.info.photoSheet.AlbumDirActivity;
import com.example.suguiming.superclass.utils.CommonUtil;
import com.example.suguiming.superclass.utils.FileUtil;
import com.example.suguiming.superclass.utils.ImageUtil;
import com.example.suguiming.superclass.utils.OttoUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
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
        initListView(view);
        refreshListView();

        OttoUtil.getInstance().register(this);

        return view;
    }

    private void initListView(View containerView){
        addImage = (ImageView) containerView.findViewById(R.id.student_add_image);
        addImage.setOnClickListener(this);

        listView = (ListView) containerView.findViewById(R.id.student_list);
        adapter = new StudentAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                StudentModel model = studentModelList.get(i);
//                StudentInfoActivity.startActivity(mainActivity,model.getIdString());

                Intent intent = new Intent(mainActivity, AlbumDirActivity.class);
                mainActivity.startActivity(intent);

            }
        });
    }

    @Override
    public void onDestroy() {
        OttoUtil.getInstance().unregister(this);
        super.onDestroy();
    }

    private void refreshListView(){
        studentModelList.clear();
        studentModelList.addAll(StudentModel.getAllStudents());

        adapter.notifyDataSetChanged();
    }

    @Override
    public void tabClicked() {
        if (studentModelList.size()<1){
            Intent intent = new Intent(mainActivity, StudentAddActivity.class);
            mainActivity.startActivity(intent);
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

    //---------接收otto通知---------
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

    //------------------adapter------------------------
    private class StudentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return studentModelList.size();
        }

        @Override
        public Object getItem(int i) {
            return studentModelList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            StudentModel model = studentModelList.get(i);

            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(StudentListFragment.this.getActivity());
                view = inflater.inflate(R.layout.item_student_list, null);

                viewHolder = new ViewHolder();
                viewHolder.nameTv = (TextView) view.findViewById(R.id.name_tv);
                viewHolder.infoTv = (TextView) view.findViewById(R.id.info_tv);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            //-------设置值-------
            String infoString ="剩余课程数:"+model.getRemainCount()+"  已完成课程数:"+model.getFinishCount();

            viewHolder.nameTv.setText(model.getNameString());
            viewHolder.infoTv.setText(infoString);

            return view;
        }

        private class ViewHolder {
            TextView nameTv;
            TextView infoTv;
        }
    }
}
