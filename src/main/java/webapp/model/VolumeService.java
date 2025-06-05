package webapp.model;

public class VolumeService {
    public double calculateVolume(String shape, double param1, double param2) {
        if (shape.equals("cube")) {
            return Math.pow(param1, 3);
        } else if (shape.equals("sphere")) {
            return (4.0 / 3.0) * Math.PI * Math.pow(param1, 3);
        } else if (shape.equals("tetrahedron")) {
            return (Math.pow(param1, 3) / (6 * Math.sqrt(2)));
        } else if (shape.equals("torus")) {
            return (Math.PI * Math.pow(param2, 2)) * (2 * Math.PI * param1);
        } else if (shape.equals("ellipsoid")) {
            return (4.0 / 3.0) * Math.PI * param1 * param1 * param2;
        }
        return 0;
    }
}
