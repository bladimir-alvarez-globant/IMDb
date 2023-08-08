package com.bladoae.imdb.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.presentation.R
import com.bladoae.imdb.presentation.common.ShowLoading

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState = loginViewModel.login.observeAsState(Resource.Content())
    LoginContent(
        uiState = uiState,
        onLogin = { email, password ->
            loginViewModel.loginUser(email, password)
        },
        onSuccess = {
            navHostController.navigate("home")
        }
    )
}

@Composable
private fun LoginContent(
    uiState: State<Resource<Boolean>>,
    onLogin: (email: String, password: String) -> Unit,
    onSuccess: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        // Create state variables to track the values of the input fields
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Calculate whether the button should be enabled based on input field values
        val isLoginEnabled = email.isNotEmpty() && password.isNotEmpty()

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            InputTextBox(hint = stringResource(id = R.string.email)) { email = it.text }
            InputTextBox(hint = stringResource(id = R.string.password)) { password = it.text }
            SimpleButton(label = stringResource(id = R.string.login), isLoginEnabled) {
                onLogin(email, password)
            }
        }
        when(uiState.value) {
            is Resource.Loading -> {
                ShowLoading()
            }
            is Resource.Success -> {
                onSuccess.invoke()
            }
            is Resource.Error -> {
                Toast.makeText(LocalContext.current, stringResource(id = R.string.error_message), Toast.LENGTH_LONG).show()
            }
            is Resource.Content -> {

            }
        }
    }
}

@Composable
private fun SimpleButton(
    label: String,
    enable: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        // * Rounded corners button
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(10.dp),
            enabled = enable
        ) {
            Text(
                text = label,
                Modifier.padding(12.dp))
        }
    }
}

@Composable
private fun InputTextBox(
    hint: String,
    onValueChange: (TextFieldValue) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        // TextField placed within the Box
        BasicTextField(
            value = textState,
            maxLines = 1,
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