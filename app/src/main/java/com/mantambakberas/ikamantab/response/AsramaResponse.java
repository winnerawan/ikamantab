package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mantambakberas.ikamantab.model.Asrama;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 10/23/16.
 */

public class AsramaResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("asrama")
    @Expose
    private List<Asrama> asrama = new ArrayList<Asrama>();

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
     * The asrama
     */
    public List<Asrama> getAsrama() {
        return asrama;
    }

    /**
     *
     * @param asrama
     * The asrama
     */
    public void setAsrama(List<Asrama> asrama) {
        this.asrama = asrama;
    }
}
