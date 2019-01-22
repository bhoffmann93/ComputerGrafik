package ab3;

public class Vector3 {

    private float x;
    private float y;
    private float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }


    public Vector3 normalize() {
        float length = magnitude();
        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    public float magnitude() {
        return (float) Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public Vector3 between(Vector3 other){
        float x = other.x-this.x;
        float y = other.y-this.y;
        float z = other.z-this.z;

        return new Vector3(x,y,z);
    }



}

