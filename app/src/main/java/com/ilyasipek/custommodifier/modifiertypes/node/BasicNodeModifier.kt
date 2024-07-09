@file:Suppress("ModifierNodeInspectableProperties")

package com.ilyasipek.custommodifier.modifiertypes.node

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Our goal...
/*
    @Composable
    private fun Modifier.myRandomBackgroundGoal(
        alpha: Float,
     ): Modifier {
        val color = remember { mutableStateOf(listOf(Color.Red, Color.Cyan, Color.Green).random()) }
        return this then Modifier.background(color = color.value.copy(alpha = alpha))
     }
*/

// // Step 1 - Create a Node.
private class RandomBackgroundNode(
    var alpha: Float,
) : Modifier.Node(), DrawModifierNode {

    val randomColor = listOf(Color.Red, Color.Cyan, Color.Green).random()

    override fun ContentDrawScope.draw() {
        drawRect(randomColor.copy(alpha = alpha))
        drawContent()
    }
}

// Step 2 - Create a Node element.
// TODO: explain why it should be data class or implements equals and hashCode.
private data class RandomBackgroundElement(
    val alpha: Float,
) : ModifierNodeElement<RandomBackgroundNode>() {
    override fun create(): RandomBackgroundNode = RandomBackgroundNode(alpha)

    override fun update(node: RandomBackgroundNode) {
        node.alpha = alpha
    }
}

// Step 3 - Create a Modifier factory.
fun Modifier.myRandomBackground(
    alpha: Float = 1f,
): Modifier {
    return this then RandomBackgroundElement(alpha)
}

@Composable
private fun MyBackgroundUsingNodeAPI() {
    // TODO: extract it to outside of the @Composable scope.
    val x = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(16.dp))
        .myRandomBackground(alpha = 1f)

    LazyColumn {
        items(6) {
            Box(
                modifier = x.size(100.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("Hello")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyBackgroundUsingNodeAPIPreview() {
    MyBackgroundUsingNodeAPI()
}