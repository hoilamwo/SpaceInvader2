package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private final List<BufferedImage> sprites;

    public SpriteSheet(List<BufferedImage> sprites) {
        this.sprites = new ArrayList<>(sprites);
    }

    public static BufferedImage loadSprite(String file) {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spriteSheet;
    }

    public static SpriteSheet CreateSpriteArray(String file, int rows, int cols, int spriteWidth, int spriteHeight, int numOfSprite){

        BufferedImage img = loadSprite(file);

        if (spriteWidth == 0) {
            spriteWidth = img.getWidth() / cols;
        }
        if (spriteHeight == 0) {
            spriteHeight = img.getHeight() / rows;
        }

        int x = 0;
        int y = 0;
        List<BufferedImage> sprites = new ArrayList<>(numOfSprite);

        for (int index = 0; index < numOfSprite; index++) {
            sprites.add(img.getSubimage(x, y, spriteWidth, spriteHeight));
            x += spriteWidth;
            if (x >= spriteWidth * cols) {
                x = 0;
                y += spriteHeight;
            }
        }
        return new SpriteSheet(sprites);
    }

    public BufferedImage getSprite(int minionFrame) {
        return sprites.get(minionFrame);
    }

}