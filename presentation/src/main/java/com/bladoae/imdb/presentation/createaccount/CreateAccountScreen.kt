package com.bladoae.imdb.presentation.createaccount

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bladoae.imdb.base.common.NavigationOptions
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.base.utils.isValidEmail
import com.bladoae.imdb.base.utils.isValidPassword
import com.bladoae.imdb.presentation.R
import com.bladoae.imdb.presentation.common.InputTextBox
import com.bladoae.imdb.presentation.common.LoadingMessage
import com.bladoae.imdb.presentation.common.SimpleButton
import com.bladoae.imdb.presentation.theme.IMDbTheme

@Composable
fun CreateAccountScreen(
    navHostController: NavHostController,
    createAccountViewModel: CreateAccountViewModel = hiltViewModel()
) {
    val uiState = createAccountViewModel.createAccount.observeAsState()
    CreateAccountContent(
        uiState,
        onSignIn = { email, password ->
            createAccountViewModel.createAccount(email, password)
        },
        onSuccess = {
            navHostController.navigate(NavigationOptions.HOME.path)
        }
    )
}

@Composable
private fun CreateAccountContent(
    uiState: State<Resource<Boolean?>?>? = null,
    onSignIn: (email: String, password: String) -> Unit,
    onSuccess: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val isSignInEnabled = email.isValidEmail() && password.isValidPassword()

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(colorResource(id = R.color.colorPrimary)),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.mipmap.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally)
            )
            InputTextBox(
                hint = stringResource(id = R.string.email),
                typePassword = false
            ) {
                email = it.text
            }
            InputTextBox(
                hint = stringResource(id = R.string.password),
                typePassword = true
            ) {
                password = it.text
            }
            SimpleButton(
                label = stringResource(id = R.string.create_account),
                isSignInEnabled
            ) {
                onSignIn(email, password)
            }
        }
        val context = LocalContext.current
        val errorMessage = stringResource(id = R.string.error_message)
        uiState?.value?.let { states ->
            when(states) {
                is Resource.Loading -> {
                    LoadingMessage()
                }
                is Resource.Success -> {
                    LaunchedEffect(key1 = "navigation", block = {
                        onSuccess.invoke()
                    })
                }
                is Resource.Error -> {
                    LaunchedEffect(key1 = "errorMessage", block = {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG)
                            .show()
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountPreview() {
    IMDbTheme {
        CreateAccountContent(
            onSignIn = { email, password ->

            },
            onSuccess = {

            })
    }
}