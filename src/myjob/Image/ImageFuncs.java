/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.Image;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.File;


/**
 *
 * @author guillermot
 */
public class ImageFuncs {

    public static BufferedImage merge(String fileName, String fileName2, int x, int y, float alpha) throws IOException {
        BufferedImage im = ImageIO.read(new File(fileName));
        BufferedImage im2 = ImageIO.read(new File(fileName2));

        return merge(im, im2, x, y, alpha);
    }

    public static BufferedImage merge(File file, File file2, int x, int y, float alpha) throws IOException {
        BufferedImage im = ImageIO.read(file);
        BufferedImage im2 = ImageIO.read(file2);

        return merge(im, im2, x, y, alpha);
    }

    public static BufferedImage merge(URL url, URL url2, int x, int y, float alpha) throws IOException {
        BufferedImage im = ImageIO.read(url);
        BufferedImage im2 = ImageIO.read(url2);

        return merge(im, im2, x, y, alpha);
    }


    public static BufferedImage merge(BufferedImage im, BufferedImage im2, int x, int y, float alpha) throws IOException {
        Graphics2D g = im.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(im2, x, y, null);
        g.dispose();

        //return ImageIO.write(im, "jpeg", new File("sample_output.jpeg"));
        return im;
    }

    public static boolean mergeToFile(BufferedImage im, BufferedImage im2, int x, int y, float alpha, File file) throws IOException {
        BufferedImage dest = merge(im, im2, x, y, alpha);
        return ImageIO.write(dest, "jpeg", file);
    }

    public static boolean mergeToFile(URL url, URL url2, int x, int y, float alpha, File file) throws IOException {

        BufferedImage im = merge(url, url2, x, y, alpha);

        return ImageIO.write(im, "jpeg", file);
    }


    public static boolean mergeToFile(URL url, URL url2, int x, int y, float alpha, URL destFile) throws IOException {

        BufferedImage im = merge(url, url2, x, y, alpha);

        return ImageIO.write(im, "jpeg", new File(destFile.getFile()));
    }

}
