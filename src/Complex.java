import static java.lang.Math.sqrt;

/**
 * ���������� ������������ �����.
 */
public class Complex {
    /**
     *
     */
    public Complex() {
        this(0, 0);
    }

    /**
     * @param realPart ��������������.
     * @param imagePart ������.
     */
    public Complex(final double realPart, final double imagePart) {
        this.real = realPart;
        this.image = imagePart;
    }

    /**
     * @param other �����.
     * @return ��������� �� ������ �����������
     */
    public final Complex multiply(final Complex other) {
        return new Complex(real * other.real - image * other.image, real
                * other.image + other.real * image);
    }

    /**
     * @param multiplier ���������.
     * @return ��������� �� �����.
     */
    public final Complex multiply(final double multiplier) {
        return new Complex(real * multiplier, image * multiplier);
    }

    /**
     * @return ������� �����������.
     */
    public final Complex sqr() {
        return this.multiply(this);
    }

    /**
     * @param other �����.
     * @return �������� � ������ �����������.
     */
    public final Complex add(final Complex other) {
        return new Complex(real + other.real, image + other.image);
    }
    
    /**
     * @param other �����.
     * @return �������� � ������ �����������.
     */
    public static Complex absComplex(final Complex other) {
        return new Complex(other.real < 0 ? --other.real : other.real,
                other.image < 0 ? -other.image : other.image);
    }

    /**
     * @param complex �����.
     * @return ������ ������������.
     */
    public static double abs(final Complex complex) {
        return sqrt(absSqr(complex));
    }

    /**
     * @param complex �����.
     * @return ������ ������������ � ��������.
     */
    public static double absSqr(final Complex complex) {
        return complex.real * complex.real + complex.image * complex.image;
    }

    /**
     *
     */
    private double real, image;
}