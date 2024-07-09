package com.ilyasipek.custommodifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Inlined() {
    Box(
        modifier = Modifier.background(Color.Red)
    )
}

@Composable
fun ExtractedWithinComposition() {
    val extractedModifier = Modifier.background(Color.Red)
    LazyColumn {
        items(10) {
            Box(modifier = extractedModifier)
        }
    }
}

val extractedModifier = Modifier.background(Color.Red)

@Composable
fun ExtractedOutsideComposition() {
    LazyColumn {
        items(10) {
            Box(modifier = extractedModifier)
        }
    }
}