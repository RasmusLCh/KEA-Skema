package proof.concept.microservice1.models;

/**
 * Used so our test example can show fancy colors, if the automatic setup is used.
 * The class basicly stores a color.
 * */
public class MyColor {
    private int red;
    private int green;
    private int blue;

    public MyColor(double red, double green, double blue){
        this.red = (int)red;
        this.green = (int)green;
        this.blue = (int)blue;
    }

    public MyColor(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
