package com.bridgelabz.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bridgeit on 20/6/16.
 */

public class LoginResponse {

    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Success")
    @Expose
    private Boolean success;

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The Status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The Success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
