package alc.ugadining;

import java.util.ArrayList;
import java.util.List;

public class ColorPalette {

    private static List<Palette> palettes;

    static {
        palettes = new ArrayList<>();
        Palette sunset = new Palette("Sunset",0xffffa600, 0xffe64c37, 0xff003f5c);
        palettes.add(sunset);
    }

    public static Palette getPalette(String name) {
        for (Palette p : palettes) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}