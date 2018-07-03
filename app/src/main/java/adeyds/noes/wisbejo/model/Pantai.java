package adeyds.noes.wisbejo.model;

import org.json.JSONObject;

public class Pantai {
         String id_pantai;
    String nama;
    String deskripsi;
    String lokasi;
    String htm;
    String cover;

    public String getCover() {
        return cover;
    }

    public String getKontak() {
        return kontak;
    }

    String kontak;

    public String getId_pantai() {
        return id_pantai;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getHtm() {
        return htm;
    }

    public Pantai(JSONObject object){
        try {

            this.id_pantai = object.getString("id_pantai");
            this.nama = object.getString("nama");
            this.deskripsi = object.getString("deskripsi");
            this.htm = object.getString("htm");
            this.lokasi = object.getString("lokasi");
            this.cover = object.getString("cover");
            this.kontak = object.getString("kontak");

        }catch (Exception e){e.printStackTrace();}
    }
}
