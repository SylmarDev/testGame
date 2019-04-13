import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
    private BufferedImage view;
    private int[] pixels;
    private Rectangle camera;

    public RenderHandler(int width, int height) {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Create a BufferedImage that can be changed
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData(); // Create an array for pixels
        camera = new Rectangle(0,0, width, height);

        camera.x = -100;
        camera.y = -30;
    }

    // Renders Pixel Array to screen
    public void render(Graphics graphics) {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }


    //Render an image to the array of pixels (call renderImage() first, then render())
    public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); // Create an int array for pixels in image
        renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom);
    }

    public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom) {
        if (rectangle.getPixels() != null) {
            int[] rectPixels = rectangle.getPixels();
            renderArray(rectPixels, rectangle.width, rectangle.height, rectangle.x, rectangle.y, xZoom, yZoom);
        } else {
            System.out.println("renderRectangle() was called, but failed because the rectangle doesn't have a pixels field");
        }
    }

    private void setPixel(int pixel, int x, int y) {
        if(x >= camera.x && y >= camera.y && x <= camera.x + camera.width && y <= camera.y + camera.height) {
            int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
            if (pixels.length > pixelIndex) { // this is just to ensure we never get Array.Outofbounds exceptions because those are frustrating
                pixels[pixelIndex] = pixel;
            }
        }
    }

    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom) {
        for (int i = 0; i < renderHeight; i++) {
            for (int j = 0; j < renderWidth; j++) {
                for (int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++) {
                    for (int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++) {
                        setPixel(renderPixels[j + i * renderWidth], (j * xZoom) + xZoomPos + xPosition, (i * yZoom) + yZoomPos + yPosition);
                    }
                }
            }
        }
    }
}
