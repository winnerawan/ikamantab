package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by winnerawan on 11/18/16.
 */

public class IsFriendResponse {

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("is_friend")
        @Expose
        private Boolean isFriend;
        @SerializedName("user_id")
        @Expose
        private Integer userId;

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
         * The isFriend
         */
        public Boolean getIsFriend() {
            return isFriend;
        }

        /**
         *
         * @param isFriend
         * The is_friend
         */
        public void setIsFriend(Boolean isFriend) {
            this.isFriend = isFriend;
        }

        /**
         *
         * @return
         * The userId
         */
        public Integer getUserId() {
            return userId;
        }

        /**
         *
         * @param userId
         * The user_id
         */
        public void setUserId(Integer userId) {
            this.userId = userId;
        }
}
