package dominando.android.paceinpeacemvp.form

import dominando.android.paceinpeacemvp.model.Running

class RunningValidator {
    fun validate(info: Running) = with(info) {
        checkDate(date) && checkDuration(duration)
    }
    //implemente um método de check aceitável
    private fun checkDate(date: String) = true
    private fun checkDuration(duration: Float) = true
}