package com.example.panui_withvalidation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MainViewModel : ViewModel() {

    private val _isNextButtonEnabled = MutableLiveData(false)
    val isNextButtonEnabled: LiveData<Boolean> get() = _isNextButtonEnabled

    private val panPattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")

    private var isPanValid = false
    private var isDateValid = false

    fun validatePan(pan: String) {
        isPanValid = panPattern.matcher(pan).matches()
        updateNextButtonState()
    }

    fun validateBirthDate(day: String, month: String, year: String) {
        isDateValid = isValidDate(day, month, year)
        updateNextButtonState()
    }

    private fun isValidDate(day: String, month: String, year: String): Boolean {
        return try {
            val dayInt = day.toInt()
            val monthInt = month.toInt()
            val yearInt = year.toInt()

            if (monthInt !in 1..12) return false
            if (dayInt !in 1..31) return false
            if (yearInt < 1900 || yearInt > 2100) return false

            when (monthInt) {
                4, 6, 9, 11 -> dayInt <= 30
                2 -> if (yearInt % 4 == 0 && (yearInt % 100 != 0 || yearInt % 400 == 0)) {
                    dayInt <= 29
                } else {
                    dayInt <= 28
                }
                else -> true
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun updateNextButtonState() {
        _isNextButtonEnabled.value = isPanValid && isDateValid
    }


}
