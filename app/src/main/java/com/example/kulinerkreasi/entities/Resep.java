package com.example.kulinerkreasi.entities;

import android.media.Image;

public class Resep {
    private String judul_resep;
    private String minimal;
    private String maksimal;
    private String bahan;
    private String langkah;
    private String estimasi;
    private String imageUrl;

    public Resep(String judul_resep, String minimal, String maksimal, String bahan, String langkah, String estimasi, String imageUrl) {
        this.judul_resep = judul_resep;
        this.minimal = minimal;
        this.maksimal = maksimal;
        this.bahan = bahan;
        this.langkah = langkah;
        this.estimasi = estimasi;
        this.imageUrl = imageUrl;
    }

    public String getJudul_resep() {
        return judul_resep;
    }

    public void setJudul_resep(String judul_resep) {
        this.judul_resep = judul_resep;
    }

    public String getMinimal() {
        return minimal;
    }

    public void setMinimal(String minimal) {
        this.minimal = minimal;
    }

    public String getMaksimal() {
        return maksimal;
    }

    public void setMaksimal(String maksimal) {
        this.maksimal = maksimal;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getLangkah() {
        return langkah;
    }

    public void setLangkah(String langkah) {
        this.langkah = langkah;
    }

    public String getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(String estimasi) {
        this.estimasi = estimasi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
