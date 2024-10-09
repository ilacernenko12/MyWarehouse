package by.chernenko.mywarehouse.scanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ScannerReceiver(private val scanListener: ScanListener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return

        val log = "Action: ${intent.action}\nURI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}"
        Log.d("SCANNING_RECEIVED", log)

        var scannedString: String? = intent.getStringExtra("barcode_string")

        // Для datawedge
        if (scannedString == null) {
            scannedString = intent.getStringExtra("com.symbol.datawedge.data_string")
        }
        // Для сканера Honeywell ck65
        if (scannedString == null) {
            scannedString = intent.getStringExtra("data")
        }
        // Некоторые сканеры спамят пустой строкой, проверяем на null
        if (scannedString.isNullOrEmpty()) {
            return
        }

        // Преобразуем неясный символ в октоторп
        scannedString = scannedString.replace('\u001D', '#')

        // Передаем отсканированную строку через интерфейс
        scanListener.onScanned(scannedString)
    }
}