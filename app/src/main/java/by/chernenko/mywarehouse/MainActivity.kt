package by.chernenko.mywarehouse

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.chernenko.mywarehouse.databinding.ActivityMainBinding
import by.chernenko.mywarehouse.scanner.ScanListener
import by.chernenko.mywarehouse.scanner.ScannerReceiver

class MainActivity : AppCompatActivity(), ScanListener {

    private lateinit var scanningReceiver: ScannerReceiver
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        scanningReceiver = ScannerReceiver(this)
    }

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter().apply {
            addAction("android.intent.ACTION_DECODE_DATA")
        }
        registerReceiver(scanningReceiver, filter)
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(scanningReceiver)
    }

    override fun onScanned(scannedString: String) {
        binding.vTvScannedString.text = scannedString
    }
}