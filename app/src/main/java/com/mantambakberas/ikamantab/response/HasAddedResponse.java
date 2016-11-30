package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan on 11/18/16.
 */

public class HasAddedResponse {

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("is_added")
        @Expose
        private Boolean isAdded;
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
         * The isAdded
         */
        public Boolean getIsAdded() {
                return isAdded;
        }

        /**
         *
         * @param isAdded
         * The is_added
         */
        public void setIsAdded(Boolean isAdded) {
                this.isAdded = isAdded;
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
