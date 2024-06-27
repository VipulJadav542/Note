package com.rk.papergenerator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rk.papergenerator.R

val customFontFamily = FontFamily(
    Font(resId = R.font.bw_black, style = FontStyle.Normal, weight = FontWeight.Black),
    Font(resId = R.font.bw_black_italic, style = FontStyle.Italic, weight = FontWeight.Black),
    Font(resId = R.font.bw_bold, style = FontStyle.Normal, weight = FontWeight.Bold),
    Font(resId = R.font.bw_bold_italic, style = FontStyle.Italic, weight = FontWeight.Bold),
    Font(resId = R.font.bw_light, style = FontStyle.Normal, weight = FontWeight.Light),
    Font(resId = R.font.bw_light_italic, style = FontStyle.Italic, weight = FontWeight.Light),
    Font(resId = R.font.bw_medium, style = FontStyle.Normal, weight = FontWeight.Medium),
    Font(resId = R.font.bw_medium_italic, style = FontStyle.Italic, weight = FontWeight.Medium),
    Font(resId = R.font.bw_regular, style = FontStyle.Normal, weight = FontWeight.W400),
    Font(resId = R.font.bw_regular_italic, style = FontStyle.Italic, weight = FontWeight.W400),
    Font(resId = R.font.bw_thin, style = FontStyle.Normal, weight = FontWeight.Thin),
    Font(resId = R.font.bw_thin_italic, style = FontStyle.Italic, weight = FontWeight.Thin),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

data class SizeOfText(
    private val fontWeight: FontWeight,
    private val density: Density,
    val regular: TextStyle = TextStyle(fontWeight = fontWeight, fontFamily = customFontFamily, fontSize = with(density){ 16f.dp.toSp().value }.sp),
    val small: TextStyle = TextStyle(fontWeight = fontWeight, fontFamily = customFontFamily, fontSize = with(density){ 14f.dp.toSp().value }.sp),
    val extraSmall: TextStyle = TextStyle(fontWeight = fontWeight, fontFamily = customFontFamily, fontSize = with(density){ 12f.dp.toSp().value }.sp),
    val large: TextStyle = TextStyle(fontWeight = fontWeight, fontFamily = customFontFamily, fontSize = with(density){ 18f.dp.toSp().value }.sp),
    val extraLarge: TextStyle = TextStyle(fontWeight = fontWeight, fontFamily = customFontFamily, fontSize = with(density){ 20f.dp.toSp().value }.sp)
)

/**
 * Weight+Size
 */
data class AppTypography(
    private val density: Density,
    val thin: SizeOfText = SizeOfText(fontWeight = FontWeight.Thin, density = density),
    val extraLight: SizeOfText = SizeOfText(fontWeight = FontWeight.ExtraLight, density = density),
    val light: SizeOfText = SizeOfText(fontWeight = FontWeight.Light, density = density),
    val normal: SizeOfText = SizeOfText(fontWeight = FontWeight.Normal, density = density),
    val medium: SizeOfText = SizeOfText(fontWeight = FontWeight.Medium, density = density),
    val semiBold: SizeOfText = SizeOfText(fontWeight = FontWeight.SemiBold, density = density),
    val bold: SizeOfText = SizeOfText(fontWeight = FontWeight.Bold, density = density),
    val extraBold: SizeOfText = SizeOfText(fontWeight = FontWeight.ExtraBold, density = density),
    val black: SizeOfText = SizeOfText(fontWeight = FontWeight.Black, density = density),
)

