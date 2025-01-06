package tanvd.konfy.keepass

import org.linguafranca.pwdb.kdbx.KdbxCreds
import org.linguafranca.pwdb.kdbx.jackson.JacksonDatabase
import java.io.File

internal class KeepassClient(databaseFile: File, masterPassword: String) {
    private lateinit var database: JacksonDatabase

    init {
        val credentials = KdbxCreds(masterPassword.toByteArray())
        if (databaseFile.exists()) database =  JacksonDatabase.load(credentials, databaseFile.inputStream())
    }

    fun get(title: String): String? {
        return database.findEntries { it.title == title }.firstOrNull()?.password
    }
}
