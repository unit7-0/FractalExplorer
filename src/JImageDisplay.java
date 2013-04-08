import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * ����� ��������� ��� ��������� image.
 */
public class JImageDisplay extends JComponent {
    /**
     * @param width ������.
     * @param height ������.
     */
    public JImageDisplay(final int width, final int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width, height));
    }

    /**
     * ������ image �� ������.
     * @param g �����.
     */
    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    /**
     * ��������� �� ������� �������
     * ����������� ��� �� ������� �
     * �������� �
     * ������ ����.
     */
    public final void clearImage() {
        for (int x = 0; x < image.getHeight(); ++x) {
            for (int y = 0; y < image.getWidth(); ++y) {
                image.setRGB(x, y, Color.BLACK.getRGB());
            }
        }
    }

    /**
     * ������������� ���� ������� �
     * ����������� x, y � ���� rgbColor.
     * @param x x-����������.
     * @param y y-����������.
     * @param rgbColor ����.
     */
    public final void drawPixel(final int x, final int y, final int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }

    public BufferedImage getImage() {
        return image;
    }
    
    /**
     * ����� ��� ���������.
     */
    private BufferedImage image;
}
