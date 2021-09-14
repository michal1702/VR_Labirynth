package pl.polsl.vr_labirynth.entities

class SaveEntity(
    val offsetX: Double = 0.0,
    val offsetZ: Double = 0.0,
    val positionX: Double = 0.0,
    val positionY: Double = 0.0,
    val positionZ: Double = 0.0,
    val points: Int = 0,
    val hearts: Int = 0,
    val map: IntArray? = intArrayOf(0)
) {
}