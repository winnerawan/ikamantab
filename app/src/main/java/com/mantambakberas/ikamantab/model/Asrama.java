package com.mantambakberas.ikamantab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan on 10/23/16.
 */

public class Asrama {

    @SerializedName("asrama_id")
    @Expose
    private Integer asramaId;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    /**
     *
     * @return
     * The asramaId
     */
    public Integer getAsramaId() {
        return asramaId;
    }

    /**
     *
     * @param asramaId
     * The asrama_id
     */
    public void setAsramaId(Integer asramaId) {
        this.asramaId = asramaId;
    }

    /**
     *
     * @return
     * The deskripsi
     */
    public String getDeskripsi() {
        return deskripsi;
    }

    /**
     *
     * @param deskripsi
     * The deskripsi
     */
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


}
