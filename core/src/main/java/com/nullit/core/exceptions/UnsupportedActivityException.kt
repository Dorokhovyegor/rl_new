package com.nullit.core.exceptions

class UnsupportedActivityException
constructor(message: String) : Exception(message) {
    override val message: String?
        get() = super.message
}
