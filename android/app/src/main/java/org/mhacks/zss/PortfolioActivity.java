package org.mhacks.zss;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mhacks.zss.model.Portfolio;

public class PortfolioActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private Portfolio mPortfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mPortfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setCurrentItem(mPager.getCurrentItem());
        mPager.setAdapter(mPagerAdapter);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ((TextView) findViewById(R.id.toolbar_title)).setText("Funds Allocation");
                } else if (position == 1) {
                    ((TextView) findViewById(R.id.toolbar_title)).setText("Recommended Portfolio");
                } else {
                    ((TextView) findViewById(R.id.toolbar_title)).setText("Customize Portfolio");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ((TextView) findViewById(R.id.toolbar_title)).setText("Funds Allocation");

        findViewById(R.id.prev_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                if (mPager.getCurrentItem() == 0) {
                    findViewById(R.id.prev_button).setEnabled(false);
                } else {
                    findViewById(R.id.prev_button).setEnabled(true);
                }
                if (mPager.getCurrentItem() == 2) {
                    findViewById(R.id.next_button).setEnabled(false);
                } else {
                    findViewById(R.id.next_button).setEnabled(true);
                }
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                if (mPager.getCurrentItem() == 2) {
                    findViewById(R.id.next_button).setEnabled(false);
                } else {
                    findViewById(R.id.next_button).setEnabled(true);
                }
                if (mPager.getCurrentItem() == 0) {
                    findViewById(R.id.prev_button).setEnabled(false);
                } else {
                    findViewById(R.id.prev_button).setEnabled(true);
                }
            }
        });

        findViewById(R.id.prev_button).setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position == 0) {
                fragment = new FundsAllocationPageFragment();
            } else if (position == 1) {
                fragment = new PortfolioPageFragment();
            } else {
                fragment = new CustomizationPageFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("portfolio", mPortfolio);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void signout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
