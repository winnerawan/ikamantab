package com.mantambakberas.ikamantab.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mantambakberas.ikamantab.model.Jurusan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 10/23/16.
 */

public class JurusanResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("jurusan")
    @Expose
    private List<Jurusan> jurusan = new ArrayList<Jurusan>();

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
     * The jurusan
     */
    public List<Jurusan> getJurusan() {
        return jurusan;
    }

    /**
     *
     * @param jurusan
     * The jurusan
     */
    public void setJurusan(List<Jurusan> jurusan) {
        this.jurusan = jurusan;
    }

}
