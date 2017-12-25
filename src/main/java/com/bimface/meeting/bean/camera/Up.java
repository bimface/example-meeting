package com.bimface.meeting.bean.camera;

/**
 * @author dup, 2017-11-23
 */
public class Up {
    private Integer x;
    private Integer y;
    private Integer z;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Up{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Up up = (Up) o;

        if (x != null ? !x.equals(up.x) : up.x != null) return false;
        if (y != null ? !y.equals(up.y) : up.y != null) return false;
        return z != null ? z.equals(up.z) : up.z == null;
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = 31 * result + (z != null ? z.hashCode() : 0);
        return result;
    }
}
