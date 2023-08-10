package com.bladoae.imdb.base.utils

import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    val emailAddressPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return emailAddressPattern.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return if(this.length > 8) {
        val passwordPattern = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}\$"
        )
        passwordPattern.matcher(this).matches()
    } else {
        false
    }
}