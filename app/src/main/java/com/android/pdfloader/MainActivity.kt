package com.android.pdfloader

import android.annotation.SuppressLint
import android.app.AppComponentFactory
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import com.android.pdfloader.ui.theme.PdfloaderTheme
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import com.rajat.pdfviewer.util.saveTo


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            PdfloaderTheme {


                     val screen : String
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colors.background
                ) {
                   Button(onClick = { context.startActivity(Intent(context,SecondActivity::class.java)) }) {
                       Text(text = "open pdf")
                   }
                    }
                }
            }
        }
    }


@Composable
fun MyPdfScreenFromUrl(url: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val progressbar = CircularProgressIndicator()
    PdfRendererViewCompose(
        url = url,
        lifecycleOwner = lifecycleOwner,
        statusCallBack = object : PdfRendererView.StatusCallBack {
            override fun onPdfLoadStart() {
                Log.i("statusCallBack","onPdfLoadStart")
            }

            override fun onPdfLoadProgress(
                progress: Int,
                downloadedBytes: Long,
                totalBytes: Long?
            ) {
                //Download is in progress
                return progressbar
            }

            override fun onPdfLoadSuccess(absolutePath: String) {
                Log.i("statusCallBack","onPdfLoadSuccess")
            }

            override fun onError(error: Throwable) {
                Log.i("statusCallBack","onError")
            }

            override fun onPageChanged(currentPage: Int, totalPage: Int) {
                //Page change. Not require
            }
        }

    )
}

class SecondActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        val url: String = "https://icseindia.org/document/sample.pdf"

        val url1: String =
            "https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf"
        super.onCreate(savedInstanceState)
        setContent {
            PdfloaderTheme() {
                Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background) {


                    val context = LocalContext.current
                       MyPdfScreenFromUrl(url = url1)
                  /*  PdfViewerActivity.launchPdfFromUrl(
                        pdfUrl = url1,
                        context = context,
                        pdfTitle = "pdf",
                        saveTo = saveTo.ASK_EVERYTIME*/
                  //  )
                }


            }
        }
    }
}