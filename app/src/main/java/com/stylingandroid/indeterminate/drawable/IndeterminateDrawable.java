package com.stylingandroid.indeterminate.drawable;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.stylingandroid.indeterminate.R;

public class IndeterminateDrawable extends Drawable implements Animatable {
    private static final int STROKE_WIDTH = 6;
    private static final int PADDING = 3;

    private final Paint paint;
    private final RectF bounds;
    private final float padding;
    private float startAngle;
    private float endAngle;
    private float rotation;
    private Animator animator;

    public static IndeterminateDrawable newInstance(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float strokeWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, STROKE_WIDTH, displayMetrics);
        float paddingPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING, displayMetrics);
        Paint paint = createPaint(fetchControlColour(context), strokeWidthPx);
        RectF bounds = new RectF();
        float padding = strokeWidthPx / 2 + paddingPx;
        IndeterminateDrawable drawable = new IndeterminateDrawable(paint, bounds, padding);
        drawable.createAnimator();
        return drawable;
    }

    @ColorInt
    private static int fetchControlColour(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(0, new int[]{R.attr.colorControlActivated});
        try {
            return typedArray.getColor(0, 0);
        } finally {
            typedArray.recycle();
        }
    }

    static Paint createPaint(@ColorInt int colour, float strokeWidth) {
        Paint paint = new Paint();
        paint.setColor(colour);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        return paint;
    }

    IndeterminateDrawable(Paint paint, RectF bounds, float padding) {
        this.paint = paint;
        this.bounds = bounds;
        this.padding = padding;
    }

    private void createAnimator() {
        animator = IndeterminateAnimatorFactory.createIndeterminateDrawableAnimator(this);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }

    @Override
    public void start() {
        animator.start();
    }

    @Override
    public void stop() {
        animator.end();
    }

    @Override
    public boolean isRunning() {
        return animator.isRunning();
    }

    @SuppressWarnings("unused")
    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        invalidateSelf();
    }

    @SuppressWarnings("unused")
    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
        invalidateSelf();
    }

    @SuppressWarnings("unused")
    public void setRotation(float rotation) {
        this.rotation = rotation;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        bounds.set(padding, padding, canvas.getWidth() - padding, canvas.getHeight() - padding);
        canvas.drawArc(bounds, rotation + startAngle, endAngle - startAngle, false, paint);
    }
}
