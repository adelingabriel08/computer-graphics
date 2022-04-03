package cg.bouncysquare;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class SquareRenderer implements GLSurfaceView.Renderer{
    private Square mSquare1;
    private Square mSquare2;
    private Context context;
    private float mTransY;

    public SquareRenderer(Context context)
    {
        this.context = context;
        float[] squareColorsYMCA =
                {
                        1.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f
                };
        float[] squareColorsRGBA =
                {
                        1.0f, 0.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f, 1.0f
                };
        mSquare1 = new Square(squareColorsYMCA);
        mSquare2 = new Square(squareColorsRGBA);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
//        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0,0,255,255);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        int resid = R.drawable.dog;
        int resId2 = R.drawable.texture2;
        mSquare1.createTexture(gl, this.context, resid);
        mSquare2.createTexture(gl, this.context, resId2);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float) width / height;
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL11.GL_MODELVIEW);
        gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f,(float)Math.sin(mTransY), -3.0f);
//        gl.glColor4f(0.0f,0.0f,1.0f,0.5f);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
//        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//        gl.glColorMask(true, false, true, true);
        mSquare1.draw(gl);

        gl.glLoadIdentity();
        gl.glTranslatef((float)(Math.sin(mTransY)/2.0f),0.0f, -2.9f);
//        gl.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
        mSquare2.draw(gl);
        mTransY += .075f;
    }


}
