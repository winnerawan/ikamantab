package com.mantambakberas.ikamantab.response;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mantambakberas.ikamantab.model.Friend;

/**
 * Created by winnerawan on 11/18/16.
 */

public class FriendsResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("friends")
    @Expose
    private List<Friend> friends = new ArrayList<Friend>();

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
     * The friends
     */
    public List<Friend> getFriends() {
        return friends;
    }

    /**
     *
     * @param friends
     * The friends
     */
    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
