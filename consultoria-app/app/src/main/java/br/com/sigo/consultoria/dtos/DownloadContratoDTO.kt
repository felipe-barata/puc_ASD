package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class DownloadContratoDTO(var arquivo: ByteArray) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DownloadContratoDTO

        if (!arquivo.contentEquals(other.arquivo)) return false

        return true
    }

    override fun hashCode(): Int {
        return arquivo.contentHashCode()
    }
}