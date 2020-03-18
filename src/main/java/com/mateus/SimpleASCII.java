package com.mateus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimpleASCII {
    private static final char[] ASCII_CHARACTERS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$".toCharArray();
    private static final int MAX_HEIGHT = 150;
    private static final int MAX_WIDTH = 200;
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("Invalid arguments, the first argument must be the file name");
        }
        String fileName = args[0];
        File file = new File(System.getProperty("user.dir"), fileName);
        if (!file.exists()) {
            throw new RuntimeException("This file doesn't exist");
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            toASCII(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void toASCII(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage;
        if (height > MAX_HEIGHT) {
            height = MAX_HEIGHT;
        }
        if (width > MAX_WIDTH) {
            width = MAX_WIDTH;
        }
        newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        System.out.println("Image loaded successfully\nImage dimensions:\n" + width + " x " + height);
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] rgb = getSeparatedRGB(newImage.getRGB(x, y));
                int averageBrightness = (rgb[0] + rgb[1] + rgb[2]) /3;
                int chosenCharacterIndex = averageBrightness == 0 ? 0 : (averageBrightness * 65) /255;
                if (chosenCharacterIndex > 0) {
                    chosenCharacterIndex--;
                }
                char chosenCharacter = ASCII_CHARACTERS[chosenCharacterIndex];
                stringBuilder.append(chosenCharacter).append(chosenCharacter).append(chosenCharacter);
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }
    private static int[] getSeparatedRGB(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0xff00) >> 8;
        int blue = color & 0xff;
        return new int[]{red, green, blue};
    }
}
