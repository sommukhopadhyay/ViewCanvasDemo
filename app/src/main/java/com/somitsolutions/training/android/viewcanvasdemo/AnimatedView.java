package com.somitsolutions.training.android.viewcanvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * This is a very simple View animation class that depicts a small 
 * ball bouncing around the screen.
 * 
 * The animation loop is created by repeatedly calling invalidate()
 * on the view using a runnable and postDelayed. This is a very simple 
 * animation loop, but because postDelayed may not be completely
 * accurate and invalidate() may not happen instantaneously, the 
 * timing is not precise. This might create animation stutters or lag.
 *
 *
 */
public class AnimatedView extends View {


	private static final int FRAME_RATE = 16; //~60fps

	private int xCircle = 0;
	private int yCircle = 0;
	private int wCircle = 50;
	private int hCircle = 50;
	private int xVelocity = 10;
	private int yVelocity = 10;
	private ShapeDrawable circle;

	Runnable r = new Runnable() {
		@Override
		public void run() {
			// Update state of what we draw
			updateState();

			// Request a redraw of this view
			// onDraw(Canvas) will be called
			invalidate();
		}
	};
	public AnimatedView(Context context) {
		super(context);
		init();
	}

	public AnimatedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AnimatedView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		xCircle = getDipsFromPixel(xCircle);
		yCircle = getDipsFromPixel(yCircle);
		wCircle = getDipsFromPixel(wCircle);
		hCircle = getDipsFromPixel(hCircle);
		xVelocity = getDipsFromPixel(xVelocity);
		yVelocity = getDipsFromPixel(yVelocity);
		circle = new ShapeDrawable(new OvalShape());
		circle.getPaint().setColor(getResources().getColor(android.R.color.darker_gray));
	}



	@Override
	protected void onDraw(Canvas canvas) {
		// Draw on canvas
		circle.setBounds(xCircle, yCircle, xCircle + wCircle, yCircle + hCircle);
		circle.draw(canvas);

		// Invalidate view at about 60fps
		postDelayed(r, FRAME_RATE);
	}

	private void updateState() {
		if(xCircle + wCircle >= getWidth() || xCircle < 0) {
			xVelocity *= -1;
		}

		if(yCircle + hCircle >= getHeight() || yCircle < 0) {
			yVelocity *= -1;
		}

		xCircle += xVelocity;
		yCircle += yVelocity;
	}

	/**
	 * Convert pixels to dips
	 * @param pixels - pixels to be converted to dips
	 * @return the provided pixels converted to dips
	 */
	private int getDipsFromPixel(float pixels) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}
}