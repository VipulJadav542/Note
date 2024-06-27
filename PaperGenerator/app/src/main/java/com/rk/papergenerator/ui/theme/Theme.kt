package com.rk.papergenerator.ui.theme

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat


private val lightColorScheme = lightColorScheme(
    primary = customTufeeColorsLight.primary,
    onPrimary = customTufeeColorsLight.onPrimary,
    primaryContainer = customTufeeColorsLight.primaryContainer,
    onPrimaryContainer = customTufeeColorsLight.onPrimaryContainer,
    secondary = customTufeeColorsLight.secondary,
    onSecondary = customTufeeColorsLight.onSecondary,
    secondaryContainer = customTufeeColorsLight.secondaryContainer,
    onSecondaryContainer = customTufeeColorsLight.onSecondaryContainer,
    tertiary = customTufeeColorsLight.tertiary,
    onTertiary = customTufeeColorsLight.onTertiary,
    tertiaryContainer = customTufeeColorsLight.tertiaryContainer,
    onTertiaryContainer = customTufeeColorsLight.onTertiaryContainer,
    error = customTufeeColorsLight.error,
    onError = customTufeeColorsLight.onError,
    errorContainer = customTufeeColorsLight.errorContainer,
    onErrorContainer = customTufeeColorsLight.onErrorContainer,
    outline = customTufeeColorsLight.outline,
    background = customTufeeColorsLight.background,
    onBackground = customTufeeColorsLight.onBackground,
    surface = customTufeeColorsLight.surface,
    onSurface = customTufeeColorsLight.onSurface,
    surfaceVariant = customTufeeColorsLight.surfaceVariant,
    onSurfaceVariant = customTufeeColorsLight.onSurfaceVariant,
    inverseSurface = customTufeeColorsLight.inverseSurface,
    inverseOnSurface = customTufeeColorsLight.inverseOnSurface,
    inversePrimary = customTufeeColorsLight.inversePrimary,
    surfaceTint = customTufeeColorsLight.surfaceTint,
    outlineVariant = customTufeeColorsLight.outlineVariant,
    scrim = customTufeeColorsLight.scrim,
)

private val darkColorScheme = darkColorScheme(
    primary = customTufeeColorsDark.primary,
    onPrimary = customTufeeColorsDark.onPrimary,
    primaryContainer = customTufeeColorsDark.primaryContainer,
    onPrimaryContainer = customTufeeColorsDark.onPrimaryContainer,
    secondary = customTufeeColorsDark.secondary,
    onSecondary = customTufeeColorsDark.onSecondary,
    secondaryContainer = customTufeeColorsDark.secondaryContainer,
    onSecondaryContainer = customTufeeColorsDark.onSecondaryContainer,
    tertiary = customTufeeColorsDark.tertiary,
    onTertiary = customTufeeColorsDark.onTertiary,
    tertiaryContainer = customTufeeColorsDark.tertiaryContainer,
    onTertiaryContainer = customTufeeColorsDark.onTertiaryContainer,
    error = customTufeeColorsDark.error,
    onError = customTufeeColorsDark.onError,
    errorContainer = customTufeeColorsDark.errorContainer,
    onErrorContainer = customTufeeColorsDark.onErrorContainer,
    outline = customTufeeColorsDark.outline,
    background = customTufeeColorsDark.background,
    onBackground = customTufeeColorsDark.onBackground,
    surface = customTufeeColorsDark.surface,
    onSurface = customTufeeColorsDark.onSurface,
    surfaceVariant = customTufeeColorsDark.surfaceVariant,
    onSurfaceVariant = customTufeeColorsDark.onSurfaceVariant,
    inverseSurface = customTufeeColorsDark.inverseSurface,
    inverseOnSurface = customTufeeColorsDark.inverseOnSurface,
    inversePrimary = customTufeeColorsDark.inversePrimary,
    surfaceTint = customTufeeColorsDark.surfaceTint,
    outlineVariant = customTufeeColorsDark.outlineVariant,
    scrim = customTufeeColorsDark.scrim,
)

val LocalAppColor = compositionLocalOf { customTufeeColorsLight }
val LocalAppTypography = compositionLocalOf { AppTypography(density = Density(3.0f, 1.0f)) }

object PaperAppTheme {

    val colors: TufeeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColor.current

    val textStyles: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val activity : Activity?
        @Composable
        @ReadOnlyComposable
        get() {
            var context = LocalContext.current
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
}

@Composable
fun PaperGeneratorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val density = LocalDensity.current
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val appColors = if(darkTheme) customTufeeColorsDark else customTufeeColorsLight

    val appTypography = remember { AppTypography(density = Density(3.0f, 1.0f)) }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            CompositionLocalProvider(
                LocalAppColor provides appColors,
                LocalAppTypography provides appTypography,
            ) {
                content()
            }
        }
    )
}

//@HiltViewModel
//class TufeeThemeViewModel @Inject constructor(
//    val sharePrefHelper: SharePrefHelper,
//    val smsHelper: SmsHelper
//) : ViewModel()
