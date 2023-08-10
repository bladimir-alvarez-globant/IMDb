package com.bladoae.imdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.usecase.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    private val _login = MutableLiveData<Resource<Boolean?>?>()
    val login: LiveData<Resource<Boolean?>?> = _login

    fun isUserLoggedIn() {
        viewModelScope.launch {
            val response = isUserLoggedInUseCase()
            if(response) {
                _login.value = Resource.Success(true)
            } else {
                _login.value = Resource.Error("Error")
            }
        }
    }

}