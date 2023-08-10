package com.bladoae.imdb.presentation.createaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.usecase.CreateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    private val _createUser = MutableLiveData<Resource<Boolean?>?>()
    val createUser: LiveData<Resource<Boolean?>?> = _createUser

    fun createAccount(email: String, password: String) {
        _createUser.value = Resource.Loading()
        viewModelScope.launch {
            createAccountUseCase(email, password).collect { response ->
                _createUser.value = if (response) Resource.Success(true) else Resource.Error("Error")
            }
        }
    }

}