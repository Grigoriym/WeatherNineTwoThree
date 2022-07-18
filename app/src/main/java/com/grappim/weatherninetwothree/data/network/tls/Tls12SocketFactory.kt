package com.grappim.weatherninetwothree.data.network.tls

import android.content.Context
import android.os.Build
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.*

private const val OPEN_WEATHER_CERTIFICATE_FILE_NAME = "openweathermap-org.pem"
private const val GEO_IPIFY_ORG_FILE_NAME = "sni-cloudflaressl-com.pem"
private const val CERTIFICATE_TYPE = "X.509"

/**
 * For the open weather and geo ipify to work correctly on API 21, we have
 * to use the certificates which I took from their web sites in the browser
 */
class Tls12SocketFactory(private val delegate: SSLSocketFactory) : SSLSocketFactory() {
    companion object {

        @JvmStatic
        fun OkHttpClient.Builder.enableTls12Compat(context: Context) = apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                val trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(createKeyStore(createCertificates(context)))
                val trustManagers = trustManagerFactory.trustManagers
                val sslContext = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName)
                sslContext.init(null, trustManagers, null)
                sslSocketFactory(Tls12SocketFactory(sslContext.socketFactory),
                    trustManagers.first { it is X509TrustManager } as X509TrustManager)
            }
        }

        // init trusted CA from assets
        private fun createCertificates(context: Context): Map<String, java.security.cert.Certificate> {
            return mapOf(
                OPEN_WEATHER_CERTIFICATE_FILE_NAME to getCertificate(
                    context,
                    OPEN_WEATHER_CERTIFICATE_FILE_NAME
                ),
                GEO_IPIFY_ORG_FILE_NAME to getCertificate(
                    context,
                    GEO_IPIFY_ORG_FILE_NAME
                )
            )
        }

        private fun getCertificate(
            context: Context,
            certificateName: String
        ): java.security.cert.Certificate {
            val cf: CertificateFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE)
            val caInput: InputStream =
                BufferedInputStream(context.resources.assets.open(certificateName))
            return caInput.use { cf.generateCertificate(it) }
        }

        private fun createKeyStore(certificates: Map<String, java.security.cert.Certificate>): KeyStore {
            val keyStoreType = KeyStore.getDefaultType()
            return KeyStore.getInstance(keyStoreType).apply {
                load(null, null)
                certificates.forEach { (name, certificate) ->
                    setCertificateEntry(name, certificate)
                }
            }
        }
    }

    /**
     * Forcefully adds [TlsVersion.TLS_1_2] as an enabled protocol if called on an [SSLSocket]
     *
     * @return the (potentially modified) [Socket]
     */
    private fun Socket.patchForTls12(): Socket {
        return (this as? SSLSocket)?.apply {
            enabledProtocols += TlsVersion.TLS_1_2.javaName
        } ?: this
    }

    override fun getDefaultCipherSuites(): Array<String> {
        return delegate.defaultCipherSuites
    }

    override fun getSupportedCipherSuites(): Array<String> {
        return delegate.supportedCipherSuites
    }

    @Throws(IOException::class)
    override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket? {
        return delegate.createSocket(s, host, port, autoClose)
            .patchForTls12()
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(host: String, port: Int): Socket? {
        return delegate.createSocket(host, port)
            .patchForTls12()
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(
        host: String,
        port: Int,
        localHost: InetAddress,
        localPort: Int
    ): Socket? {
        return delegate.createSocket(host, port, localHost, localPort)
            .patchForTls12()
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress, port: Int): Socket? {
        return delegate.createSocket(host, port)
            .patchForTls12()
    }

    @Throws(IOException::class)
    override fun createSocket(
        address: InetAddress,
        port: Int,
        localAddress: InetAddress,
        localPort: Int
    ): Socket? {
        return delegate.createSocket(address, port, localAddress, localPort)
            .patchForTls12()
    }

}