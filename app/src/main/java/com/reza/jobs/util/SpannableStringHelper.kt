package com.reza.jobs.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.*
import android.text.style.BulletSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.reza.jobs.R
import com.reza.jobs.util.bulletspan.ImprovedBulletSpan
import org.xml.sax.XMLReader

object SpannableStringHelper {

    fun fixListBulletHtml(context: Context?, html: String?): SpannableStringBuilder {
        context?.let {
            html?.let {
                val htmlSpannable = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    Html.fromHtml(html, null, UlTagHandler())
                } else {
                    HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
                }
                val spannableBuilder = SpannableStringBuilder(htmlSpannable)
                val bulletSpans = spannableBuilder.getSpans(0, spannableBuilder.length, BulletSpan::class.java)
                bulletSpans.forEach {
                    val start = spannableBuilder.getSpanStart(it)
                    val end = spannableBuilder.getSpanEnd(it)
                    spannableBuilder.removeSpan(it)
                    spannableBuilder.setSpan(
                        dip(2, context.resources)?.let { it1 -> dip(8, context.resources)?.let { it2 -> ImprovedBulletSpan(bulletRadius = it1, gapWidth = it2) } },
                        start,
                        end,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }

                return spannableBuilder
            }
        }

        return SpannableStringBuilder("")
    }

    class UlTagHandler : Html.TagHandler {
        override fun handleTag(
            opening: Boolean,
            tag: String,
            output: Editable,
            xmlReader: XMLReader?
        ) {
            if (tag == "ul" && !opening) output.append("\n")
            if (tag == "li" && opening) output.append("\n\t•")
        }
    }

    fun linkInsideText(
        context: Context,
        text: Spanned,
        clickableText: String,
        clickableSpan: ClickableSpan?
    ): SpannableString {
        val ss = SpannableString(text)
        val i1 = text.toString().indexOf(clickableText)
        val i2 = i1 + clickableText.length
        ss.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.link)),
            i1,
            i2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ss.setSpan(clickableSpan, i1, i2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ss
    }

    private fun dip(dp: Int?, resources: Resources?): Int? {
        return dp?.let { TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, it.toFloat(), resources?.displayMetrics).toInt() }
    }
}
