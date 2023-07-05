package com.example.kulinerkreasi.entities;

public class Berita {
    private String judul_berita;
    private String desc_berita;
    private String imageUrl;

    public String getJudul_berita() {
        return judul_berita;
    }

    public void setJudul_berita(String judul_berita) {
        this.judul_berita = judul_berita;
    }

    public String getDesc_berita() {
        return desc_berita;
    }

    public void setDesc_berita(String desc_berita) {
        this.desc_berita = desc_berita;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Berita(String judul_berita, String desc_berita, String imageUrl) {
        this.judul_berita = judul_berita;
        this.desc_berita = desc_berita;
        this.imageUrl = imageUrl;
    }

}
