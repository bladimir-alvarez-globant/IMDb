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

    private val _createAccount = MutableLiveData<Resource<Boolean?>?>()
    val createAccount: LiveData<Resource<Boolean?>?> = _createAccount

    fun createAccount(email: String, password: String) {
        _createAccount.value = Resource.Loading()
        viewModelScope.launch {
            createAccountUseCase(email, password).collect { response ->
                _createAccount.value = if (response) Resource.Success(true) else Resource.Error("Error")
            }
        }
    }

}