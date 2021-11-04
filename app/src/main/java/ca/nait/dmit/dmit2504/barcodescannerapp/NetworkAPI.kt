package ca.nait.dmit.dmit2504.barcodescannerapp;

import android.net.Uri
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

import kotlin.jvm.Throws;

class NetworkAPI {

    @Throws(IOException::class)
    suspend fun fetchUrlResponseBytes(urlString: String): ByteArray? {
        // Creates a URL object from the String representation.
        val remoteUrl = URL(urlString)
        // Returns a URLConnection instance that represents a connection to the remote object referred to by the URL.
        val remoteConnection = remoteUrl.openConnection() as HttpURLConnection
        try {
            // Set the request method
            remoteConnection.requestMethod = "GET"
            // Optional: Set the Request content-type header parameter
//            connection.setRequestProperty("Content-Type","text/plain, text/html");
            // Optional: Set the Response format type
//            connection.setRequestProperty("Accept","text/plain, text/html");
            //  Read the response from the input stream
            val inputStream = remoteConnection.inputStream
            val responseCode = remoteConnection.responseCode
            // Throw an IOException if the response code is not 200
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("Error accessing $urlString with response ${remoteConnection.responseMessage} ")
            }
            // Create a new byte array output stream for storing data from the input stream
            val outputStream = ByteArrayOutputStream()
            var bytesRead = 0
            val BUFFER_SIZE = 1024
            val buffer = ByteArray(BUFFER_SIZE)
            while ((inputStream.read(buffer).also { bytesRead = it }) > 0) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.close()
            return outputStream.toByteArray()
        } finally {
            remoteConnection.disconnect()
        }
    }


    @Throws(IOException::class)
    suspend fun fetchUrlResponseString(urlString: String): String {
        var responseString = ""
        val responseBytes = fetchUrlResponseBytes(urlString)
        if (responseBytes != null) {
            responseString = String(responseBytes)
        }
        return responseString
    }

    @Throws(IOException::class)
    suspend fun postFormData(urlString: String, formData: Map<String, String>) {
        // Creates a URL object from the String representation.
        val remoteUrl = URL(urlString)
        // Returns a URLConnection instance that represents a connection to the remote object referred to by the URL.
        val connection = remoteUrl.openConnection() as HttpURLConnection
        try {
            // Set the request method
            connection.requestMethod = "POST"
            // Set the Request content-type header parameter
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            // Optional: Set the Response format type
//            connection.setRequestProperty("Accept","text/plain");
            // Enable the connection to send output
            connection.doOutput = true
            // Create the request body
            // Convert the key-value pairs from the formData map to a query string in the format "paramName1=param1Value&paramName2=param2Value"
            val requestBodyBuilder = StringBuilder()
            for ((key, value) in formData) {
                if (requestBodyBuilder.length > 0) {
                    requestBodyBuilder.append("&")
                }
                requestBodyBuilder.append(
                    String.format("%s=%s", key, Uri.encode(value, "utf-8"))
                )
            }
            val requestBody = requestBodyBuilder.toString() 
            // For best performance, you should call either setFixedLengthStreamingMode(int) when the body length is known in advance,
            // or setChunkedStreamingMode(int) when it is not
            connection.setFixedLengthStreamingMode(requestBody.length)
            val out: OutputStream = BufferedOutputStream(connection.outputStream)
            out.write(requestBody.toByteArray(StandardCharsets.UTF_8))
            out.close()
            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_CREATED && responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("Error accessing $urlString with response ${connection.responseMessage}")
            }
        } finally {
            connection.disconnect()
        }
    }
}