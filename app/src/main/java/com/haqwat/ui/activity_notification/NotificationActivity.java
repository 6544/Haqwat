package com.haqwat.ui.activity_notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.adapters.NotificationAdapter;
import com.haqwat.databinding.ActivityAboutAppBinding;
import com.haqwat.databinding.ActivityNotificationBinding;
import com.haqwat.language.Language;
import com.haqwat.models.NotificationModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_notification_mvp.FragmentNotificationPresenter;
import com.haqwat.mvp.activity_notification_mvp.FragmentNotificationView;
import com.haqwat.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class NotificationActivity extends AppCompatActivity implements FragmentNotificationView {
    private ActivityNotificationBinding binding;
    private String lang;
    private FragmentNotificationPresenter presenter;
    private int current_page =1;
    private boolean isLoading = false;
    private List<NotificationModel> notificationModelList;
    private NotificationAdapter adapter;
    private UserModel userModel;
    private Preferences preferences;
    private boolean isNotificationCountChanged = false;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        initView();
    }



    private void initView()
    {
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        notificationModelList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationModelList,this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(adapter);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.llBack.setOnClickListener(view -> onBackPressed());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        presenter = new FragmentNotificationPresenter(this,this);
        presenter.getNotification(userModel,lang);


        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    LinearLayoutManager manager = (LinearLayoutManager) binding.recView.getLayoutManager();
                    int last_item_pos = manager.findLastCompletelyVisibleItemPosition();
                    int total_items_count = binding.recView.getAdapter().getItemCount();
                    if (last_item_pos == (total_items_count - 2) && !isLoading) {
                        int page = current_page + 1;
                        presenter.loadMore(userModel,lang,page);
                    }
                }
            }
        });

    }


    @Override
    public void onSuccess(List<NotificationModel> notificationModelList) {
        isNotificationCountChanged = true;

        if (notificationModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            this.notificationModelList.addAll(notificationModelList);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onLoadMoreSuccess(List<NotificationModel> notificationModelList,int current_page) {
        this.current_page = current_page;
        int pos = this.notificationModelList.size()-1;
        this.notificationModelList.addAll(notificationModelList);
        adapter.notifyItemRangeChanged(pos,this.notificationModelList.size());

    }

    @Override
    public void onFailed(String msg) {
        if (notificationModelList.get(notificationModelList.size()-1)==null){
            notificationModelList.remove(notificationModelList.size()-1);
            adapter.notifyItemRemoved(notificationModelList.size()-1);
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        if (isNotificationCountChanged){
            setResult(RESULT_OK);
        }
        finish();
    }
}