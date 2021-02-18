package dev.mcd.untitledcaloriesapp.presentation.component.overview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import dev.mcd.untitledcaloriesapp.R
import dev.mcd.untitledcaloriesapp.databinding.WeeklyOverviewBinding

class WeeklyOverviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var limit: Int = 0
        set(value) {
            field = value
            updateDayValues()
        }

    var dayValues: List<Int> = 0.until(7).map { 0 }
        set(value) {
            field = value
            updateDayValues()
        }

    private val binding: WeeklyOverviewBinding

    private val font: Typeface? by lazy {
        if (!isInEditMode) {
            ResourcesCompat.getFont(context, R.font.futura_book)
        } else null
    }

    private val dayLabels = resources.getStringArray(R.array.weekly_overview_day_labels)

    init {
        inflate(context, R.layout.weekly_overview, this)
        binding = WeeklyOverviewBinding.bind(this)
    }

    private fun updateDayValues() {
        val yValues = dayValues.mapIndexed { i, j ->
            Entry(i.toFloat(), j.toFloat())
        }
        val dataSet = LineDataSet(yValues, "Weekly Overview").also(this::styleDataSet)
        with(binding.lineChart) {
            data = LineData(dataSet)
            invalidate()
        }

        setupChart()
    }

    private fun styleDataSet(dataSet: LineDataSet) {
        // TODO: Apply from styles
        dataSet.apply {
            setDrawCircles(true)
            setDrawCircleHole(false)
            setDrawValues(false)
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.weekly_overview_fill)
            font?.let { valueTypeface = it }
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            circleColors = listOf(
                ContextCompat.getColor(context, R.color.teal_700)
            )
            valueTextSize = 11f
            color = Color.BLACK
            cubicIntensity = 1.3f
            label = null
        }
    }

    private fun setupChart() {
        // TODO: Apply from styles
        with(binding.lineChart) {
            description = null
            axisRight.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawGridLinesBehindData(false)
                font?.let { typeface = it }
                axisMinimum = 0f
            }

            axisLeft.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawGridLinesBehindData(false)
                removeAllLimitLines()
                addLimitLine(
                    LimitLine(limit.toFloat(), null).apply {
                        lineColor = ContextCompat.getColor(context, R.color.purple_700)
                        enableDashedLine(16f, 8f, 4f)
                        lineWidth = 1.5f
                    }
                )
                axisMinimum = 0f
            }

            xAxis.apply {
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return dayLabels[value.toInt()]
                    }
                }
                setDrawGridLines(false)
                setDrawLabels(true)
                setDrawAxisLine(false)
                font?.let { typeface = it }
                textSize = 14f
                textColor = ContextCompat.getColor(context, R.color.grey1)
            }

            extraTopOffset = 12f
            legend.isEnabled = false

            setScaleEnabled(false)
            setTouchEnabled(false)
            setDrawMarkers(false)
        }
    }
}
