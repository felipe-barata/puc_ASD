package br.com.sigo.consultoria.util

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class LoadingHelper {

    private lateinit var btnLoadingOk: Button
    private lateinit var loadingContainer: View
    private lateinit var containerLayout: View
    private lateinit var tvLoadingStatus: TextView
    private lateinit var progressBar: ProgressBar

    fun initializeLoading(status: String?) {
        setStatus(status)
        init()
    }

    fun initializeLoading(status: Int) {
        setStatus(status)
        init()
    }

    fun stopLoading(
        message: String?,
        onClickListener: View.OnClickListener?,
        tag: Any?
    ) {
        setStatus(message)
        stop(onClickListener, tag)
    }

    fun stopLoading(
        message: Int,
        onClickListener: View.OnClickListener?,
        tag: Any?
    ) {
        setStatus(message)
        stop(onClickListener, tag)
    }

    fun finish() {
        containerLayout.visibility = View.VISIBLE
        loadingContainer.visibility = View.GONE
        btnLoadingOk.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun setStatus(stringId: Int) {
        tvLoadingStatus.setText(stringId)
    }

    fun setStatus(text: String?) {
        tvLoadingStatus.text = text
    }

    private fun init() {
        containerLayout.visibility = View.GONE
        loadingContainer.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        btnLoadingOk.visibility = View.GONE
    }

    private fun stop(
        onClickListener: View.OnClickListener?,
        tag: Any?
    ) {
        if (onClickListener != null) {
            btnLoadingOk.setOnClickListener(onClickListener)
        }
        if (tag != null) {
            btnLoadingOk.tag = tag
        }
        btnLoadingOk.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    companion object {

        fun initialize(
            activity: Activity,
            btId: Int,
            layoutId: Int,
            tvId: Int,
            containerId: Int,
            progressId: Int
        ): LoadingHelper {
            val ld = LoadingHelper();
            ld.loadingContainer = activity.findViewById<View>(layoutId)
            ld.btnLoadingOk = activity.findViewById<Button>(btId)
            ld.tvLoadingStatus = activity.findViewById<TextView>(tvId)
            ld.containerLayout = activity.findViewById<View>(containerId)
            ld.progressBar = activity.findViewById<ProgressBar>(progressId)
            ld.loadingContainer.setVisibility(View.GONE)
            return ld;
        }

    }
}