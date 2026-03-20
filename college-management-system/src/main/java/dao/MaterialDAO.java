package dao;

import java.util.ArrayList;
import java.util.List;
import model.Material;

public class MaterialDAO {

    private List<Material> materials = new ArrayList<>();

    public void addMaterial(Material m) {
        materials.add(m);
    }

    public List<Material> getAllMaterials() {
        return materials;
    }
}