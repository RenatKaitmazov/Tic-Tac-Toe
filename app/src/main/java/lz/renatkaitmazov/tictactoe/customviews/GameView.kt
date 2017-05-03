package lz.renatkaitmazov.tictactoe.customviews

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import lz.renatkaitmazov.tictactoe.R
import lz.renatkaitmazov.tictactoe.di.app.AppModule

/**
 *
 * @author Renat Kaitmazov
 */

class GameView : View {

    /** Instance variables **/

    val fadeAnimationDuration: Long = 300L
    val scaleAnimationDuration: Long = 300L

    val verticalSeparatorPath = Path()
    val horizontalSeparatorPath = Path()
    val xMarkerThumbnailPath = Path()
    val oMarkerThumbnailPath = Path()

    val decelerateInterpolator = DecelerateInterpolator()

    var separatorColor: Int = Color.rgb(0, 188, 212)
    var xMarkerColor: Int = Color.rgb(0, 150, 136)
    var xMarkerThumbnailColor: Int = Color.rgb(0, 150, 136)
    var oMarkerColor: Int = Color.rgb(3, 169, 244)
    var oMarkerThumbnailColor: Int = Color.argb(63, 3, 169, 244)

    val separatorPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = context.resources.getDimension(R.dimen.separatorThickness)
        paint.color = separatorColor
        paint
    }

    val xMarkerPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = context.resources.getDimension(R.dimen.xMarkerThickness)
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = xMarkerColor
        paint
    }

    val oMarkerPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = context.resources.getDimension(R.dimen.oMarkerThickness)
        paint.color = oMarkerColor
        paint
    }

    val xMarkerThumbnailPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = context.resources.getDimension(R.dimen.xMarkerThumbnailThickness)
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = xMarkerThumbnailColor
        paint
    }

    val oMarkerThumbnailPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = context.resources.getDimension(R.dimen.oMarkerThumbnailThickness)
        paint.color = oMarkerThumbnailColor
        paint
    }

    val density: Float by lazy(LazyThreadSafetyMode.NONE) {
        context.resources.displayMetrics.density
    }

    val gridLength: Float by lazy(LazyThreadSafetyMode.NONE) {
        val desiredWidth: Float = context.resources.getDimension(R.dimen.gridLength)
        if (width > desiredWidth) desiredWidth else width * 0.75F
    }

    val cellLength: Float by lazy(LazyThreadSafetyMode.NONE) {
        gridLength / AppModule.CELL_PER_ROW
    }

    // Makes invalidation of the view more efficient by passing this rectangle to the invalidate() method
    val xMarkerThumbnailRect: Rect by lazy(LazyThreadSafetyMode.NONE) {
        val rectF = RectF()
        xMarkerThumbnailPath.computeBounds(rectF, false)
        Rect(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
    }

    val oMarkerThumbnailRect: Rect by lazy(LazyThreadSafetyMode.NONE) {
        val rectF = RectF()
        oMarkerThumbnailPath.computeBounds(rectF, false)
        Rect(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
    }

    /** Constructors **/

    constructor(ctx: Context) : super(ctx) {
        init(ctx, null)
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        init(ctx, attrs)
    }

    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle) {
        init(ctx, attrs)
    }

    /** Measure, Layout, Draw **/

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        val widthF: Float = width.toFloat()
        val heightF: Float = height.toFloat()
        setupGrid(widthF, heightF)
        // Thumbnails for x, and o marker paths
        setupXMarkerThumbnailPath(widthF, heightF)
        setupOMarkerThumbnailPath(widthF, heightF)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth: Int = context.resources.displayMetrics.widthPixels
        val desiredHeight: Int = context.resources.displayMetrics.heightPixels
        val measuredWidth: Int = calculateSize(desiredWidth, widthMeasureSpec)
        val measuredHeight: Int = calculateSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            canvas.drawPath(verticalSeparatorPath, separatorPaint)
            canvas.drawPath(horizontalSeparatorPath, separatorPaint)
            canvas.drawPath(xMarkerThumbnailPath, xMarkerThumbnailPaint)
            canvas.drawPath(oMarkerThumbnailPath, oMarkerThumbnailPaint)
        }
    }

    var animId = 1
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                if (animId == 1) {
                    fadeInOMarkerThumbnail()
                } else {
                    fadeInXMarkerAndFadeOutOMarker()
                }
                animId = 1 - animId

                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

    /** API **/

    fun fadeInXMarkerAndFadeOutOMarker() {
        val xMarkerFadeIn = markerAlphaAnimation(xMarkerThumbnailPaint, xMarkerThumbnailRect, 63, 255)
        val xMarkerScaleUp = xMarkerScaleAnimation(2F)
        val oMarkerFadeOut = markerAlphaAnimation(oMarkerThumbnailPaint, oMarkerThumbnailRect, 255, 63)
        val oMarkerScaleDown = oMarkerScaleAnimation(0.5F)
        val animatorSet = AnimatorSet()
        animatorSet.play(xMarkerFadeIn)
                .with(oMarkerFadeOut)
                .with(oMarkerScaleDown)
                .with(xMarkerScaleUp)
        animatorSet.start()
    }

    fun fadeInOMarkerThumbnail() {
        val xMarkerFadeOut = markerAlphaAnimation(xMarkerThumbnailPaint, xMarkerThumbnailRect, 255, 63)
        val xMarkerScaleDown = xMarkerScaleAnimation(0.5F)
        val oMarkerFadeIn = markerAlphaAnimation(oMarkerThumbnailPaint, oMarkerThumbnailRect, 63, 255)
        val oMarkerScaleUp = oMarkerScaleAnimation(2F)
        val animatorSet = AnimatorSet()
        animatorSet.play(xMarkerFadeOut)
                .with(oMarkerFadeIn)
                .with(oMarkerScaleUp)
                .with(xMarkerScaleDown)
        animatorSet.start()
    }

    fun markerAlphaAnimation(markerPaint: Paint,
                             dirtyRect: Rect,
                             startValue: Int,
                             endValue: Int,
                             duration: Long = fadeAnimationDuration): ValueAnimator {
        val animator = ValueAnimator.ofInt(startValue, endValue)
        animator.duration = duration
        animator.interpolator = decelerateInterpolator
        animator.addUpdateListener {
            val alpha = it.animatedValue as Int
            markerPaint.alpha = alpha
            invalidate(dirtyRect)
        }
        return animator
    }

    fun oMarkerScaleAnimation(scaleFactor: Float, duration: Long = scaleAnimationDuration): ValueAnimator {
        val bounds = RectF()
        oMarkerThumbnailPath.computeBounds(bounds, false)
        val startRadius: Float = Math.abs(bounds.left - bounds.right) / 2
        val endRadius: Float = startRadius * scaleFactor
        val centerX: Float = width.toFloat() / 2
        val centerY: Float = (height.toFloat() / 2) - (gridLength / 2) - resources.getDimension(R.dimen.markerThumbnailToGridMargin)

        val animator = ValueAnimator.ofFloat(startRadius, endRadius)
        animator.duration = duration
        animator.interpolator = decelerateInterpolator
        animator.addUpdateListener {
            val radius = it.animatedValue as Float
            oMarkerThumbnailPath.reset()
            oMarkerThumbnailPath.addCircle(centerX, centerY, radius, Path.Direction.CCW)
            invalidate(oMarkerThumbnailRect)
        }
        return animator
    }

    fun xMarkerScaleAnimation(scaleFactor: Float, duration: Long = scaleAnimationDuration): ValueAnimator {
        val bounds = RectF()
        xMarkerThumbnailPath.computeBounds(bounds, false)

        val markerSideLength: Float = bounds.right - bounds.left
        val startLength: Float = markerSideLength / 2
        val endLength: Float = startLength * scaleFactor
        val centerX: Float = width.toFloat() / 2
        val centerY: Float = (height.toFloat() / 2) + (gridLength / 2) + resources.getDimension(R.dimen.markerThumbnailToGridMargin)

        val animator = ValueAnimator.ofFloat(startLength, endLength)
        animator.duration = duration
        animator.interpolator = decelerateInterpolator
        animator.addUpdateListener {
            val legLength = it.animatedValue as Float
            xMarkerThumbnailPath.reset()
            xMarkerThumbnailPath.moveTo(centerX + legLength, centerY - legLength)
            xMarkerThumbnailPath.lineTo(centerX - legLength, centerY + legLength)
            xMarkerThumbnailPath.moveTo(centerX - legLength, centerY - legLength)
            xMarkerThumbnailPath.lineTo(centerX + legLength, centerY + legLength)
            invalidate(xMarkerThumbnailRect)
        }
        return animator
    }

    /** Helper methods **/

    private fun init(ctx: Context, attrs: AttributeSet?) {
        // Parse the data from the xml file
        if (attrs != null) {
            val typedArray = ctx.theme.obtainStyledAttributes(attrs, R.styleable.GameView, 0, 0)
            try {
                separatorColor = typedArray.getColor(R.styleable.GameView_separatorColor,R.color.separatorColor)
                xMarkerColor = typedArray.getColor(R.styleable.GameView_xMarkerColor, R.color.xMarkerColor)
                oMarkerColor = typedArray.getColor(R.styleable.GameView_oMarkerColor,R.color.oMarkerColor)
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun calculateSize(desiredSize: Int, sizeMeasureSpec: Int): Int {
        val actualSize: Int = MeasureSpec.getSize(sizeMeasureSpec)
        val measuredSize: Int
        when(MeasureSpec.getMode(sizeMeasureSpec)) {
            MeasureSpec.EXACTLY -> measuredSize = actualSize
            MeasureSpec.AT_MOST -> measuredSize = minOf(desiredSize, actualSize)
            else -> measuredSize = desiredSize
        }
        return measuredSize
    }

    private fun dipToPixel(dips: Float): Float = dips * density

    private fun setupGrid(viewWidth: Float, viewHeight: Float) {
        val cellPerRow: Int = AppModule.CELL_PER_ROW.toInt()

        val halfWidth: Float = viewWidth / 2
        val halfHeight: Float = viewHeight / 2
        val halfGridLength: Float = gridLength / 2
        val halfCellLength: Float = cellLength / 2

        val horizontalX: Float = halfWidth - halfGridLength
        var horizontalY: Float = halfHeight - halfCellLength
        var verticalX: Float = halfWidth - halfCellLength
        val verticalY: Float = halfHeight - halfGridLength

        for (i in 0..cellPerRow - 2) {
            horizontalSeparatorPath.moveTo(horizontalX, horizontalY)
            horizontalSeparatorPath.lineTo(horizontalX + gridLength, horizontalY)
            horizontalSeparatorPath.close()
            verticalSeparatorPath.moveTo(verticalX, verticalY)
            verticalSeparatorPath.lineTo(verticalX, verticalY + gridLength)

            horizontalY += cellLength
            verticalX += cellLength
        }
    }

    private fun setupXMarkerThumbnailPath(viewWidth: Float, viewHeight: Float) {
        val thumbnailLength: Float = cellLength / 3
        val thumbnailHalfLength: Float = thumbnailLength / 2
        val halfViewWidth: Float = viewWidth / 2
        val halfViewHeight: Float = viewHeight / 2
        val gridBottomY: Float = halfViewHeight + (gridLength / 2)
        val margin: Float = context.resources.getDimension(R.dimen.markerThumbnailToGridMargin)

        val leftX: Float = halfViewWidth - thumbnailHalfLength
        val rightX: Float = leftX + thumbnailLength
        val upperY: Float = gridBottomY + margin - thumbnailHalfLength
        val lowerY: Float = upperY + thumbnailLength
        xMarkerThumbnailPath.moveTo(leftX, upperY)
        xMarkerThumbnailPath.lineTo(rightX, lowerY)
        xMarkerThumbnailPath.moveTo(rightX, upperY)
        xMarkerThumbnailPath.lineTo(leftX, lowerY)
    }

    private fun setupOMarkerThumbnailPath(viewWidth: Float, viewHeight: Float) {
        // The initial size of o marker should be twice as small as the size of the x marker
        // since the x marker starts the game and the focus should be on that marker
        val thumbnailLength: Float = cellLength / 6
        val radius: Float = thumbnailLength / 2
        val halfViewWidth: Float = viewWidth / 2
        val halfViewHeight: Float = viewHeight / 2
        val gridTopY: Float = halfViewHeight - (gridLength / 2)
        val margin: Float = context.resources.getDimension(R.dimen.markerThumbnailToGridMargin)
        oMarkerThumbnailPath.addCircle(halfViewWidth, gridTopY - margin, radius, Path.Direction.CCW)
    }
}