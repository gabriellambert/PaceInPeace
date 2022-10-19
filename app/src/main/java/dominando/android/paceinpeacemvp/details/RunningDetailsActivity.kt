package dominando.android.paceinpeacemvp.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dominando.android.paceinpeacemvp.R
import dominando.android.paceinpeacemvp.form.RunningFormFragment
import dominando.android.paceinpeacemvp.model.Running

class RunningDetailsActivity : AppCompatActivity() {
    private val runningId: Long by lazy {
        intent.getLongExtra(EXTRA_RUNNING_ID, -1)
    }

    companion object {
        private const val EXTRA_RUNNING_ID = "running_id"
        fun open(activity: Activity, runningId: Long) {
            activity.startActivityForResult(
                Intent(activity, RunningDetailsActivity::class.java).apply {
                    putExtra(EXTRA_RUNNING_ID, runningId)
                }, 0
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running_details)
        if (savedInstanceState == null) {
            showRunningDetailsFragment()
        }
    }

    private fun showRunningDetailsFragment() {
        val fragment = RunningDetailsFragment.newInstance(runningId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, fragment, RunningDetailsFragment.TAG_DETAILS)
            .commit()
    }
}