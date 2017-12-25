package com.bimface.meeting.bean.camera;

/**
 * @author dup, 2017-11-23
 */
public class CameraStatus {
    private Position position;
    private Target target;
    private Up up;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Up getUp() {
        return up;
    }

    public void setUp(Up up) {
        this.up = up;
    }

    @Override
    public String toString() {
        return "CameraStatus{" +
                "position=" + position +
                ", target=" + target +
                ", up=" + up +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CameraStatus that = (CameraStatus) o;

        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        return up != null ? up.equals(that.up) : that.up == null;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (up != null ? up.hashCode() : 0);
        return result;
    }
}
