import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Runnable;
import java.lang.Thread;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class Game extends JFrame implements Runnable {
    private Canvas canvas = new Canvas();
    private RenderHandler renderer;
    private BufferedImage testImage;

    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes program exit when closed
        setBounds(0,0, 1000, 800); // Set the position and size of frame.
        setLocationRelativeTo(null);  // Center by default
        add(canvas); // Adds graphics component
        setVisible(true);
        canvas.createBufferStrategy(3); // Creates object for buffer strategy
        renderer = new RenderHandler(getWidth(), getHeight()); // init RenderHandler object
        testImage = loadImage("grasstile.png"); // test image to see if an image will load
    }

    private BufferedImage loadImage(String path) {
        try {
            InputStream stream = Files.newInputStream(Paths.get(path));
            BufferedImage loadedImage = ImageIO.read(stream);
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB); // python has something similar to this
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage; // will change image to work with no opacity and correct format
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        }
    }


    public void update() {
        // ???
    }


    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        renderer.renderImage(testImage, 0, 0, 1, 1);
        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public void run() {
        long lastTime = System.nanoTime(); // long 2^63
        double nanoSecondConversion = 1000000000.0 / 60; // Sets framerate to 60
        double changeInSeconds = 0;

        while(true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while(changeInSeconds >= 1) {
                update(); // Update method ensures consistency regardless of processing power since its rooted in system time
                changeInSeconds--;
            }

            render(); // Render method will vary with quality of computer, faster computers will slow and freeze less often
            lastTime = now;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

}