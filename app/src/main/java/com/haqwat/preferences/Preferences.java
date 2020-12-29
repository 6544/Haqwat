package com.haqwat.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.google.gson.reflect.TypeToken;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;
import com.haqwat.tags.Tags;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Preferences {

    private static Preferences instance = null;

    private Preferences() {
    }

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }

    public void create_update_userdata(Context context, UserModel userModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = gson.toJson(userModel);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_data", user_data);
        editor.apply();
        create_update_session(context, Tags.session_login);

    }

    public UserModel getUserData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = preferences.getString("user_data", "");
        UserModel userModel = gson.fromJson(user_data, UserModel.class);
        return userModel;
    }

    public void create_update_session(Context context, String session) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state", session);
        editor.apply();


    }


    public String getSession(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("state", Tags.session_logout);
        return session;
    }

    public void createAccount(Context context, AccountsModel accountsModel) {
        SharedPreferences preferences = context.getSharedPreferences("accounts", Context.MODE_PRIVATE);
        String gson = preferences.getString("data", "");
        List<AccountsModel> accountsModelList = new Gson().fromJson(gson, new TypeToken<List<AccountsModel>>() {
        }.getType());
        if (accountsModelList == null) {
            accountsModelList = new ArrayList<>();
            accountsModelList.add(accountsModel);
        } else {
            if (isUserEmailAccountFound(accountsModelList, accountsModel)) {
                int pos = getAccountPos(accountsModelList,accountsModel);
                accountsModelList.set(pos, accountsModel);


            }else {

                accountsModelList.add(accountsModel);

            }


        }

        String newGson = new Gson().toJson(accountsModelList);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data", newGson);
        editor.apply();
    }


    private boolean isUserEmailAccountFound(List<AccountsModel> accountsModelList, AccountsModel accountsModel) {
        if (accountsModelList != null && accountsModelList.size() > 0) {

            for (int index = 0; index < accountsModelList.size(); index++) {
                AccountsModel model = accountsModelList.get(index);
                if (model.getEmail().equals(accountsModel.getEmail())) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getAccountPos(List<AccountsModel> accountsModelList, AccountsModel accountsModel) {
        int pos = -1;
        for (int index = 0; index < accountsModelList.size(); index++) {
            AccountsModel model = accountsModelList.get(index);

            if (model.getEmail().equals(accountsModel.getEmail())) {
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    public List<AccountsModel> getAccounts(Context context) {
        List<AccountsModel> accountsModelList;
        SharedPreferences preferences = context.getSharedPreferences("accounts", Context.MODE_PRIVATE);
        String gson = preferences.getString("data", "");
        accountsModelList = new Gson().fromJson(gson, new TypeToken<List<AccountsModel>>() {
        }.getType());
        if (accountsModelList == null) {
            accountsModelList = new ArrayList<>();
        }
        return accountsModelList;
    }

    public void removeAccount(Context context, int pos) {
        List<AccountsModel> accountsModelList;
        SharedPreferences preferences = context.getSharedPreferences("accounts", Context.MODE_PRIVATE);
        String gson = preferences.getString("data", "");
        accountsModelList = new Gson().fromJson(gson, new TypeToken<List<AccountsModel>>() {
        }.getType());
        if (accountsModelList != null && accountsModelList.size() > 0) {
            accountsModelList.remove(pos);
            String newGson = new Gson().toJson(accountsModelList);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("data", newGson);
            editor.apply();
        }


    }
    public AccountsModel getMyCurrentAccount(Context context ,String email){
        AccountsModel accountsModel1=null;
        List<AccountsModel> accountsModelList = getAccounts(context);
        if (accountsModelList!=null&&accountsModelList.size()>0){
            for (int index = 0; index < accountsModelList.size(); index++) {
                AccountsModel model = accountsModelList.get(index);

                if (model.getEmail().equals(email)) {
                   accountsModel1 = model;
                   return accountsModel1;
                }
            }
        }


        return accountsModel1;
    }

    public void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
        create_update_session(context, Tags.session_logout);
    }


}
