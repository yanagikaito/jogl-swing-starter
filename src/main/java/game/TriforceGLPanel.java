package game;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.geom.Point2D;

public class TriforceGLPanel extends GLJPanel implements GLEventListener {

    private final SmallTri[] tris = new SmallTri[3];
    private boolean forming = false;
    private final double formDuration = 1.0;
    private double formTime = 0.0;
    private final int fps = 60;
    private final FPSAnimator animator;

    public TriforceGLPanel() {
        super(new GLCapabilities(GLProfile.getDefault()));
        setBackground(Color.BLACK);

        double finalSize = 1.5 * 0.28;

        tris[0] = new SmallTri(-0.9, -1.6, finalSize, 0.35, 80);
        tris[1] = new SmallTri(0.0, -1.7, finalSize, 0.40, -90);
        tris[2] = new SmallTri(0.9, -1.6, finalSize, 0.35, 100);

        addGLEventListener(this);
        animator = new FPSAnimator(this, fps, true);
    }

    public void start() {
        if (!animator.isAnimating()) animator.start();
    }

    public void stop() {
        if (animator.isAnimating()) animator.stop();
    }

    private Point2D.Double[] finalPositions() {
        return new Point2D.Double[]{
                new Point2D.Double(0.0, 0.39),
                new Point2D.Double(-0.42, -0.42),
                new Point2D.Double(0.42, -0.42)
        };
    }

    private void update(double dt) {
        if (!forming) {
            for (SmallTri t : tris) {
                t.y += t.vy * dt;
                t.angle += t.angularVel * dt;
            }
            boolean allAbove = true;
            for (SmallTri t : tris) {
                if (t.y < -0.05) {
                    allAbove = false;
                    break;
                }
            }
            if (allAbove) {
                forming = true;
                formTime = 0.0;
                Point2D.Double[] targets = finalPositions();
                for (int i = 0; i < tris.length; i++) {
                    SmallTri t = tris[i];
                    t.setTarget(targets[i].x, targets[i].y, t.size);
                }
            }
        } else {
            formTime += dt;
            double t = Math.min(1.0, formTime / formDuration);
            double ease = t * t * (3 - 2 * t);
            for (SmallTri st : tris) {
                st.x = lerp(st.startX, st.targetX, ease);
                st.y = lerp(st.startY, st.targetY, ease);
                st.size = lerp(st.startSize, st.targetSize, ease);
                st.angle = lerp(st.startAngle, 0.0, ease);
            }
        }
    }

    private double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // nothing
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        double dt = 1.0 / fps;
        update(dt);

        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        int w = getWidth();
        int h = getHeight();
        float aspect = (float) w / Math.max(1, h);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-aspect, aspect, -1.0, 1.0, -1.0, 1.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        for (SmallTri t : tris) {
            gl.glPushMatrix();
            gl.glTranslated(t.x, t.y, 0.0);
            gl.glRotated(t.angle, 0.0, 0.0, 1.0);
            double s = t.size;

            gl.glColor4d(1.0, 0.95, 0.2, 1.0);
            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex2d(0.0, s);
            gl.glVertex2d(-s * 0.8, -s * 0.9);
            gl.glVertex2d(s * 0.8, -s * 0.9);
            gl.glEnd();

            gl.glColor4d(0.5, 0.35, 0.0, 1.0);
            gl.glLineWidth(2.0f);
            gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2d(0.0, s);
            gl.glVertex2d(-s * 0.8, -s * 0.9);
            gl.glVertex2d(s * 0.8, -s * 0.9);
            gl.glEnd();

            gl.glPopMatrix();
        }

        if (forming && formTime >= formDuration) {
            gl.glColor4d(1.0, 0.9, 0.2, 0.25);
            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex2d(0.0, 0.8);
            gl.glVertex2d(-0.4, 0.0);
            gl.glVertex2d(0.4, 0.0);
            gl.glEnd();

            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex2d(-0.4, 0.0);
            gl.glVertex2d(-0.8, -0.8);
            gl.glVertex2d(0.0, -0.8);
            gl.glEnd();

            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex2d(0.4, 0.0);
            gl.glVertex2d(0.0, -0.8);
            gl.glVertex2d(0.8, -0.8);
            gl.glEnd();
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, Math.max(1, height));
    }
}