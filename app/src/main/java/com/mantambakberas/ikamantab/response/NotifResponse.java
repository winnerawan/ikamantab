package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mantambakberas.ikamantab.model.ResultNotif;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 11/19/16.
 */

public class NotifResponse {

    @SerializedName("multicast_id")
    @Expose
    private Long multicastId;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("failure")
    @Expose
    private Integer failure;
    @SerializedName("canonical_ids")
    @Expose
    private Integer canonicalIds;
    @SerializedName("results")
    @Expose
    private List<ResultNotif> results = new ArrayList<ResultNotif>();

    /**
     *
     * @return
     * The multicastId
     */
    public Long getMulticastId() {
        return multicastId;
    }

    /**
     *
     * @param multicastId
     * The multicast_id
     */
    public void setMulticastId(Long multicastId) {
        this.multicastId = multicastId;
    }

    /**
     *
     * @return
     * The success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The failure
     */
    public Integer getFailure() {
        return failure;
    }

    /**
     *
     * @param failure
     * The failure
     */
    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    /**
     *
     * @return
     * The canonicalIds
     */
    public Integer getCanonicalIds() {
        return canonicalIds;
    }

    /**
     *
     * @param canonicalIds
     * The canonical_ids
     */
    public void setCanonicalIds(Integer canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    /**
     *
     * @return
     * The results
     */
    public List<ResultNotif> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<ResultNotif> results) {
        this.results = results;
    }
}
