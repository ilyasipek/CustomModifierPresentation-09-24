package com.ilyasipek.custommodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilyasipek.custommodifier.modifiertypes.node.getDefaultGradientColors
import com.ilyasipek.custommodifier.modifiertypes.node.getLightGrayGradientColors
import com.ilyasipek.custommodifier.modifiertypes.node.shimmer
import com.ilyasipek.custommodifier.ui.theme.CustomModifierPresentationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomModifierPresentationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var shimmerColors by remember {
                        mutableStateOf(getDefaultGradientColors())
                    }
                    var isDefaultSimmerColors by remember {
                        mutableStateOf(true)
                    }
                    var duration by remember {
                        mutableIntStateOf(1000)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .shimmer(
                                    gradientColors = shimmerColors,
                                    animationDuration = duration,
                                )
                                .padding(16.dp)
                                .size(200.dp)
                        )

                        Box(modifier = Modifier.size(16.dp))

                        Button(
                            onClick = {
                                shimmerColors = if (isDefaultSimmerColors) {
                                    getLightGrayGradientColors()
                                } else {
                                    getDefaultGradientColors()
                                }
                                isDefaultSimmerColors = !isDefaultSimmerColors
                            },
                        ) {
                            Text("Change shimmer color")
                        }

                        Button(
                            onClick = {
                                duration = if (duration == 1000) {
                                    2000
                                } else {
                                    1000
                                }
                                isDefaultSimmerColors = !isDefaultSimmerColors
                            },
                        ) {
                            Text("Change animation duration")
                        }
                    }
                }
            }
        }
    }
}