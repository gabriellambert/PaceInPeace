package dominando.android.paceinpeacemvp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dominando.android.paceinpeacemvp.databinding.ActivityMainBinding
import dominando.android.paceinpeacemvp.details.RunningDetailsActivity
import dominando.android.paceinpeacemvp.details.RunningDetailsFragment
import dominando.android.paceinpeacemvp.dialog.AboutDialogFragment
import dominando.android.paceinpeacemvp.form.RunningFormFragment
import dominando.android.paceinpeacemvp.list.RunningListFragment
import dominando.android.paceinpeacemvp.list.RunningListViewModel
import dominando.android.paceinpeacemvp.model.Running
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),
    RunningListFragment.OnRunningClickListener,
    SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    private val viewModel: RunningListViewModel by viewModel()

    private var searchView: SearchView? = null

    private lateinit var binding: ActivityMainBinding

    private val listFragment: RunningListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as RunningListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_training -> {
                    showFragment(TrainingFragment.newInstance())
                }
                R.id.action_running -> {
                    true
                }
                R.id.action_account -> {
                    showFragment(AccountFragment.newInstance())
                }
                else -> false
            }
        }

        binding.fabAdd.setOnClickListener {
            listFragment.hideDeleteMode()
            RunningFormFragment.newInstance().open(supportFragmentManager)
        }
    }

    override fun onRunningClick(running: Running) {
        showDetailsActivity(running.id)
    }

    private fun showDetailsActivity(runningId: Long) {
        RunningDetailsActivity.open(this, runningId)
    }

    //carrega as ações que aparecerão na action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.running, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)
        if (viewModel.getSearchTerm()?.value?.isNotEmpty() == true) {
            Handler().post {
                val query = viewModel.getSearchTerm()?.value
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    //onde cada ação da action bar é tratada
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info ->
                AboutDialogFragment().show(supportFragmentManager, "sobre")
        }
        return super.onOptionsItemSelected(item)
    }

    //é chamado quando o botão de busca do teclado é pressionado
    override fun onQueryTextSubmit(query: String?) = true

    //é disparado a cada caractere digitado na caixa de texto
    override fun onQueryTextChange(newText: String?): Boolean {
        listFragment.search(newText ?: "")
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem?) = true

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        listFragment.search()
        return true
    }

    private fun showFragment(fragment: Fragment): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        binding.container.visibility = View.VISIBLE
        binding.fragmentList.visibility = View.GONE
        transaction.replace(R.id.container, fragment).show(fragment).commit()
        return true
    }
}