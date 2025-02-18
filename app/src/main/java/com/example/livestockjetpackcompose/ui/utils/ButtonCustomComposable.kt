package com.example.livestockjetpackcompose.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livestockjetpackcompose.ui.theme.background_danger_button
import com.example.livestockjetpackcompose.ui.theme.background_simple_button
import com.example.livestockjetpackcompose.ui.theme.background_special_button

enum class ButtonType {
    SPECIAL,
    SIMPLE,
    DANGER
}

@Composable
fun ButtonCustom(type: ButtonType, buttonText: String, onClicked: () -> Unit) {

    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)

    val commonButtonProperties: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        disabledContentColor = Color.LightGray,
        disabledContainerColor = Color.LightGray
    )

    val commonTextProperties: @Composable () -> Unit = {
        Text(
            text = buttonText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }

    val (containerColor, textColor) = when (type) {
        ButtonType.SPECIAL -> background_special_button to Color.White
        ButtonType.SIMPLE -> background_simple_button to Color.Black
        ButtonType.DANGER -> background_danger_button to Color.White
    }

    Button(
        onClick = { onClicked() },
        modifier = commonModifier,
        colors = commonButtonProperties.copy(containerColor = containerColor),
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.buttonElevation(10.dp)
    ) {
        if (type == ButtonType.SIMPLE) {
            Text(
                text = buttonText,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = textColor
            )
        } else {
            commonTextProperties()
        }
    }

}