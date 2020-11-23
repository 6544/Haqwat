package com.haqwat.mvp.sign_up_mpv;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.haqwat.R;
import com.haqwat.models.SignUpModel;
import com.haqwat.models.UserModel;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_complete_sign_up.fragments.Fragment_Sign_Up1;
import com.haqwat.ui.activity_complete_sign_up.fragments.Fragment_Sign_Up2;
import com.haqwat.ui.activity_complete_sign_up.fragments.Fragment_Sign_Up3;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter {
    private Context context;
    private SignUpView view;
    private FragmentManager fragmentManager;
    private Fragment_Sign_Up1 fragment_sign_up1;
    private Fragment_Sign_Up2 fragment_sign_up2;
    private Fragment_Sign_Up3 fragment_sign_up3;
    private SignUpModel signUpModel;
    private UserModel userModel;
    public static int next = 1, previous = 0;


    public SignUpPresenter(Context context, SignUpView view, FragmentManager fragmentManager, UserModel userModel) {
        this.context = context;
        this.view = view;
        this.fragmentManager = fragmentManager;
        this.userModel = userModel;
        if (signUpModel == null) {
            this.signUpModel = new SignUpModel();
            signUpModel.setEmail(userModel.getEmail());
        }
        displayFragmentStep1();
    }

    public void manageFragments(int next_previous) {
        if (fragment_sign_up1 != null && fragment_sign_up1.isAdded() && fragment_sign_up1.isVisible()) {
            signUpModel = fragment_sign_up1.signUpModel;
            if (next_previous == next) {
                if (signUpModel.isStep2Valid(context)) {
                    displayFragmentStep2();

                }
            } else {
                view.onBack();
            }
        } else if (fragment_sign_up2 != null && fragment_sign_up2.isAdded() && fragment_sign_up2.isVisible()) {

            signUpModel = fragment_sign_up2.signUpModel;

            if (next_previous == next) {
                if (signUpModel.isStep3Valid(context)) {
                    displayFragmentStep3();

                }
            } else {
                displayFragmentStep1();
            }
        } else if (fragment_sign_up3 != null && fragment_sign_up3.isAdded() && fragment_sign_up3.isVisible()) {
            signUpModel = fragment_sign_up3.signUpModel;
            if (next_previous == next) {
                if (signUpModel.isStep4Valid(context))
                    if (signUpModel.getLocal_image()!=null&&!signUpModel.getLocal_image().isEmpty()){
                        signUpWithImage();
                    }else {
                        signUpWithoutImage();
                    }
            } else {
                displayFragmentStep2();
            }
        }
    }




    private void displayFragmentStep1() {
        if (fragment_sign_up1 == null) {
            fragment_sign_up1 = Fragment_Sign_Up1.newInstance(signUpModel);
        }


        if (fragment_sign_up2 != null && fragment_sign_up2.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sign_up2).commit();
        }

        if (fragment_sign_up3 != null && fragment_sign_up3.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sign_up3).commit();
        }

        if (fragment_sign_up1 != null && fragment_sign_up1.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_up1).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_sign_up1, "fragment_sign_up1").addToBackStack("fragment_sign_up1").commit();
        }
        view.onDisplayFragmentStep1();
    }

    private void displayFragmentStep2() {
        if (fragment_sign_up2 == null) {
            fragment_sign_up2 = Fragment_Sign_Up2.newInstance(signUpModel);
        }

        if (fragment_sign_up1 != null && fragment_sign_up1.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sign_up1).commit();
        }


        if (fragment_sign_up3 != null && fragment_sign_up3.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sign_up3).commit();
        }

        if (fragment_sign_up2 != null && fragment_sign_up2.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_up2).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_sign_up2, "fragment_sign_up2").addToBackStack("fragment_sign_up2").commit();
        }
        view.onDisplayFragmentStep2();

    }

    private void displayFragmentStep3() {

        if (fragment_sign_up3 == null) {
            fragment_sign_up3 = Fragment_Sign_Up3.newInstance(signUpModel);

        }

        if (fragment_sign_up1 != null && fragment_sign_up1.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sign_up1).commit();
        }


        if (fragment_sign_up2 != null && fragment_sign_up2.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sign_up2).commit();
        }

        if (fragment_sign_up3 != null && fragment_sign_up3.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_up3).commit();
            fragment_sign_up3.getTeams();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_sign_up3, "fragment_sign_up3").addToBackStack("fragment_sign_up3").commit();
        }
        view.onDisplayFragmentStep3();


    }



    private void signUpWithoutImage() {
        view.showProgressDialog();
        String subscribe_method;
        if (signUpModel.isFromOtherUser()){
            subscribe_method = "invitation";
        }else {
            subscribe_method = "normal";
        }
        Api.getService(Tags.base_url).signUpWithoutImage2("Bearer "+userModel.getToken(),signUpModel.getName(),signUpModel.getNationality_id(),signUpModel.getGender(),signUpModel.getBirth_date(),subscribe_method,signUpModel.getAnother_user_code(),signUpModel.getLeague_id(),signUpModel.getTeam_id())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.hideProgressDialog();
                        if (response.isSuccessful()){

                            view.onSuccess(response.body());

                        }else {
                            view.hideProgressDialog();

                            try {
                                if (response.code()==403){
                                    view.onFailed(context.getString(R.string.sub_code_incorrect));
                                }else if (response.code()==404){
                                    view.onFailed(context.getString(R.string.sub_code_incorrect));

                                }else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.hideProgressDialog();

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));

                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }


                        }catch (Exception e){}

                    }
                });
    }

    private void signUpWithImage()
    {

        view.showProgressDialog();
        String subscribe_method;
        if (signUpModel.isFromOtherUser()){
            subscribe_method = "invitation";
        }else {
            subscribe_method = "normal";
        }
        RequestBody name_part = Common.getRequestBodyText(signUpModel.getName());
        RequestBody nationality_id_part = Common.getRequestBodyText(signUpModel.getNationality_id());
        RequestBody gender_part = Common.getRequestBodyText(signUpModel.getGender());
        RequestBody date_birth_part = Common.getRequestBodyText(signUpModel.getBirth_date());
        RequestBody method_part = Common.getRequestBodyText(subscribe_method);
        RequestBody another_code_part = Common.getRequestBodyText(signUpModel.getAnother_user_code());
        RequestBody league_id_part = Common.getRequestBodyText(signUpModel.getLeague_id());
        RequestBody team_part = Common.getRequestBodyText(signUpModel.getTeam_id());
        MultipartBody.Part logo = Common.getMultiPart(context, Uri.parse(signUpModel.getLocal_image()),"logo");
        Api.getService(Tags.base_url).signUpWithImage2("Bearer "+userModel.getToken(),name_part,nationality_id_part,gender_part,date_birth_part,method_part,another_code_part,league_id_part,team_part,logo)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.hideProgressDialog();
                        if (response.isSuccessful()){

                            view.onSuccess(response.body());

                        }else {
                            view.hideProgressDialog();

                            try {
                                if (response.code()==403){
                                    view.onFailed(context.getString(R.string.sub_code_incorrect));
                                }else if (response.code()==404){
                                    view.onFailed(context.getString(R.string.sub_code_incorrect));

                                }else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.hideProgressDialog();

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));

                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }


                        }catch (Exception e){}

                    }
                });

    }
}
