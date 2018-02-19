package RayTraceAlgo;

public class Colour {

    public float r, g, b;

    public Colour()
    {
        r = 0.0F;
        g = 0.0F;
        b = 0.0F;
    }

    public Colour(float r, float g, float b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Colour(Colour colour)
    {
        r = colour.r;
        g = colour.g;
        b = colour.b;

    }

    public void add(Colour colour)
    {
        r += colour.r;
        g += colour.g;
        b += colour.b;
    }

    public void divide(int scalar)
    {
        r /= scalar;
        g /= scalar;
        b /= scalar;
    }

    public int toInteger()
    {
        return (int) (r*255)<<16| (int) (g*255)<<8| (int) (b*255);
    }
}

