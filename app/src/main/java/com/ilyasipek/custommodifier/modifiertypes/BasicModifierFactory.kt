package com.ilyasipek.custommodifier.modifiertypes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.cardBackground(
    color: Color
) = this then background(color = color, shape = RoundedCornerShape(16.dp))

@Preview
@Composable
private fun DuplicatedModifiersPreview() {
    Box(Modifier
        .size(200.dp)
        .background(Color.Red)
        .padding(16.dp)
        .background(Color.Yellow)
        .padding(16.dp)
        .cardBackground(Color.Blue)
        .clickable {  }
    )
}