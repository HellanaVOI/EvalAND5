package be.technifutur.tlm.evaluation

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun Double.isNote(): CharSequence {
    return this.toString().subSequence(0,3)
}

fun String.toCustomDate(): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}