package Data;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public final class DeserializeData {
    private static LocationData locationData;
    private static FemaleNamesData femaleNamesData;
    private static MaleNamesData maleNamesData;
    private static SurnamesData surnamesData;

    private static DeserializeData dataCache = null;

    private DeserializeData() {
        try {
            Gson gson = new Gson();

            Reader locationsReader = new FileReader("json/locations.json");
            locationData = (LocationData)gson.fromJson(locationsReader, LocationData.class);

            Reader fNamesReader = new FileReader("json/fnames.json");
            femaleNamesData = (FemaleNamesData) gson.fromJson(fNamesReader, FemaleNamesData.class);

            Reader mNamesReader = new FileReader("json/mnames.json");
            maleNamesData = (MaleNamesData) gson.fromJson(mNamesReader, MaleNamesData.class);

            Reader sNamesReader = new FileReader("json/snames.json");
            surnamesData = (SurnamesData) gson.fromJson(sNamesReader, SurnamesData.class);

            try {
                locationsReader.close();
                fNamesReader.close();
                mNamesReader.close();
                sNamesReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DeserializeData getDataCache() {
        if (dataCache == null) {
            dataCache = new DeserializeData();
        }
        return dataCache;
    }

    public static LocationData getLocationData() {
        return locationData;
    }

    public static void setLocationData(LocationData locationData) {
        DeserializeData.locationData = locationData;
    }

    public static FemaleNamesData getFemaleNamesData() {
        return femaleNamesData;
    }

    public static void setFemaleNamesData(FemaleNamesData femaleNamesData) {
        DeserializeData.femaleNamesData = femaleNamesData;
    }

    public static MaleNamesData getMaleNamesData() {
        return maleNamesData;
    }

    public static void setMaleNamesData(MaleNamesData maleNamesData) {
        DeserializeData.maleNamesData = maleNamesData;
    }

    public static SurnamesData getSurnamesData() {
        return surnamesData;
    }

    public static void setSurnamesData(SurnamesData surnamesData) {
        DeserializeData.surnamesData = surnamesData;
    }
}
