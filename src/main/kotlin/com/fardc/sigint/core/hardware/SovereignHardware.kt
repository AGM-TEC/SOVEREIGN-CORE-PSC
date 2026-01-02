package com.fardc.sigint.core.hardware

class SovereignHardware {
    companion object {
        init {
            try { System.loadLibrary("rtlsdr") } catch(e: Throwable) {}
        }
    }
    external fun setFrequency(freq: Long): Int
}
