package com.rmd.media.bookstore.presentation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.rmd.media.bookstore.R
import com.rmd.media.bookstore.databinding.ActivityMainBinding
import com.rmd.media.bookstore.domain.Document
import com.rmd.media.bookstore.presentation.library.LibraryFragment
import com.rmd.media.bookstore.presentation.reader.ReaderFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainActivityDelegate {

    private lateinit var binding : ActivityMainBinding


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)
    setContentView(R.layout.activity_main)
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
        this, binding.drawerLayout, toolbar,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)
      binding.drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    binding.navView.setNavigationItemSelectedListener(this)

    if(savedInstanceState == null) {
      binding.navView.menu.findItem(R.id.nav_library).isChecked = true
      binding.navView.menu.performIdentifierAction(R.id.nav_library, 0)
    }
  }


  override fun onBackPressed() {
    val drawerLayout: DrawerLayout = findViewById(
        R.id.drawer_layout)
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
      R.id.nav_library -> supportFragmentManager.beginTransaction()
          .replace(R.id.content, LibraryFragment.newInstance())
          .commit()
      R.id.nav_reader -> openDocument(
          Document.EMPTY)
    }
    val drawerLayout: DrawerLayout = findViewById(
        R.id.drawer_layout)
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }

  override fun openDocument(document: Document) {
    binding.navView.menu.findItem(R.id.nav_reader).isChecked = true
    supportFragmentManager.beginTransaction()
        .replace(R.id.content, ReaderFragment.newInstance(document))
        .commit()
  }
}
