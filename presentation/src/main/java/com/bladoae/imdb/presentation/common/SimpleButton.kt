package com.bladoae.imdb.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bladoae.imdb.presentation.theme.IMDbTheme

@Composable
fun SimpleButton(
    label: String,
    enable: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(20.dp, 10.dp, 20.dp, 0.dp)
    ) {

        // * Rounded corners button
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(10.dp),
            enabled = enable,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, disabledContentColor = Color.LightGray)
        ) {
            Text(
                text = label,
                modifier = Modifier.padding(12.dp),
                style = LocalTextStyle.current.copy(color = Color.White)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleButtonPreview() {
    IMDbTheme {
        SimpleButton(
            "Login",
            false,
            {  }
        )
    }
}