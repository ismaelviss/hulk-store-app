package com.ismaelviss.hulkstore.utils

import android.R
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Tools {
    companion object {
        fun setSystemBarColor(act: Activity, @ColorRes color: Int) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(color)
        }

        fun setSystemBarLight(act: Activity) {
            val view = act.findViewById<View>(R.id.content)
            val flags = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }

        fun getFormatMoney(valor: Double?): String {

            val amount = valor ?: 0.0
            val nf = NumberFormat.getNumberInstance(Locale.US)
            val formatter = nf as DecimalFormat
            formatter.applyPattern("$#,##0.00")

            return formatter.format(amount)
        }

        fun formatDate(dateTransaction: Date): String {
            val currentLocale = Locale("es", "EC")
            val pattern = "E, dd MMM yyyy - HH:mm"
            val fecha = dateTransaction
            val simpleDateFormat = SimpleDateFormat(pattern)
            val capitalDays = arrayOf(
                "",
                "Domingo",
                "Lunes",
                "Martes",
                "Miércoles",
                "Jueves",
                "Viernes",
                "Sábado"
            )
            val capitalMonths = arrayOf(
                "ene.",
                "feb.",
                "mar.",
                "abr.",
                "may.",
                "jun.",
                "jul.",
                "ago.",
                "sep.",
                "oct.",
                "nov.",
                "dic."
            )

            val dateFormatSymbols = DateFormatSymbols(currentLocale)
            dateFormatSymbols.shortWeekdays = capitalDays
            dateFormatSymbols.shortMonths = capitalMonths
            simpleDateFormat.dateFormatSymbols = dateFormatSymbols

            val formateado = simpleDateFormat.format(fecha).toString()
            //formateado = formateado.replace("_",":")
            return formateado
        }

        fun formatDate(dateTransaction: String): Date {
            try {
                //2022-07-15T02:07:29.600Z
                val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                val simpleDateFormat = SimpleDateFormat(pattern)
                val date = simpleDateFormat.parse(dateTransaction)

                return date
            } catch (ex: Exception) {
                Log.e("formatDate", ex)
                return Calendar.getInstance().time
            }
        }

        fun formatDateService(dateTransaction: Date): String {
            return try {
                //2022-07-15T02:07:29.600Z
                val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                val simpleDateFormat = SimpleDateFormat(pattern)
                val date = simpleDateFormat.format(dateTransaction).toString()

                date
            } catch (ex: Exception) {
                Log.e("formatDateService", ex)
                ""
            }
        }
    }
}