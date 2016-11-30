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

package com.mantambakberas.ikamantab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan on 10/19/16.
 */

public class Me {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gcm")
    @Expose
    private String gcm;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("profesi")
    @Expose
    private String profesi;
    @SerializedName("keahlian")
    @Expose
    private String keahlian;
    @SerializedName("penghargaan")
    @Expose
    private String penghargaan;
    @SerializedName("minat_profesi")
    @Expose
    private String minatProfesi;
    @SerializedName("referensi_rekomendasi")
    @Expose
    private String referensiRekomendasi;
    @SerializedName("telp")
    @Expose
    private String telp;
    @SerializedName("jenis_kelamin")
    @Expose
    private String jenisKelamin;
    @SerializedName("angkatan")
    @Expose
    private Integer angkatan;
    @SerializedName("jurusan")
    @Expose
    private String jurusan;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The gcm
     */
    public String getGcm() {
        return gcm;
    }

    /**
     *
     * @param gcm
     * The gcm
     */
    public void setGcm(String gcm) {
        this.gcm = gcm;
    }

    /**
     *
     * @return
     * The foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     *
     * @param foto
     * The foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     *
     * @return
     * The bio
     */
    public String getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     * The bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     *
     * @return
     * The profesi
     */
    public String getProfesi() {
        return profesi;
    }

    /**
     *
     * @param profesi
     * The profesi
     */
    public void setProfesi(String profesi) {
        this.profesi = profesi;
    }

    /**
     *
     * @return
     * The keahlian
     */
    public String getKeahlian() {
        return keahlian;
    }

    /**
     *
     * @param keahlian
     * The keahlian
     */
    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    /**
     *
     * @return
     * The penghargaan
     */
    public String getPenghargaan() {
        return penghargaan;
    }

    /**
     *
     * @param penghargaan
     * The penghargaan
     */
    public void setPenghargaan(String penghargaan) {
        this.penghargaan = penghargaan;
    }

    /**
     *
     * @return
     * The minatProfesi
     */
    public String getMinatProfesi() {
        return minatProfesi;
    }

    /**
     *
     * @param minatProfesi
     * The minat_profesi
     */
    public void setMinatProfesi(String minatProfesi) {
        this.minatProfesi = minatProfesi;
    }

    /**
     *
     * @return
     * The referensiRekomendasi
     */
    public String getReferensiRekomendasi() {
        return referensiRekomendasi;
    }

    /**
     *
     * @param referensiRekomendasi
     * The referensi_rekomendasi
     */
    public void setReferensiRekomendasi(String referensiRekomendasi) {
        this.referensiRekomendasi = referensiRekomendasi;
    }

    /**
     *
     * @return
     * The telp
     */
    public String getTelp() {
        return telp;
    }

    /**
     *
     * @param telp
     * The telp
     */
    public void setTelp(String telp) {
        this.telp = telp;
    }

    /**
     *
     * @return
     * The jenisKelamin
     */
    public String getJenisKelamin() {
        return jenisKelamin;
    }

    /**
     *
     * @param jenisKelamin
     * The jenis_kelamin
     */
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    /**
     *
     * @return
     * The angkatan
     */
    public Integer getAngkatan() {
        return angkatan;
    }

    /**
     *
     * @param angkatan
     * The angkatan
     */
    public void setAngkatan(Integer angkatan) {
        this.angkatan = angkatan;
    }

    /**
     *
     * @return
     * The jurusan
     */
    public String getJurusan() {
        return jurusan;
    }

    /**
     *
     * @param jurusan
     * The jurusan
     */
    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

}
