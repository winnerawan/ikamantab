package com.mantambakberas.ikamantab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan on 10/23/16.
 */

public class Jurusan {

    @SerializedName("jurusan_id")
    @Expose
    private Integer jurusanId;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    /**
     *
     * @return
     * The jurusanId
     */
    public Integer getJurusanId() {
        return jurusanId;
    }

    /**
     *
     * @param jurusanId
     * The jurusan_id
     */
    public void setJurusanId(Integer jurusanId) {
        this.jurusanId = jurusanId;
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
