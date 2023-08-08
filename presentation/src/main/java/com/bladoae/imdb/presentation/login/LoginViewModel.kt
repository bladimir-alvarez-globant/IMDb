package com.bladoae.imdb.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _login = MutableLiveData<Resource<Boolean>>()
    val login: LiveData<Resource<Boolean>> = _login

    fun loginUser(email: String, password: String) {
        _login.value = Resource.Loading()
        loginUserUseCase(email, password) { response ->
            if(response) {
                _login.value = Resource.Success(true)
            } else {
                _login.value = Resource.Error("Error")
            }
        }
    }

}