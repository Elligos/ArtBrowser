package com.example.deviantartviewer.utils.network

import android.util.Log
import com.example.deviantartviewer.utils.log.Logger
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory


object NetworkHasInternet {

    const val TAG = "NetworkHasInternet"

    // Make sure to execute this on a background thread.
    fun execute(socketFactory: SocketFactory): Boolean {
        return try{
            Logger.d(TAG, "PINGING google.")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Logger.d(TAG, "PING success.")
            true
        }catch (e: IOException){
            Logger.e(TAG, "No internet connection. $e")
            false
        }
    }
}