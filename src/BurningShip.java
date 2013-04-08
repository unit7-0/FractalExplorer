import java.awt.geom.Rectangle2D;

/**
 * ��������� ��������� ������������.
 */
public class BurningShip extends FractalGenerator {
    /**
     * ������������� ���������
     * �������� ������������ �������.
     * @param range �������.
     */
    @Override
    public final void getInitialRange(final Rectangle2D.Double range) {
        range.x = X_RANGE;
        range.y = Y_RANGE;
        range.width = WIDTH;
        range.height = HEIGHT;
    }

    /**
     * ���������� �������,
     * ������������ �����������
     * �� ����� �������. ���������
     * ���� MAX_ITERATIONS,
     * ���� �����������,
     * ����� ����� ������� �� �������.
     * @param x x-����������.
     * @param y y-����������.
     * @return ���������� ��������.
     */
    @Override
    public final int numIterations(final double x, final double y) {
        Complex z = new Complex(0, 0);
        Complex c = new Complex(x, y);

        int i = 0;
        for (; i < MAX_ITERATIONS; ++i) {
            Complex z1 = Complex.absComplex(z).sqr().add(c);
            if (Complex.absSqr(z1) > CONST_VAL) {
                break;
            }

            z = z1;
        }

        if (i == MAX_ITERATIONS) {
            return -1;
        } else {
            return i;
        }
    }
    
    @Override
    public String toString() {
        return "BurningShip";
    }

    /**
     * ������������ ���-�� ��������.
     */
    public static final int MAX_ITERATIONS = 2000;

    /**
     * �������� ��� ������� ������������.
     */
    private static final int CONST_VAL = 4;
    /**
     * X.
     */
    private static final double X_RANGE = -2;
    /**
     * Y.
     */
    private static final double Y_RANGE = -2.5;
    /**
     * ������.
     */
    private static final double WIDTH = 4;
    /**
     * ������.
     */
    private static final double HEIGHT = 4;
}
