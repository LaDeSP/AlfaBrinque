package br.ufms.ladesp.alfabrinque.swipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import br.ufms.ladesp.alfabrinque.R;
import br.ufms.ladesp.alfabrinque.util.UtilitarioUI;

public class EscolherCategoria extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private ImageButton leftNav;
    private ImageButton rightNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_categoria);
        UtilitarioUI.hideSystemUI(getWindow());

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        leftNav = (ImageButton) findViewById(R.id.left_nav);
        rightNav = (ImageButton) findViewById(R.id.right_nav);

        leftNav.setOnClickListener(this);
        rightNav.setOnClickListener(this);

        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        String[] tabs = getResources().getStringArray(R.array.categorias);

        AlimentoFragment alimentoFragment = AlimentoFragment.newInstance();
        AnimaisFragment animaisFragment = AnimaisFragment.newInstance();
        ObjetosFragments objetosFragments = ObjetosFragments.newInstance();


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(animaisFragment, tabs[0]);
        adapter.addFragment(alimentoFragment, tabs[1]);
        adapter.addFragment(objetosFragments, tabs[2]);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.right_nav) {
            int tab = mViewPager.getCurrentItem();
            tab++;
            mViewPager.setCurrentItem(tab);

        } else if (v.getId() == R.id.left_nav) {
            int tab = mViewPager.getCurrentItem();
            if (tab > 0) {
                tab--;
                mViewPager.setCurrentItem(tab);
            } else if (tab == 0) {
                mViewPager.setCurrentItem(tab);
            }
        }
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
