import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * �������� �����.
 */
public class FractalExplorer {
    /**
     * ������ ������� ��������.
     */
    private static final int DEFAULT_SIZE = 500;
    /**
     * �������.
     */
    private static final double SCALE = 0.5;
    /**
     *
     */
    private static final float CONST1 = 0.7f;
    /**
     *
     */
    private static final float CONST2 = 200f;

    /**
     * main.
     * @param args ���������.
     */
    public static void main(final String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(DEFAULT_SIZE);

        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

    /**
     * @param size �������.
     */
    public FractalExplorer(final int size) {
        this.sideSize = size;
        setFractalGenerator(new Mandelbrot());

        imageDisplay = new JImageDisplay(sideSize, sideSize);
        fractalRectangle = new Rectangle2D.Double();
        fractalGenerator.getInitialRange(fractalRectangle);
    }

    /**
     * ���������� ������ ����� ���������
     * � ���������� ��� ��� ���� ������ ��
     * ���������� �������� ��������
     * ������������.
     */
    public final void drawFractal() {
        for (int x = 0; x < sideSize; ++x) {
            for (int y = 0; y < sideSize; ++y) {
                double xCoord = FractalGenerator.getCoord(fractalRectangle.x,
                        fractalRectangle.x + fractalRectangle.width, sideSize,
                        x);
                double yCoord = FractalGenerator.getCoord(fractalRectangle.y,
                        fractalRectangle.y + fractalRectangle.height, sideSize,
                        y);

                int rgbColor = Color.BLACK.getRGB();
                int iterations = fractalGenerator.numIterations(xCoord, yCoord);

                if (iterations != -1) {
                    float hue = CONST1 + (float) iterations / CONST2;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }

                imageDisplay.drawPixel(x, y, rgbColor);
            }
        }

        imageDisplay.repaint();
    }

    /**
     * ����� �������� �������������
     * ������������ ����������,
     * � ����� �����������
     * ������������ �������.
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame();

        frame.setTitle("FractalExplorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        
        JButton reset = new JButton("ResetDisplay");
        JButton saveImage = new JButton("Save image");

        buttons.add(saveImage);
        buttons.add(reset);
        
        frame.getContentPane().add(imageDisplay, BorderLayout.CENTER);
        frame.getContentPane().add(buttons, BorderLayout.SOUTH);

        reset.addActionListener(new ResetButtonListener());
        saveImage.addActionListener(new SaveImageListener());
        imageDisplay.addMouseListener(new ImageDisplayListener());

        JPanel fractalBox = new JPanel();
        JLabel fractLabel = new JLabel("Fractal: ");
        final JComboBox fractals = new JComboBox();
        
        fractals.addItem(new Mandelbrot());
        fractals.addItem(new Tricorn());
        fractals.addItem(new BurningShip());
        
        fractalBox.add(fractLabel);
        fractalBox.add(fractals);
        
        frame.getContentPane().add(fractalBox, BorderLayout.NORTH);
        
        fractals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource() == fractals) {
                    FractalExplorer.this.setFractalGenerator((FractalGenerator) fractals.getSelectedItem());
                }
            }
        });
        
        frame.pack();
        frame.setVisible(true);
        // frame.setResizable(false);
    }

    /**
     * ���������� ��� ������ reset
     * ���������� ��� � ���������
     * ��������� �
     * �������������� �������.
     */
    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent arg0) {
            fractalGenerator.getInitialRange(fractalRectangle);
            drawFractal();
        }

    }

    /**
     * ���������� ��� �������� ����.
     * ������������� ������� ��������
     * ��� �����.
     */
    private class ImageDisplayListener extends MouseAdapter {
        @Override
        public void mouseClicked(final MouseEvent e) {
            super.mouseClicked(e);

            int x = e.getX();
            int y = e.getY();

            double xCoord = FractalGenerator.getCoord(fractalRectangle.x,
                    fractalRectangle.x + fractalRectangle.width, sideSize, x);
            double yCoord = FractalGenerator.getCoord(fractalRectangle.y,
                    fractalRectangle.y + fractalRectangle.height, sideSize, y);

            fractalGenerator.recenterAndZoomRange(fractalRectangle, xCoord,
                    yCoord, SCALE);
            drawFractal();
        }

    }
    
    private class SaveImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fChooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            fChooser.setFileFilter(filter);
            fChooser.setAcceptAllFileFilterUsed(false);
            
            int status = fChooser.showSaveDialog(imageDisplay);
            if (status == JFileChooser.APPROVE_OPTION) {
                File file = fChooser.getSelectedFile();
                try {
                    ImageIO.write(imageDisplay.getImage(), "png", file);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(imageDisplay, e1.getMessage(), "Нельзя сохранить изображение!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * ������������� �������� ���� fractalGenerator.
     * @param generator ���������.
     */
    public final void setFractalGenerator(
            final FractalGenerator generator) {
        this.fractalGenerator = generator;
    }

    /**
     * ������ ������� ���������.
     */
    private int sideSize;
    /**
     * ������ ��� �����������.
     */
    private JImageDisplay imageDisplay;
    /**
     * ��������� ���������.
     */
    private FractalGenerator fractalGenerator;
    /**
     * ������������ �������.
     */
    private Rectangle2D.Double fractalRectangle;
}
