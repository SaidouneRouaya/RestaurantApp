package example.android.com.RestoPresto

import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_formule_binaire.*
import org.jetbrains.anko.intentFor

class FormuleBinaireActivity : AppCompatActivity() {
    private val listFragment = listOf(EntreePlatFragment(),PlatDessertFragment())
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formule_binaire)

        setSupportActionBar(toolbar)
        //Action Up
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        val nom = intent.getStringExtra("nom")
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        fab.setOnClickListener { view ->
            startActivity(intentFor<ReserverActivity>("nom" to nom))
        }
        val formuleModel = FormuleModel()
        formuleModel.remplirFormules()
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(Tab))
        Tab.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_formule_binaire, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return listFragment.get(position)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }
    }

}
