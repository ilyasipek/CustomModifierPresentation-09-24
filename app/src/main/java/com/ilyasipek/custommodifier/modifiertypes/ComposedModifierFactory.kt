package com.ilyasipek.custommodifier.modifiertypes

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private fun Modifier.rotateOnClick() = composed {
    val color =
        remember { mutableStateOf(listOf(Color.Red, Color.Green, Color.Yellow, Color.Cyan, Color.Gray).random()) }
    var isClicked by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (isClicked) 45f else 0f)


    this then Modifier.background(color = color.value)
        .clickable { isClicked = !isClicked }
        .graphicsLayer { rotationZ = rotation }
}

@Composable
private fun Modifier.myBackground() = composed {
    val color = LocalContentColor.current
    this then Modifier.background(color.copy(alpha = 0.5f))
}

@Preview
@Composable
private fun CallSiteModifierDemo() {
    CompositionLocalProvider(LocalContentColor provides Color.Green) {
        // Background modifier created with green background
        val backgroundModifier = Modifier.myBackground()

        // LocalContentColor updated to red
        CompositionLocalProvider(LocalContentColor provides Color.Red) {

            // Box will have green background, not red as expected.
            Box(modifier = backgroundModifier.size(200.dp))
        }
    }
}

@Preview
@Composable
private fun RotateOnClickPreview() {
    val extractedModifier = Modifier
        .size(100.dp)
        .rotateOnClick()

    Row {
        LazyColumn {
            items(10) {
                Box(modifier = extractedModifier, contentAlignment = Alignment.Center) {
                    Text("Click me")
                }
            }
        }
        LazyColumn {
            items(10) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .rotateOnClick(), contentAlignment = Alignment.Center
                ) {
                    Text("Click me")
                }
            }
        }
    }
}


