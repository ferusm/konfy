package tanvd.konfy.k8s

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.apis.CoreV1Api
import tanvd.konfy.conversion.ConversionService
import tanvd.konfy.provider.ConfigProvider
import java.lang.reflect.Type
import java.util.Base64

/**
 * Provider takes values from K8S Secrets.
 * Each key will be extracted from [secretName] secret
 * @param namespace K8S namespace
 * @param secretName Secret name
 */
class K8sSecretsProvider(
    private val namespace: String = "default",
    private val secretName: String,
    private val k8sClient: ApiClient = K8sSecretsClient.defaultClient,
    private val convert: (String, Type) -> Any? = ConversionService::convert) : ConfigProvider()
{
    private val api by lazy { CoreV1Api(k8sClient) }

    private val base64Decoder = Base64.getDecoder()

    @Suppress("UNCHECKED_CAST")
    override fun <N : Any> fetch(key: String, type: Type): N? {
        val secretValue = api.readNamespacedSecret(secretName, namespace, null)
        val stringData = secretValue?.stringData?.get(key)
        val secretData by lazy { secretValue?.data?.get(key)?.let { base64Decoder.decode(it) }?.decodeToString() }
        return (stringData ?: secretData)?.let { convert(it, type) as N }
    }
}
