package adeyds.noes.wisbejo.model;

import org.json.JSONObject;

public class Galeri {
    String nama, path;

    public String getNama() {
        return nama;
    }

    public String getPath() {
        return path;
    }

    public Galeri(JSONObject object){
        try {

            this.nama= object.getString("nama_pantai");
            this.path= object.getString("path_gambar");
        }catch (Exception e){e.printStackTrace();}
    }
}
