package example.android.com.RestoPresto


import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_les_menus.*
import android.support.design.widget.TabLayout




class LesMenusActivity : AppCompatActivity(){

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_les_menus)

        toolbar.setTitle(intent.getStringExtra("nom"))
        setSupportActionBar(toolbar)
        // Action Up
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_les_menus, menu)
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

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> MenuNormalFragment()
                1-> MenuDiabetFragment()
                2-> MenuVegetarienFragment()
                else -> MenuNormalFragment()
            }
        }

        override fun getCount(): Int {
            // Show 2s total pages.
            return 3
        }
    }

}