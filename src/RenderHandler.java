import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
    private BufferedImage view;
    private int[] pixels;

    public RenderHandler(int width, int height) {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Create a BufferedImage that can be changed
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData(); // Create an array for pixels
    }

    // Renders Pixel Array to screen
    public void render(Graphics graphics) {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }


    //Render an image to the array of pixels (call renderImage() first, then render())
    public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); // Create an int array for pixels in image
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                for (int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++) {
                    for (int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++) {
                        setPixel(imagePixels[j + i * image.getWidth()], (j * xZoom) + xZoomPos + xPosition, (i * yZoom) + yZoomPos + yPosition);
                    }
                }
            }
        }
    }

    private void setPixel(int pixel, int x, int y) {
        int pixelIndex = x + y * view.getWidth();
        if (pixels.length > pixelIndex) {
            pixels[pixelIndex] = pixel;
        }
    }
}
