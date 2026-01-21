package game;

public class SmallTri {
    double x, y;
    double size;
    double vy;
    double angle;
    double angularVel;

    double targetX, targetY, targetSize;
    double startX, startY, startSize, startAngle;

    public SmallTri(double x, double y, double size, double vy, double angularVel) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.vy = vy;
        this.angularVel = angularVel;
        this.angle = Math.random() * 360.0;
        this.targetX = x;
        this.targetY = y;
        this.targetSize = size;
        this.startX = x;
        this.startY = y;
        this.startSize = size;
        this.startAngle = angle;
    }

    void setTarget(double tx, double ty, double tsize) {
        this.targetX = tx;
        this.targetY = ty;
        this.targetSize = tsize;
        this.startX = this.x;
        this.startY = this.y;
        this.startSize = this.size;
        this.startAngle = this.angle;
    }
}