package com.bladoae.imdb.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bladoae.imdb.presentation.R
import com.bladoae.imdb.presentation.theme.IMDbTheme

@Composable
fun ShowLoading() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowError(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.error_message)
        )
        Button(
            onClick = { onRetry() },
            content = {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = MaterialTheme.typography.body1,
                )
            }
        )
    }
}

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                modifier = Modifier.padding(12.dp),
                style = LocalTextStyle.current.copy(color = Color.White)
            )
        }
    }
}

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
                        androidx.compose.material3.Text(
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