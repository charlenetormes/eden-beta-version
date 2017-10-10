package subatom.eden_beta;

import java.util.ArrayList;

/**
 * Created by JSP on 10/6/2017.
 */

public class Emotion {
    public static boolean detect = false;
    public static ArrayList<Float> valence  = new ArrayList<>();
    public static ArrayList<Float> attention = new ArrayList<>();
    public static ArrayList<Float> brow_furrow = new ArrayList<>();
    public static ArrayList<Float> engagement = new ArrayList<>();

    public static void addValence(float f) { valence.add(f);}
    public static void addAttention(float f) { attention.add(f);}
    public static void addBrowFurrow(float f) { brow_furrow.add(f);}
    public static void addEngagement(float f) { engagement.add(f);}

    public static float getValence(int i) {return valence.get(i);}
    public static float getAttention(int i) {return attention.get(i);}
    public static float getBrowFurrow(int i) {return brow_furrow.get(i);}
    public static float getEngagement(int i) {return engagement.get(i);}

}
