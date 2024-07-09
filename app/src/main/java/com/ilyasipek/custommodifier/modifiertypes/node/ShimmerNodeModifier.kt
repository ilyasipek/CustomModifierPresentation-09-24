package com.ilyasipek.custommodifier.modifiertypes.node

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Density
import kotlinx.coroutines.launch

fun Modifier.shimmer(
    gradientColors: List<Color> = getDefaultGradientColors(),
    animationDuration: Int = 1000,
) = this then ShimmerElement(
    gradientColors = gradientColors,
    animationDuration = animationDuration,
    inspectorInfo = debugInspectorInfo {
        name = "shimmer"
        properties["gradientColors"] = gradientColors
        properties["animationDuration"] = animationDuration
    },
)

private data class ShimmerElement(
    val gradientColors: List<Color>,
    val animationDuration: Int,
    private val inspectorInfo: InspectorInfo.() -> Unit,
) : ModifierNodeElement<ShimmerNode>() {
    override fun create(): ShimmerNode = ShimmerNode(gradientColors, animationDuration)

    override fun update(node: ShimmerNode) {
        node.update(
            gradientColors = gradientColors,
            animationDuration = animationDuration,
        )
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }
}

// TODO: explain the lifecycle of the node.

private class ShimmerNode(
    private var gradientColors: List<Color>,
    private var animationDuration: Int,
) : Modifier.Node(), DrawModifierNode, CompositionLocalConsumerModifierNode {

    private val translationAnimatable = Animatable(0f)

    override fun onAttach() {
        super.onAttach()
        startTranslationAnimation()
    }

    override fun ContentDrawScope.draw() {
        /**
         * Auto redraws the node when the value of the [LocalDensity] changes.
         * TODO: Show other scopes and observable values.
         * */
        val localeDensity = currentValueOf(LocalDensity)
//        val contentColor = currentValueOf(LocalContentColor)
        val brush = getBrush(gradientColors, translationAnimatable.value, localeDensity)
        drawRect(brush)
        drawContent()
    }

    override fun onDetach() {
        super.onDetach()
        println("ShimmerNode.onDetach() is called")
    }

    fun update(
        gradientColors: List<Color>,
        animationDuration: Int,
    ) {
        this.gradientColors = gradientColors
        if (this.animationDuration != animationDuration) {
            this.animationDuration = animationDuration
            startTranslationAnimation()
        }
    }

    private fun startTranslationAnimation() {
        coroutineScope.launch {
            if (translationAnimatable.isRunning) {
                translationAnimatable.snapTo(0f)
            }
            translationAnimatable.animateTo(
                targetValue = animationDuration.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDuration,
                        easing = FastOutLinearInEasing,
                        delayMillis = animationDuration / 4,
                    ),
                ),
            )
        }
    }

    private fun getBrush(
        gradientColors: List<Color>,
        translateAnimation: Float,
        localeDensity: Density,
    ): Brush {
        val initialOffset = with(localeDensity) { 16.toDp() }
        return Brush.linearGradient(
            colors = gradientColors,
            start = Offset(initialOffset.value, initialOffset.value),
            end = Offset(
                x = translateAnimation,
                y = translateAnimation,
            ),
        )
    }
}

fun getDefaultGradientColors() = listOf(
    Color.Gray.copy(alpha = 0.9f),
    Color.Gray.copy(alpha = 0.7f),
    Color.Gray.copy(alpha = 0.5f),
    Color.Gray.copy(alpha = 0.7f),
    Color.Gray.copy(alpha = 0.9f),
)

fun getLightGrayGradientColors() = listOf(
    Color.LightGray.copy(alpha = 0.9f),
    Color.LightGray.copy(alpha = 0.7f),
    Color.LightGray.copy(alpha = 0.5f),
    Color.LightGray.copy(alpha = 0.7f),
    Color.LightGray.copy(alpha = 0.9f),
)
