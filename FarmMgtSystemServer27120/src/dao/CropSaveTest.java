package dao;

import java.util.Date;

/**
 * Small test harness to reproduce crop save failures locally.
 * Usage (from IDE or command line): run main and pass a valid nationalId
 */
public class CropSaveTest {
    public static void main(String[] args) {
        String nid = args.length > 0 ? args[0] : "testnid"; // replace with real nid
        CropDao dao = new CropDao();
        model.Crop c = new model.Crop();
        c.setNationalId(nid);
        c.setCropName("TEST_CROP");
        c.setPlantingDate(new Date());
        c.setHarvestDate(new Date());
        c.setExpectedYield(10.0);
        c.setStatus("Planted");

        System.out.println("Attempting to save crop for nationalId='" + nid + "'");
        model.Crop saved = dao.saveCrop(c);
        if (saved != null) {
            System.out.println("Saved crop id=" + saved.getCropId());
        } else {
            System.out.println("Save returned null — check server logs for details.");
        }
    }
}
