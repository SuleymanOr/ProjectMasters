package RayTraceAlgo;


public class Plane extends Figure {

    private Point3D point3D;
    private Normal normal;

    public Plane(Point3D point3D, Normal normal, Colour colour) {
        this.point3D = point3D;
        this.normal = normal;
        this.colour = colour;
    }

    @Override
    public double hit(Ray ray) { // this method takes in a ray and determines where it intersects with the plane
//        (p-a)# n=0 // plane equation
//        (origin + directionVecDist - arbitraryPoint) # n = 0;
//        origin#n + directionVecDist#n - arbitraryPoint#n = 0; // expanded
//        directionVecDist#n + (origin-arbitraryPoint)#n = 0; // further expanded
//        directionVecDist#n = (arbitraryPoint-origin)#n;

        double t = point3D.sub(ray.origin).dotProduct(normal)/ray.direction.dotProduct(normal); // arbitrary point on a plane

        if(t > 10E-9)
            return t;
        else
            return 0.0;  // so if t is not zero it means in intersected with the plane
    }
}
