package com.haqwat.ui.activity_complete_sign_up.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.R;
import com.haqwat.adapters.GenderSpinnerAdapter;
import com.haqwat.adapters.NationalitySpinnerAdapter;
import com.haqwat.databinding.FragmentSignUp1Binding;
import com.haqwat.databinding.FragmentSignUp2Binding;
import com.haqwat.models.NationalityModel;
import com.haqwat.models.SignUpModel;
import com.haqwat.mvp.fragment_sign_up1_mvp.FragmentSignUp1Presenter;
import com.haqwat.mvp.fragment_sign_up1_mvp.FragmentSignUp1View;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fragment_Sign_Up1 extends Fragment implements FragmentSignUp1View {
    public static final String TAG= "TAG";
    public SignUpModel signUpModel;
    private FragmentSignUp1Binding binding;
    private CompleteSignUpActivity activity;
    private List<String> genderList;
    private GenderSpinnerAdapter genderSpinnerAdapter;
    private NationalitySpinnerAdapter nationalitySpinnerAdapter;
    private List<NationalityModel> nationalityModelList;
    private final int IMG_CODE=100;
    private FragmentSignUp1Presenter presenter;



    public static Fragment_Sign_Up1 newInstance(SignUpModel signUpModel){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG,signUpModel);
        Fragment_Sign_Up1 fragment_sign_up1 = new Fragment_Sign_Up1();
        fragment_sign_up1.setArguments(bundle);
        return fragment_sign_up1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up1,container,false);
        initView();
        return binding.getRoot();

    }

    private void initView() {
        activity = (CompleteSignUpActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle!=null){
            signUpModel = (SignUpModel) bundle.getSerializable(TAG);
        }
        nationalityModelList= new ArrayList<>();
        nationalityModelList.add(new NationalityModel(0,"","Choose Nationality","إختر الجنسية",""));
        genderList = new ArrayList<>();
        binding.llDate.setOnClickListener(view -> {
           presenter.showDateDialog();
        });
        genderSpinnerAdapter = new GenderSpinnerAdapter(genderList,activity);
        binding.spinnerGender.setAdapter(genderSpinnerAdapter);
        binding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    signUpModel.setGender("");
                }else if (i==1){
                    signUpModel.setGender("male");

                }else if (i==2){
                    signUpModel.setGender("female");

                }

                binding.setModel(signUpModel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nationalitySpinnerAdapter = new NationalitySpinnerAdapter(nationalityModelList,activity);
        binding.spinnerNationality.setAdapter(nationalitySpinnerAdapter);
        binding.spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                NationalityModel nationalityModel = nationalityModelList.get(i);
                signUpModel.setNationality_id(String.valueOf(nationalityModel.getId()));
                binding.setModel(signUpModel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.checkbox.setOnClickListener(view -> {
            if (binding.checkbox.isChecked()){
                binding.anotherUser.setVisibility(View.VISIBLE);
                signUpModel.setFromOtherUser(true);
            }else {
                binding.anotherUser.setVisibility(View.GONE);
                signUpModel.setFromOtherUser(false);
            }
            binding.setModel(signUpModel);
        });
        binding.flSelectImage.setOnClickListener(view -> {
            presenter.selectImage();
        });
        presenter = new FragmentSignUp1Presenter(this,activity,signUpModel,activity.getFragmentManager());


    }

    @Override
    public void onDateSelected(SignUpModel signUpModel) {
        this.signUpModel = signUpModel;
        binding.setModel(signUpModel);
    }

    @SuppressLint("IntentReset")
    @Override
    public void onSelectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Image"),IMG_CODE);

    }

    @Override
    public void onGenderSuccess(List<String> list) {
        genderList.clear();
        genderList.addAll(list);
        genderSpinnerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onNationalitySuccess(List<NationalityModel> list) {
        nationalityModelList.addAll(list);
        activity.runOnUiThread(()->{
            nationalitySpinnerAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }


    private void cropImage(Uri uri) {

        CropImage.activity(uri).setAspectRatio(1,1).setGuidelines(CropImageView.Guidelines.ON).start(activity);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG_CODE&&resultCode== Activity.RESULT_OK&&data!=null){
            Uri uri = data.getData();
            if (uri!=null){
                cropImage(uri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = result.getUri();
                signUpModel.setLocal_image(uri.toString());
                Picasso.get().load(uri).into(binding.image);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

}
