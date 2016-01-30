package com.stylingandroid.indeterminate.drawable;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

final class IndeterminateAnimatorFactory {
    private static final String START_ANGLE = "startAngle";
    private static final String END_ANGLE = "endAngle";
    private static final String ROTATION = "rotation";

    private static final int SWEEP_DURATION = 1333;
    private static final int ROTATION_DURATION = 6665;
    private static final float END_ANGLE_MAX = 360;
    private static final float START_ANGLE_MAX = END_ANGLE_MAX - 1;
    private static final int ROTATION_END_ANGLE = 719;

    private IndeterminateAnimatorFactory() {
        // NO-OP
    }

    public static Animator createIndeterminateDrawableAnimator(IndeterminateDrawable drawable) {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator startAngleAnimator = createStartAngleAnimator(drawable);
        Animator sweepAngleAnimator = createSweepAngleAnimator(drawable);
        Animator rotationAnimator = createAnimationAnimator(drawable);
        animatorSet.playTogether(startAngleAnimator, sweepAngleAnimator, rotationAnimator);
        return animatorSet;
    }

    private static Animator createStartAngleAnimator(IndeterminateDrawable drawable) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(drawable, START_ANGLE, 0f, START_ANGLE_MAX);
        animator.setDuration(SWEEP_DURATION);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(createStartInterpolator());
        return animator;
    }

    //CHECKSTYLE IGNORE MagicNumber
    private static Interpolator createStartInterpolator() {
        Path path = new Path();
        path.cubicTo(0.3f, 0f, 0.1f, 0.75f, 0.5f, 0.85f);
        path.lineTo(1f, 1f);
        return PathInterpolatorCompat.create(path);
    }
    //CHECKSTYLE END IGNORE MagicNumber

    private static Animator createSweepAngleAnimator(IndeterminateDrawable drawable) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(drawable, END_ANGLE, 0f, END_ANGLE_MAX);
        animator.setDuration(SWEEP_DURATION);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(createEndInterpolator());
        return animator;
    }

    //CHECKSTYLE IGNORE MagicNumber
    private static Interpolator createEndInterpolator() {
        Path path = new Path();
        path.lineTo(0.5f, 0.1f);
        path.cubicTo(0.7f, 0.15f, 0.6f, 0.75f, 1f, 1f);
        return PathInterpolatorCompat.create(path);
    }
    //CHECKSTYLE END IGNORE MagicNumber

    private static Animator createAnimationAnimator(IndeterminateDrawable drawable) {
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(drawable, ROTATION, 0, ROTATION_END_ANGLE);
        rotateAnimator.setDuration(ROTATION_DURATION);
        rotateAnimator.setRepeatMode(ValueAnimator.RESTART);
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        return rotateAnimator;
    }
}
