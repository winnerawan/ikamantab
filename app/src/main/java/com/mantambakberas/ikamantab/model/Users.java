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
 * Created by winnerawan on 10/17/16.
 */

public class Users {

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
    @SerializedName("telp")
    @Expose
    private String telp;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("angkatan")
    @Expose
    private Integer angkatan;
    @SerializedName("jurusan")
    @Expose
    private String jurusan;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("profesi")
    @Expose
    private String profesi;
    @SerializedName("minat_profesi")
    @Expose
    private String minatProfesi;
    @SerializedName("referensi_rekomendasi")
    @Expose
    private String referensiRekomendasi;
    @SerializedName("keahlian")
    @Expose
    private String keahlian;
    @SerializedName("penghargaan")
    @Expose
    private String penghargaan;
    @SerializedName("asrama")
    @Expose
    private String asrama;
    @SerializedName("jenis_kelamin")
    @Expose
    private String jenisKelamin;


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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfesi() {
        return profesi;
    }

    public void setProfesi(String profesi) {
        this.profesi = profesi;
    }

    public String getMinatProfesi() {
        return minatProfesi;
    }

    public void setMinatProfesi(String minatProfesi) {
        this.minatProfesi = minatProfesi;
    }

    public String getReferensiRekomendasi() {
        return referensiRekomendasi;
    }

    public void setReferensiRekomendasi(String referensiRekomendasi) {
        this.referensiRekomendasi = referensiRekomendasi;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getPenghargaan() {
        return penghargaan;
    }

    public void setPenghargaan(String penghargaan) {
        this.penghargaan = penghargaan;
    }

    public String getAsrama() {
        return asrama;
    }

    public void setAsrama(String asrama) {
        this.asrama = asrama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}
