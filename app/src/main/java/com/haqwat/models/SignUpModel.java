package com.haqwat.models;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.haqwat.BR;
import com.haqwat.R;

import java.io.Serializable;

public class SignUpModel extends BaseObservable implements Serializable {
    private String email;
    private String password;
    private String re_password;
    private String user_type;
    private String name;
    private String gender;
    private String birth_date;
    private String nationality_id;
    private String league_id;
    private String team_id;
    private String local_image;
    private String social_image;
    private String another_user_code;
    private boolean isFromOtherUser;
    public String withSocial="no";


    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_re_password = new ObservableField<>();
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_birth_date = new ObservableField<>();
    public ObservableField<String> error_another_user = new ObservableField<>();


    public SignUpModel() {
        email = "";
        password="";
        re_password="";
        user_type = withSocial;
        name="";
        gender="";
        birth_date="";
        nationality_id="0";
        league_id="0";
        team_id="0";
        local_image="";
        social_image="";
        another_user_code="";
        isFromOtherUser =false;


    }

    public boolean isStep1Valid(Context context){
        if (!email.isEmpty()&&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()&&
                !password.isEmpty()&&
                password.length()>=6&&
                password.equals(re_password)
        ){
            error_email.set(null);
            error_password.set(null);
            error_re_password.set(null);
            return true;
        }else {
            if (email.isEmpty()){
                error_email.set(context.getString(R.string.field_required));
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                error_email.set(context.getString(R.string.inv_email));
            }else {
                error_email.set(null);

            }

            if (password.isEmpty()){
                error_password.set(context.getString(R.string.field_required));
            }else if (password.length()<6){
                error_password.set(context.getString(R.string.password_short));

            }else {
                error_password.set(null);
                if (re_password.isEmpty()){
                    error_re_password.set(context.getString(R.string.field_required));

                }else if (!re_password.equals(password)){
                    error_re_password.set(context.getString(R.string.password_not_match));

                }else {
                    error_re_password.set(null);
                }
            }


            return false;
        }
    }

    public boolean isStep2Valid(Context context){
        if (!name.isEmpty()&&
                !nationality_id.equals("0")&&
                !gender.isEmpty()&&
                !birth_date.isEmpty()
        ){
            error_name.set(null);
            error_birth_date.set(null);
            if (isFromOtherUser){
                if (!another_user_code.isEmpty()){
                    error_another_user.set(null);
                    return true;
                }else {
                    error_another_user.set(context.getString(R.string.field_required));
                    return false;
                }
            }else {
                return true;

            }
        }else {
            if (name.isEmpty()){
                error_name.set(context.getString(R.string.field_required));
            }else {
                error_name.set(null);

            }
            if (nationality_id.equals("0")){
                Toast.makeText(context, R.string.ch_nationality, Toast.LENGTH_SHORT).show();
            }

            if (gender.isEmpty()){
                Toast.makeText(context, R.string.ch_gender, Toast.LENGTH_SHORT).show();

            }

            if (birth_date.isEmpty()){
                error_birth_date.set(context.getString(R.string.field_required));
            }else {
                error_birth_date.set(null);

            }

            if (isFromOtherUser){
                if (!another_user_code.isEmpty()){
                    error_another_user.set(null);
                }else {
                    error_another_user.set(context.getString(R.string.field_required));
                }
            }

            return false;
        }
    }

    public boolean isStep3Valid(Context context){
        if (!league_id.equals("0")){
            return true;
        }else {
            Toast.makeText(context, R.string.ch_league, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isStep4Valid(Context context){
        if (!team_id.equals("0")){
            return true;
        }else {
            Toast.makeText(context, R.string.ch_team, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);

    }
    @Bindable
    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
        notifyPropertyChanged(BR.re_password);

    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(String nationality_id) {
        this.nationality_id = nationality_id;
    }

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String league_id) {
        this.league_id = league_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getLocal_image() {
        return local_image;
    }

    public void setLocal_image(String local_image) {
        this.local_image = local_image;
    }

    public String getSocial_image() {
        return social_image;
    }

    public void setSocial_image(String social_image) {
        this.social_image = social_image;
    }

    public boolean isFromOtherUser() {
        return isFromOtherUser;
    }

    public void setFromOtherUser(boolean fromOtherUser) {
        isFromOtherUser = fromOtherUser;
    }

    @Bindable
    public String getAnother_user_code() {
        return another_user_code;
    }

    public void setAnother_user_code(String another_user_code) {
        this.another_user_code = another_user_code;
        notifyPropertyChanged(BR.another_user_code);
    }
}
