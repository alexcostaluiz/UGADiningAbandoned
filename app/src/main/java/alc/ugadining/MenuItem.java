package alc.ugadining;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MenuItem implements Parcelable {

    public enum ItemType {VEGAN, MEATLESS, LOW_FAT, TASTE_OF_HOME, BONE_I_FIED}

    private int id;
    private String name;
    private String type;
    private String servingName;
    private String servingSize;
    private float numServings = 1f;
    private int calories;
    private int caloriesFromFat;
    private int totalFat;
    private int totalFatDv;
    private int satFat;
    private int satFatDv;
    private int transFat;
    private int transFatDv;
    private int cholesterol;
    private int cholesterolDv;
    private int sodium;
    private int sodiumDv;
    private int totalCarb;
    private int totalCarbDv;
    private int dietaryFiber;
    private int dietaryFiberDv;
    private int sugars;
    private int sugarsDv;
    private int protein;
    private int proteinDv;
    private String allergens;
    private String ingredients;

    public MenuItem(int id, String name, String type, String servingName, String servingSize,
                    int calories, int caloriesFromFat, int totalFat, int totalFatDv, int satFat,
                    int satFatDv, int transFat, int transFatDv, int cholesterol, int cholesterolDv,
                    int sodium, int sodiumDv, int totalCarb, int totalCarbDv, int dietaryFiber,
                    int dietaryFiberDv, int sugars, int sugarsDv, int protein, int proteinDv,
                    String allergens, String ingredients) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.servingName = servingName;
        this.servingSize = servingSize;
        this.calories = calories;
        this.caloriesFromFat = caloriesFromFat;
        this.totalFat = totalFat;
        this.totalFatDv = totalFatDv;
        this.satFat = satFat;
        this.satFatDv = satFatDv;
        this.transFat = transFat;
        this.transFatDv = transFatDv;
        this.cholesterol = cholesterol;
        this.cholesterolDv = cholesterolDv;
        this.sodium = sodium;
        this.sodiumDv = sodiumDv;
        this.totalCarb = totalCarb;
        this.totalCarbDv = totalCarbDv;
        this.dietaryFiber = dietaryFiber;
        this.dietaryFiberDv = dietaryFiberDv;
        this.sugars = sugars;
        this.sugarsDv = sugarsDv;
        this.protein = protein;
        this.proteinDv = proteinDv;
        this.allergens = allergens;
        this.ingredients = ingredients;

        /*String[] types = type.split(",");
        this.type = new ItemType[types.length];
        for (int i = 0; i < types.length; i++) {
            String s = types[i];
            s = s.toUpperCase().replaceAll(" ", "_")
                    .replaceAll("-", "_");
            this.type[i] = ItemType.valueOf(s);
        }*/
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServingName() {
        return servingName;
    }

    public void setServingName(String servingName) {
        this.servingName = servingName;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public float getNumServings() {
        return numServings;
    }

    public void setNumServings(float numServings) {
        this.numServings = numServings;
    }

    public int getCalories() {
        return (int) (calories * numServings);
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCaloriesFromFat() {
        return (int) (caloriesFromFat * numServings);
    }

    public void setCaloriesFromFat(int caloriesFromFat) {
        this.caloriesFromFat = caloriesFromFat;
    }

    public int getTotalFat() {
        return (int) (totalFat * numServings);
    }

    public void setTotalFat(int totalFat) {
        this.totalFat = totalFat;
    }

    public int getTotalFatDv() {
        return (int) (totalFatDv * numServings);
    }

    public void setTotalFatDv(int totalFatDv) {
        this.totalFatDv = totalFatDv;
    }

    public int getSatFat() {
        return (int) (satFat * numServings);
    }

    public void setSatFat(int satFat) {
        this.satFat = satFat;
    }

    public int getSatFatDv() {
        return (int) (satFatDv * numServings);
    }

    public void setSatFatDv(int satFatDv) {
        this.satFatDv = satFatDv;
    }

    public int getTransFat() {
        return (int) (transFat * numServings);
    }

    public void setTransFat(int transFat) {
        this.transFat = transFat;
    }

    public int getTransFatDv() {
        return (int) (transFatDv * numServings);
    }

    public void setTransFatDv(int transFatDv) {
        this.transFatDv = transFatDv;
    }

    public int getCholesterol() {
        return (int) (cholesterol * numServings);
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getCholesterolDv() {
        return (int) (cholesterolDv * numServings);
    }

    public void setCholesterolDv(int cholesterolDv) {
        this.cholesterolDv = cholesterolDv;
    }

    public int getSodium() {
        return (int) (sodium * numServings);
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getSodiumDv() {
        return (int) (sodiumDv * numServings);
    }

    public void setSodiumDv(int sodiumDv) {
        this.sodiumDv = sodiumDv;
    }

    public int getTotalCarb() {
        return (int) (totalCarb * numServings);
    }

    public void setTotalCarb(int totalCarb) {
        this.totalCarb = totalCarb;
    }

    public int getTotalCarbDv() {
        return (int) (totalCarbDv * numServings);
    }

    public void setTotalCarbDv(int totalCarbDv) {
        this.totalCarbDv = totalCarbDv;
    }

    public int getDietaryFiber() {
        return (int) (dietaryFiber * numServings);
    }

    public void setDietaryFiber(int dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public int getDietaryFiberDv() {
        return (int) (dietaryFiberDv * numServings);
    }

    public void setDietaryFiberDv(int dietaryFiberDv) {
        this.dietaryFiberDv = dietaryFiberDv;
    }

    public int getSugars() {
        return (int) (sugars * numServings);
    }

    public void setSugars(int sugars) {
        this.sugars = sugars;
    }

    public int getSugarsDv() {
        return (int) (sugarsDv * numServings);
    }

    public void setSugarsDv(int sugarsDv) {
        this.sugarsDv = sugarsDv;
    }

    public int getProtein() {
        return (int) (protein * numServings);
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getProteinDv() {
        return (int) (proteinDv * numServings);
    }

    public void setProteinDv(int proteinDv) {
        this.proteinDv = proteinDv;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(servingName);
        dest.writeString(servingSize);
        dest.writeFloat(numServings);
        dest.writeInt(calories);
        dest.writeInt(caloriesFromFat);
        dest.writeInt(totalFat);
        dest.writeInt(totalFatDv);
        dest.writeInt(satFat);
        dest.writeInt(satFatDv);
        dest.writeInt(transFat);
        dest.writeInt(transFatDv);
        dest.writeInt(cholesterol);
        dest.writeInt(cholesterolDv);
        dest.writeInt(sodium);
        dest.writeInt(sodiumDv);
        dest.writeInt(totalCarb);
        dest.writeInt(totalCarbDv);
        dest.writeInt(dietaryFiber);
        dest.writeInt(dietaryFiberDv);
        dest.writeInt(sugars);
        dest.writeInt(sugarsDv);
        dest.writeInt(protein);
        dest.writeInt(proteinDv);
        dest.writeString(allergens);
        dest.writeString(ingredients);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel source) {
            return new MenuItem(source);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    protected MenuItem(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
        this.type = source.readString();
        this.servingName = source.readString();
        this.servingSize = source.readString();
        this.numServings = source.readFloat();
        this.calories = source.readInt();
        this.caloriesFromFat = source.readInt();
        this.totalFat = source.readInt();
        this.totalFatDv = source.readInt();
        this.satFat = source.readInt();
        this.satFatDv = source.readInt();
        this.transFat = source.readInt();
        this.transFatDv = source.readInt();
        this.cholesterol = source.readInt();
        this.cholesterolDv = source.readInt();
        this.sodium = source.readInt();
        this.sodiumDv = source.readInt();
        this.totalCarb = source.readInt();
        this.totalCarbDv = source.readInt();
        this.dietaryFiber = source.readInt();
        this.dietaryFiberDv = source.readInt();
        this.sugars = source.readInt();
        this.sugarsDv = source.readInt();
        this.protein = source.readInt();
        this.proteinDv = source.readInt();
        this.allergens = source.readString();
        this.ingredients = source.readString();
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
