package com.haqwat.mvp.sign_up_mpv;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.haqwat.R;
import com.haqwat.models.SignUpModel;
import com.haqwat.ui.activity_complete_sign_up.fragments.Fragment_Sign_Up1;
import com.haqwat.ui.activity_complete_sign_up.fragments.Fragment_Sign_Up2;
import com.haqwat.ui.activity_complete_sign_up.fragments.Fragment_Sign_Up3;

public class SignUpPresenter {
    private Context context;
    private SignUpView view;
    private FragmentManager fragmentManager;
    private Fragment_Sign_Up1 fragment_sign_up1;
    private Fragment_Sign_Up2 fragment_sign_up2;
    private Fragment_Sign_Up3 fragment_sign_up3;
    private SignUpModel signUpModel;
    public static int next = 1,previous=0;


    public SignUpPresenter(Context context, SignUpView view, FragmentManager fragmentManager,SignUpModel signUpModel) {
        this.context = context;
        this.view = view;
        this.fragmentManager = fragmentManager;

        if (signUpModel!=null){
            this.signUpModel = signUpModel;
        }else {
            this.signUpModel = new SignUpModel();

        }
        displayFragmentStep1();
    }

    public void manageFragments(int next_previous){
        if (fragment_sign_up1!=null&&fragment_sign_up1.isAdded()&&fragment_sign_up1.isVisible()){
            signUpModel = fragment_sign_up1.signUpModel;
            if (next_previous==next){
                if (signUpModel.isStep2Valid(context)){
                    displayFragmentStep2();

                }
            }else {
                view.onBack();
            }
        }
        else if (fragment_sign_up2 !=null&& fragment_sign_up2.isAdded()&& fragment_sign_up2.isVisible()){

            signUpModel = fragment_sign_up2.signUpModel;

            if (next_previous==next){
                if (signUpModel.isStep3Valid(context)){
                    displayFragmentStep3();

                }
            }else {
                displayFragmentStep1();
            }
        }
        else if (fragment_sign_up3 !=null&& fragment_sign_up3.isAdded()&& fragment_sign_up3.isVisible()){

            if (next_previous==next){
                if (signUpModel.isStep4Valid(context))
                signUp();

            }else {
                displayFragmentStep2();
            }
        }
    }



    private void displayFragmentStep1()
    {
        if (fragment_sign_up1==null){
            fragment_sign_up1 = Fragment_Sign_Up1.newInstance(signUpModel);
        }


        if (fragment_sign_up2 !=null&& fragment_sign_up2.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_sign_up2).commit();
        }

        if (fragment_sign_up3 !=null&& fragment_sign_up3.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_sign_up3).commit();
        }

        if (fragment_sign_up1!=null&&fragment_sign_up1.isAdded()){
            fragmentManager.beginTransaction().show(fragment_sign_up1).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment_sign_up1,"fragment_sign_up1").addToBackStack("fragment_sign_up1").commit();
        }
        view.onDisplayFragmentStep1();
    }
    private void displayFragmentStep2()
    {
        if (fragment_sign_up2 ==null){
            fragment_sign_up2 = Fragment_Sign_Up2.newInstance(signUpModel);
        }

        if (fragment_sign_up1!=null&&fragment_sign_up1.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_sign_up1).commit();
        }


        if (fragment_sign_up3 !=null&& fragment_sign_up3.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_sign_up3).commit();
        }

        if (fragment_sign_up2 !=null&& fragment_sign_up2.isAdded()){
            fragmentManager.beginTransaction().show(fragment_sign_up2).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_sign_up2,"fragment_sign_up2").addToBackStack("fragment_sign_up2").commit();
        }
        view.onDisplayFragmentStep2();

    }
    private void displayFragmentStep3()
    {

        if (fragment_sign_up3 ==null){
            fragment_sign_up3 = Fragment_Sign_Up3.newInstance(signUpModel);
        }

        if (fragment_sign_up1!=null&&fragment_sign_up1.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_sign_up1).commit();
        }


        if (fragment_sign_up2 !=null&& fragment_sign_up2.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_sign_up2).commit();
        }

        if (fragment_sign_up3 !=null&& fragment_sign_up3.isAdded()){
            fragmentManager.beginTransaction().show(fragment_sign_up3).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_sign_up3,"fragment_sign_up3").addToBackStack("fragment_sign_up3").commit();
        }
        view.onDisplayFragmentStep3();


    }


    private void signUp() {


    }
}
