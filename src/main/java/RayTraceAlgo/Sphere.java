package RayTraceAlgo;


public class Sphere extends Figure{

    private Point3D center;
    private double radius;

    public Sphere(Point3D center, double radius, Colour colour) {
        this.center = center;
        this.radius = radius;
        this.colour = colour;
    }

    @Override
    public double hit(Ray ray) {
        // (p-c)*(p-c) = r^2; // if you take a point on a sphere and you subtract it from the centre of
        // that sphere and you dot it with the same value you will get radius squared
        // the point in the sphere p will be the point where ray intersects
        // (o +td -c)*(o +td -c)-r^2 = 0
        //we isolate t from this equation, by doing that we get a quadratic equation which we solve

        double a = ray.direction.dotProduct(ray.direction); // the first part of a2 +2ab+b2
        double b = 2*ray.origin.sub(center).dotProduct(ray.direction);
        double c = ray.origin.sub(center).dotProduct(ray.origin.sub(center))-radius*radius;
        double discriminant = b*b -4*a*c;

        if(discriminant < 0.0)
            return 0.0;
        else{
            double t = (-b -Math.sqrt(discriminant))/(2*a); // negative b value is closest value

            if(t > 10E-9)
                return t;
            else
                return 0.0;  // so if t is not zero it means in intersected with the plane

        }
    }
}
