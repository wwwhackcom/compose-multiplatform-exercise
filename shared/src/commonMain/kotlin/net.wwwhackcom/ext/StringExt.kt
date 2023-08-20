package net.wwwhackcom.ext

private const val EMAIL_LENGTH = 32
private const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
private const val PASSWORD_MIN_LENGTH = 6

fun String.validateEmail(): String? {
    return if (this.isBlank()) {
        "Please type your email"
    } else if (this.length > EMAIL_LENGTH) {
        "Email address is too long: ${this.length}/$EMAIL_LENGTH"
    } else if (!this.matches(EMAIL_REGEX.toRegex())) {
        "Email not valid"
    } else {
        null
    }
}

fun String.validatePassword(): String? {
    return if (this.isBlank()) {
        "Please type your password"
    } else if (this.length < PASSWORD_MIN_LENGTH) {
        "Password minimum length is : ${this.length}/$PASSWORD_MIN_LENGTH"
    } else {
        null
    }
}