package corp.burenz.expertouch.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

import corp.burenz.expertouch.R;

/**
 * Created by Developer on 3/11/2016.
 */

public class LoadProgress extends View {


    private Movie gifMovie;
    private int movieWidth,movieHeight;
    private long movieDuration;
    private long movieStart;



    public LoadProgress(Context context) {
        super(context);
        init(context);
    }

    public LoadProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

            InputStream gifInputStream = context.getResources().openRawResource(R.raw.progress);
            gifMovie = Movie.decodeStream(gifInputStream);

            movieWidth = gifMovie.width();
            movieHeight = gifMovie.height();
            movieDuration = gifMovie.duration();

          }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(movieWidth,movieHeight);
//
//    }

    public int getMovieWidth() {
        return movieWidth;
    }

    public int getMovieHeight() {
        return movieHeight;
    }

    public long getMovieDuration() {
        return movieDuration;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.scale((float)this.getWidth()/ (float)gifMovie.width(),(float)this.getHeight()/(float)gifMovie.height());
        //canvas.scale(1.91f,1.21f);

        long now = SystemClock.uptimeMillis();

        if (movieStart == 0){
            movieStart = now;
        }

        if (gifMovie != null){
            int dur = gifMovie.duration();
             if(dur == 0){
                dur = 1000;
            }

            int relTime = (int)((now - movieStart) % dur);
            gifMovie.setTime(relTime);
            gifMovie.draw(canvas, 0, 0);
                //canvas.scale((float)this.getWidth()/ (float)gifMovie.width(),(float)this.getHeight()/(float)gifMovie.height());
                //canvas.scale(1.21f,1.91f);
                invalidate();
        }



    }
}
