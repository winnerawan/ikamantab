package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mantambakberas.ikamantab.model.CR;
import com.mantambakberas.ikamantab.model.Message;
import com.mantambakberas.ikamantab.model.Msg;
import com.mantambakberas.ikamantab.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 11/17/16.
 */

public class MsgResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("messages")
    @Expose
    private List<Msg> messages = new ArrayList<Msg>();


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
     * The messages
     */
    public List<Msg> getMessages() {
        return messages;
    }

    /**
     *
     * @param messages
     * The messages
     */
    public void setMessages(List<Msg> messages) {
        this.messages = messages;
    }


}
