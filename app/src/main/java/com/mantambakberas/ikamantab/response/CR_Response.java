/*
 * Copyright (c) 2016. Ikamantab (Ikatan Keluarga Alumni Man Tambakberas).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file   except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License..
 */

package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mantambakberas.ikamantab.model.CR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 11/12/16.
 */

public class CR_Response {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("chat_rooms")
    @Expose
    private List<CR> chatRooms = new ArrayList<CR>();

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
     * The chatRooms
     */
    public List<CR> getChatRooms() {
        return chatRooms;
    }

    /**
     *
     * @param chatRooms
     * The chat_rooms
     */
    public void setChatRooms(List<CR> chatRooms) {
        this.chatRooms = chatRooms;
    }
}
