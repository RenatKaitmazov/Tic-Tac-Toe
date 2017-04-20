package lz.renatkaitmazov.tictactoe.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.annotation.ColorRes
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.View
import lz.renatkaitmazov.tictactoe.R
import lz.renatkaitmazov.tictactoe.di.app.AppModule

/**
 *
 * @author Renat Kaitmazov
 */

class GameView : View {

    /** Instance variables **/

    var backColor: Int = Color.argb(255, 54, 54, 54)
        get() = field
        set(@ColorRes value) {
            field = value
            setBackgroundColor(value)
        }

    var separatorColor: Int = Color.argb(255, 0, 188, 212)
        get() = field
        set(@ColorRes value) {
            field = value
            separatorPaint.color = value
        }

    var perimeterColor: Int = Color.argb(255, 54, 54, 54)
        get() = field
        set(@ColorRes @Nullable value) {
            field = value
            perimeterPaint.color = value
        }

    var xMarkerColor: Int = Color.rgb(0, 150, 136)
        get() = field
        set(@ColorRes value) {
            field = value
        }

    var oMarkerColor: Int = Color.rgb(3, 169, 244)
        get() = field
        set(@ColorRes value) {
            field = value
        }

    val separatorPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.color = separatorColor
        paint.strokeWidth = context.resources.getDimension(R.dimen.separatorThickness)
        paint
    }

    val perimeterPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.color = perimeterColor
        paint
    }

    val density: Float by lazy(LazyThreadSafetyMode.NONE) {
        context.resources.displayMetrics.density
    }

//    val displayWidth: Int by lazy(LazyThreadSafetyMode.NONE) {
//        context.resources.displayMetrics.widthPixels
//    }
//
//    val displayHeight: Int by lazy(LazyThreadSafetyMode.NONE) {
//        context.resources.displayMetrics.heightPixels
//    }

    val gridLength: Float by lazy(LazyThreadSafetyMode.NONE) {
        val desiredWidth: Float = context.resources.getDimension(R.dimen.gridLength)
        if (width > desiredWidth) desiredWidth else width * 0.75F
    }

    val cellLength: Float by lazy(LazyThreadSafetyMode.NONE) {
        gridLength / AppModule.CELL_PER_ROW
    }

    lateinit var verticalSeparatorPath: Path
    lateinit var horizontalSeparatorPath: Path

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
        horizontalSeparatorPath = Path()
        verticalSeparatorPath = Path()

        val cellPerRow: Int = AppModule.CELL_PER_ROW.toInt()

        val displayHalfWidth: Float = width.toFloat() / 2
        val displayHalfHeight: Float = height.toFloat() / 2
        val halfGridLength: Float = gridLength / 2
        val halfCellLength: Float = cellLength / 2

        val horizontalX: Float = displayHalfWidth - halfGridLength
        var horizontalY: Float = displayHalfHeight - halfCellLength
        var verticalX: Float = displayHalfWidth - halfCellLength
        val verticalY: Float = displayHalfHeight - halfGridLength

        for (i in 0..cellPerRow - 2) {
            horizontalSeparatorPath.moveTo(horizontalX, horizontalY)
            horizontalSeparatorPath.lineTo(horizontalX + gridLength, horizontalY)
            verticalSeparatorPath.moveTo(verticalX, verticalY)
            verticalSeparatorPath.lineTo(verticalX, verticalY + gridLength)

            horizontalY += cellLength
            verticalX += cellLength
        }

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
            if (perimeterColor != backColor) {
                // TODO draw border here
            }

            canvas.drawPath(verticalSeparatorPath, separatorPaint)
            canvas.drawPath(horizontalSeparatorPath, separatorPaint)
        }
    }

    /** Helper methods **/

    private fun init(ctx: Context, attrs: AttributeSet?) {
        // Parse the data from the xml file
        if (attrs != null) {
            val typedArray = ctx.theme.obtainStyledAttributes(attrs, R.styleable.GameView, 0, 0)
            try {
                backColor = typedArray.getColor(R.styleable.GameView_backColor, R.color.defaultGameViewBackColor)
                separatorColor = typedArray.getColor(R.styleable.GameView_separatorColor, R.color.defaultSeparatorColor)
                perimeterColor = typedArray.getColor(R.styleable.GameView_perimeterColor, R.color.defaultPerimeterColor)
                xMarkerColor = typedArray.getColor(R.styleable.GameView_xMarkerColor, R.color.defaultXMarkerColor)
                oMarkerColor = typedArray.getColor(R.styleable.GameView_oMarkerColor, R.color.defaultOMarkerColor)
            } finally {
                typedArray.recycle()
            }
        }
        setBackgroundColor(backColor)
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
}