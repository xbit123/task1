package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User =
        if (!map.any { it.value.login == email.toLowerCase() })
            User.makeUser(fullName, email = email, password = password)
                .also { user -> map[user.login] = user }
        else throw IllegalArgumentException("A user with this email already exists")

    fun registerUserByPhone(fullName: String, rawPhone: String): User {
        val phone = normalizePhone(rawPhone)
        return if (phone != null)
            if (map.any { it.value.login == phone })
                throw IllegalArgumentException("A user with this phone already exists")
            else
                User.makeUser(fullName, phone = phone)
                    .also { user -> map[user.login] = user }
        else throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
    }


    private fun normalizePhone(rawPhone: String): String? {
        return if (rawPhone.any { it in (('a'..'z') + ('A'..'Z')) }) null
        else
            rawPhone.replace("""[^+\d]""".toRegex(), "").let {
                if (it.first() == '+' && it.length == 12) it
                else null
            }
    }

    fun loginUser(login: String, password: String): String? {
        var loginToCheck = normalizePhone(login)
        if (loginToCheck == null)
            loginToCheck = login.trim()
        return map[loginToCheck]?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }
    }

    fun requestAccessCode(rawPhone: String) =
        map[normalizePhone(rawPhone)]?.updateAccessCode()

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }
}