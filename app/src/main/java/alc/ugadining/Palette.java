package alc.ugadining;

public class Palette {
    private String name;
    private int[] colors;

    public Palette(String name, int... colors) {
        this.name = name;
        this.colors = colors;
    }

    public void setColors(int... colors) {
        this.colors = colors;
    }

    public int[] getColors() {
        return colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}