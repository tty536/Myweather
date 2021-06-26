package com.example.myweather.darw

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import com.example.myweather.Database.DbManage
import com.example.myweather.R


public class TrendView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        View(context, attrs, defStyleAttr) {

    //初始化宽高信息等
    private var mWidth = 0F
    private var mHeight = 0F
    private var timeWidth = 0

    private var max = 50
    private var min = -50 //  最大值、最小值

    //初始化折线路径
    private var mPath: Path? = Path()   //    折线路径
    private var mShaderPath: Path? = Path()  //  阴影

    private var mLowPath: Path? = Path()   //    折线路径
    private var mLowShaderPath: Path? = Path()  //  阴影
    //  初始化原点坐标
    private var xOrigin = 0F //x轴
    private var yOrigin = 0F //y轴

    private var yInterval = 0f //  y轴坐标间隔
    private var xInterval = 0f //  x轴坐标间隔

    private var mProgress:Float = 0.0f
     private lateinit var interpolator :Interpolator


    //从数据库获得数据信息并存放在列表中
     var dateList  = DbManage.queryTempList("date")
    var toptempList =DbManage.queryTempList("top")
    var lowtempList = DbManage.queryTempList("low")


    val startTime = dateList?.get(0)

    //初始化样式
    val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TrendView)
    val mAxesColor = typedArray.getColor(R.styleable.TrendView_axesColor, Color.parseColor("#CCCCCC"))
    val mAxesWidth = typedArray.getDimension(R.styleable.TrendView_axesWidth, 3F)
    val mTextColor = typedArray.getColor(R.styleable.TrendView_textColor, Color.parseColor("#ABABAB"))
    val mTextSize = typedArray.getDimension(R.styleable.TrendView_textSize, 32F)
    val mLineColor = typedArray.getColor(R.styleable.TrendView_lineColor, Color.RED)
    val mBgColor = typedArray.getColor(R.styleable.TrendView_bgColor, Color.WHITE)

    //初始化渐变色
    var shadwColors = intArrayOf(Color.argb(100, 255, 86, 86), Color.argb(15, 255, 86, 86), Color.argb(0, 255, 86, 86))
    lateinit var mItems:ArrayList<Any>
    val mMargin10 =DensityUtils.dp2px(context, 10F)

    //  初始化坐标轴画笔
    private val mPaintAxes by lazy {
            Paint().also {
                it.color = mAxesColor
                it.strokeWidth = mAxesWidth
            }
    }

    //文字画笔
    private val mPaintText by lazy {
        Paint().also {
            it.color = mTextColor
            it.style =Paint.Style.FILL
            it.isAntiAlias = true
            it.textSize = mTextSize
            it.textAlign = Paint.Align.LEFT
        }
    }

    //折线画笔
    private val mPaintLine by lazy {
        Paint().also {
            it.style = Paint.Style.STROKE
            it.isAntiAlias = true
            it.strokeWidth = mAxesWidth
            it.color = mLineColor
        }
    }

    //点画笔
    private val mPaintCricle by lazy {
        Paint().also {
            it.style = Paint.Style.FILL_AND_STROKE
            it.isAntiAlias = true
            it.strokeWidth = mAxesWidth
            it.color = mLineColor
        }
    }
    //阴影画笔
    private val mPaintShader by lazy{
        Paint().also {
            it.isAntiAlias = true
            it.strokeWidth = 2F
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            mWidth = width.toFloat()
            mHeight = height-170.toFloat() //考虑到底部导航栏的的高度
            timeWidth = mPaintText!!.measureText(startTime).toInt()

            //  初始化原点坐标
            xOrigin = mMargin10.toFloat()
            yOrigin = mHeight - mTextSize - mMargin10
            setBackgroundColor(mBgColor)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //Y轴间距
        yInterval = (max - min)/(yOrigin - mMargin10)
        //X轴间距
        xInterval = (mWidth - xOrigin)  / (5-1)
        //画坐标轴
        drawAxes(canvas)
        //画文字
        drawText(canvas)
        //画折线
        drawTopLine(canvas)
        //绘制最低气温
        drawLowLine(canvas)
        //设置动画
        setAnim(canvas)
    }


    //绘制坐标轴
    private fun drawAxes(canvas: Canvas?) {
        //X轴
        canvas?.drawLine(xOrigin, yOrigin, mWidth - mMargin10, yOrigin, mPaintAxes)
        //中上刻度
        canvas?.drawLine(xOrigin, ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 10, xOrigin + 40, ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 10, mPaintAxes)
        canvas?.drawCircle(xOrigin, ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 10, 5F, mPaintAxes)
        //X中轴
        canvas?.drawLine(xOrigin, yOrigin / 2, mWidth - mMargin10, yOrigin / 2, mPaintAxes)
        canvas?.drawCircle(xOrigin, yOrigin / 2, 5F, mPaintAxes)
        //中下刻度
        canvas?.drawLine(xOrigin, mHeight - ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 10, xOrigin + 50, mHeight - ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 10, mPaintAxes)
        canvas?.drawCircle(xOrigin, mHeight - ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 10, 5F, mPaintAxes)
        //X上轴
        canvas?.drawLine(xOrigin + 100F, mMargin10.toFloat(), mWidth - mMargin10, mMargin10.toFloat(), mPaintAxes)
        canvas?.drawCircle(xOrigin, mMargin10.toFloat(), 5F, mPaintAxes)
        //Y轴
        canvas?.drawLine(xOrigin, yOrigin, xOrigin, mMargin10.toFloat(), mPaintAxes)
        //Y右轴
        canvas?.drawLine(mWidth - mMargin10, mMargin10.toFloat(), mWidth - mMargin10, yOrigin, mPaintAxes)

        //原点坐标
        canvas?.drawCircle(xOrigin, yOrigin, 5F, mPaintAxes)

    }

    private fun drawText(canvas: Canvas?) {
        //绘制气温的文字标识
        canvas?.drawText("50℃", xOrigin + 16, mMargin10.toFloat() + 10, mPaintText)
        canvas?.drawText("40℃", xOrigin + 16, ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 30, mPaintText)

        canvas?.drawText("30℃", xOrigin + 16, (mHeight + mMargin10) / 2 - 50, mPaintText)
        canvas?.drawText("20℃", xOrigin + 16, mHeight - ((mHeight + mMargin10) / 2 + mMargin10) / 2 - 30, mPaintText)

        canvas?.drawText("10℃", xOrigin + 16, yOrigin - 8, mPaintText)
        //绘制Y轴日期
        canvas?.drawText(dateList?.get(1)?.toString().toString(), xOrigin, mHeight - mMargin10, mPaintText)
        canvas?.drawText(dateList?.get(2)?.toString().toString(), xOrigin + xInterval, mHeight - mMargin10, mPaintText)
        canvas?.drawText(dateList?.get(3)?.toString().toString(), xOrigin + xInterval * 2, mHeight - mMargin10, mPaintText)
        canvas?.drawText(dateList?.get(4)?.toString().toString(), xOrigin + xInterval * 3, mHeight - mMargin10, mPaintText)
    }

    private fun drawTopLine(canvas: Canvas?) {
        for (i in 1 until 5){
             var x = (i-1)*xInterval+xOrigin+mAxesWidth+xInterval/2
            if (i == 1 ){
                canvas?.drawText(toptempList?.get(1).toString(), x - 5 - mMargin10 - mAxesWidth, (1 - (toptempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10 - mMargin10), mPaintText)
                canvas?.drawCircle(x - mMargin10 - mAxesWidth, (1 - (toptempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10), 5F, mPaintCricle)
                mShaderPath?.moveTo(x - mMargin10 - mAxesWidth, (1 - (toptempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
                mPath?.moveTo(x - mMargin10 - mAxesWidth, (1 - (toptempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
            }else{
                canvas?.drawText(toptempList?.get(i).toString(), x - 5 - mMargin10 - mAxesWidth, (1 - (toptempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10 - mMargin10), mPaintText)
                canvas?.drawCircle(x - mMargin10 - mAxesWidth, (1 - (toptempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10), 5F, mPaintCricle)
                mPath?.lineTo(x - mMargin10 - mAxesWidth, (1 - (toptempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
                mShaderPath?.lineTo(x - mMargin10 - mAxesWidth, (1 - (toptempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
                if (i==4){
                    mShaderPath?.lineTo(mWidth - mMargin10 - mAxesWidth, yOrigin)
                    mShaderPath?.lineTo(xOrigin, yOrigin)
                    mShaderPath?.close()
                }
            }
        }
        val mShader: Shader = LinearGradient(0F, 0F, 0F, yOrigin, shadwColors, null, Shader.TileMode.CLAMP)
        mPaintShader!!.shader = mShader
        canvas?.drawPath(mShaderPath!!, mPaintShader)
        //canvas?.drawPath(mPath!!, mPaintLine)
    }

    private fun drawLowLine(canvas: Canvas?) {
        for (i in 1 until 5){
            var x = (i-1)*xInterval+xOrigin+mAxesWidth+xInterval/2
            if (i == 1 ){
                canvas?.drawText(lowtempList?.get(1).toString(), x - 5 - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10 - mMargin10), mPaintText)
                canvas?.drawCircle(x - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10), 5F, mPaintCricle)
                mLowShaderPath?.moveTo(x - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
                mLowPath?.moveTo(x - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(1)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
            }else{
                canvas?.drawText(lowtempList?.get(i).toString(), x - 5 - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10 - mMargin10), mPaintText)
                canvas?.drawCircle(x - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10), 5F, mPaintCricle)
                mLowPath?.lineTo(x - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
                mLowShaderPath?.lineTo(x - mMargin10 - mAxesWidth, (1 - (lowtempList?.get(i)!!.toFloat() - 10) / 40) * (mHeight - 2 * mMargin10))
                if (i==4){
                    mLowShaderPath?.lineTo(mWidth - mMargin10 - mAxesWidth, yOrigin)
                    mLowShaderPath?.lineTo(xOrigin, yOrigin)
                    mLowShaderPath?.close()
                }
            }
        }
        val mLowShader: Shader = LinearGradient(0F, 0F, 0F, yOrigin, shadwColors, null, Shader.TileMode.CLAMP)
        mPaintShader!!.shader = mLowShader
        canvas?.drawPath(mLowShaderPath!!, mPaintShader)
    }

    private fun setAnim(canvas: Canvas?) {
        val measure = PathMeasure(mPath, false)
        val pathLength = measure.length
        val effect: PathEffect = DashPathEffect(floatArrayOf(pathLength,
                pathLength), pathLength - pathLength * mProgress)
        mPaintLine.pathEffect = effect
        canvas?.drawPath(mPath!!, mPaintLine)
        canvas?.drawPath(mLowPath!!,mPaintLine)
    }

    //动画特效
    //  属性动画的set方法
    open fun setPercentage(percentage: Float) {
        require(!(percentage < 0.0f || percentage > 1.0f)) { "setPercentage not between 0.0f and 1.0f" }
        mProgress = percentage
        invalidate()
    }
    fun startAnim(duration: Long) {
        val anim = ObjectAnimator.ofFloat(this, "percentage", 0.0f, 1.0f)
        anim.duration = duration
        anim.setInterpolator(interpolator)
        anim.start()
    }

    fun setInterpolator(interpolator: Interpolator?) {
        this.interpolator = interpolator!!
    }


}

