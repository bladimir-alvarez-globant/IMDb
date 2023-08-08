package com.bladoae.imdb.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bladoae.imdb.base.common.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private var mAuth: FirebaseAuth? = null

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    private val _login = MutableLiveData<Resource<Boolean>>()
    val login: LiveData<Resource<Boolean>> = _login

    fun loginUser(email: String, password: String) {
        _login.value = Resource.Loading()
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            if(it.isSuccessful) {
                _login.value = Resource.Success(true)
            } else {
                _login.value = Resource.Error("Error")
            }
        }
    }

}