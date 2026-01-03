package com.fardc.sigint.services.comint
class ComintDecoder {
    fun decodeMetadata(raw: ByteArray): String = "ID:" + raw.joinToString("") { "%02x".format(it) }
}
