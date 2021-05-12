package pl.polsl.vr_labirynth.utils

import pl.polsl.vr_labirynth.exceptions.IOException
import java.io.File

class IOUtil {
    companion object {
        /**
         * Reads JSON from file
         * @param fileLocation location of a file
         * @throws IOException
         */
        fun readJsonFromFile(fileLocation: String): String {
            return if (this.ifExists(fileLocation)) {
                File(fileLocation).readText()
            } else throw IOException("File does not exist")
        }

        /**
         * Writes JSON to file
         * @param fileLocation location of a file
         * @param contents JSON as a String
         */
        fun writeJsonToFile(fileLocation: String, contents: String) {
            File(fileLocation).writeText(contents)
        }

        /**
         * Checks if file exists
         * @param fileLocation location of a file
         * @return if file exists
         */
        fun ifExists(fileLocation: String): Boolean {
            val file = File(fileLocation)
            return file.exists()
        }
    }
}