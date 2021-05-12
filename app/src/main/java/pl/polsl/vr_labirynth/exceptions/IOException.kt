package pl.polsl.vr_labirynth.exceptions

import java.lang.Exception

class IOException : Exception {

    constructor(message: String?){}

    constructor(message: String?, cause: Throwable?){}
}