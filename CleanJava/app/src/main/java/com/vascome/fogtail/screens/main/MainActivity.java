package com.vascome.fogtail.screens.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.R;
import com.vascome.fogtail.databinding.ActivityMainBinding;
import com.vascome.fogtail.di.screens.main.CollectionComponent;
import com.vascome.fogtail.di.screens.main.CollectionModule;
import com.vascome.fogtail.screens.base.fragments.BaseFragment;
import com.vascome.fogtail.screens.base.other.ViewModifier;
import com.vascome.fogtail.screens.base.views.BaseActivity;
import com.vascome.fogtail.screens.main.fragment.carousel.CarouselAppFragment;
import com.vascome.fogtail.screens.main.fragment.gallery.GalleryAppFragment;
import com.vascome.fogtail.screens.main.fragment.list.ListAppFragment;
import com.vascome.fogtail.screens.main.fragment.stack.StackAppFragment;
import com.vascome.fogtail.screens.main.fragment.table.GridAppFragment;

import javax.inject.Inject;
import javax.inject.Named;

import static com.vascome.fogtail.di.appmodules.DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @Inject
    CollectionContract.Router router;

    private ActivityMainBinding binding;
    private CollectionComponent collectionComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionComponent = FogtailApplication.get(this)
                .appComponent()
                .collectionComponent()
                .collectionModule(new CollectionModule(this))
                .build();
        collectionComponent.inject(this);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(viewModifier.modify(binding.getRoot()));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.mainDrawerLayout, toolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        BaseFragment fragment =
                (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
        if (fragment == null) {
            router.replaceFragment(R.id.main_frame_layout, new ListAppFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        BaseFragment fragment =
                (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);

        if (id == R.id.nav_list) {

            if (!(fragment instanceof ListAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new ListAppFragment());
            }

        } else if (id == R.id.nav_grid) {

            if (!(fragment instanceof GridAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new GridAppFragment());
            }

        } else if (id == R.id.nav_gallery) {

            if (!(fragment instanceof GalleryAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new GalleryAppFragment());
            }

        } else if (id == R.id.nav_stack) {
            if (!(fragment instanceof StackAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new StackAppFragment());
            }

        } else if (id == R.id.nav_carousel) {
            if (!(fragment instanceof CarouselAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new CarouselAppFragment());
            }

        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    public CollectionComponent collectionComponent() {
        return collectionComponent;
    }

}

