package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mantambakberas.ikamantab.model.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 10/17/16.
 */

public class UsersResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("users")
    @Expose
    private List<Users> users = new ArrayList<Users>();

    /**
     *
     * @return
     * The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     *
     * @return
     * The users
     */
    public List<Users> getUsers() {
        return users;
    }

    /**
     *
     * @param users
     * The users
     */
    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
