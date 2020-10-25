package com.haqwat.mvp.fragment_sign_up1_mvp;

import com.haqwat.models.NationalityModel;
import com.haqwat.models.SignUpModel;

import java.util.List;

public interface FragmentSignUp1View {
    void onDateSelected(SignUpModel signUpModel);
    void onSelectImage();
    void onGenderSuccess(List<String> list);
    void onNationalitySuccess(List<NationalityModel> list);
    void onFailed(String msg);
}
