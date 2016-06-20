package com.bridgelabz.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgeit on 18/6/16.
 */

public class UserInfoList {

    @SerializedName("result")
    @Expose
    private List<UserInfo> result = new ArrayList<UserInfo>();

    /**
     *
     * @return
     * The result
     */
    public List<UserInfo> getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(List<UserInfo> result) {
        this.result = result;
    }
}
