public class Rectangle {
    public int x; // x Position
    public int y; // y Position
    public int width; // Width
    public int height; // Height
    private int[] pixels; //


    // Constructors
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle() {
        this(0,0,0,0);
    }

    public void generateGraphics(int color) {
        pixels = new int[width*height];
        // Sets pixels array to the color
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = color;
            }
        }
    }

    // just so it doesn't take up a whole block
    public void generateGraphics(int borderWidth, int color) {
        pixels = new int[width*height];
        // Sets pixels array to color, only at the borders
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // this if statement checks if the current pixel being checked is at the border
                if ((y < borderWidth) || (x < borderWidth) || (x > width-borderWidth) || (y > height-borderWidth)) {
                    pixels[x + y * width] = color;
                }
            }
        }
    }

    public int[] getPixels() {
        if (this.pixels != null) {
            return pixels;
        } else {
            System.out.println("getPixels() failed because the pixel array wasn't there");
            return null;
        }
    }
}
