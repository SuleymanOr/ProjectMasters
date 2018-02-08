package RayTraceAlgo;

public class Normal {

    public double x, y, z;

    public Normal(){
        x = 0.0;
        y = 0.0;
        z = 0.0;
    }

    public Normal(Normal normal){
        x = normal.x;
        y = normal.y;
        z = normal.z;
    }


    public Normal(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dotProduct(Normal normal){
        return x*normal.x + y*normal.y + z*normal.z;
    }

    public void normalize(){
        double magnitude = Math.sqrt(x*x + y*y + z*z);

        x /= magnitude;
        y /= magnitude;
        z /= magnitude;
    }

}
