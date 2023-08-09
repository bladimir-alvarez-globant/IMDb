package com.bladoae.imdb.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bladoae.imdb.presentation.theme.IMDbTheme

@Composable
fun InputTextBox(
    hint: String,
    typePassword: Boolean,
    onValueChange: (TextFieldValue) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 10.dp, 20.dp, 0.dp),
        contentAlignment = Alignment.Center,
    ) {
        // TextField placed within the Box
        BasicTextField(
            value = textState,
            maxLines = 1,
            singleLine = true,
            visualTransformation = if(typePassword) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = {
                onValueChange.invoke(it)
                textState = it
            },
            textStyle = LocalTextStyle.current.copy(color = Color.LightGray),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color.White, RoundedCornerShape(percent = 10))
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    if (textState.text.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color.LightGray
                        )
                    }
                    // <-- Add this
                    innerTextField()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputTextBoxPreview() {
    IMDbTheme {
        InputTextBox(
            "Password",
            true,
            {  }
        )
    }
}