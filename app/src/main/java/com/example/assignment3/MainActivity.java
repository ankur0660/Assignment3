package com.example.assignment3;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.assignment3.Adapter.CustomViewPageAdapter;
import com.example.assignment3.fragment.BaseFragment;
import com.example.assignment3.fragment.ShowStudentViewModel;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ShowStudentViewModel mViewModel;

    private void initViews() {

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        mViewModel=ViewModelProviders.of(this).get(ShowStudentViewModel.class);
        mViewModel.setViewpager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
//               mViewModel.mode=Constants.ACTIVITY_NEW_MODE;
                BaseFragment frg = (BaseFragment) getSupportFragmentManager().getFragments().get(i);
                frg.refresh();


            }


            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewPager.setAdapter(new CustomViewPageAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.actions_menu, menu);
//        getSupportActionBar().setTitle("Students");
//
//
//        final MenuItem switchItem = menu.findItem(R.id.switcher);
//        switchItem.setActionView(R.layout.switch_layout);
//        MenuItem spinnerItem = menu.findItem(R.id.spinnerMenu);
//        spinnerItem.setActionView(R.layout.spinn);
//
//
//        Switch swtch = switchItem.getActionView().findViewById(R.id.switchLayout);
//        Spinner spinner = spinnerItem.getActionView().findViewById(R.id.spinnerLayout);
//
//        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
//
//
//                } else
//                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//            }
//
//        });
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case SORT_BY_NAME:
//
//                        Collections.sort(student, new Comparator<Student>() {
//                            @Override
//                            public int compare(Student o1, Student o2) {
//                                return o1.getName().compareToIgnoreCase(o2.getName());
//
//                            }
//                        });
//                        studentAdapter.notifyDataSetChanged();
//                        break;
//                    case SORT_BY_ROLL:
//                        Collections.sort(student, new Comparator<Student>() {
//                            @Override
//                            public int compare(Student o1, Student o2) {
//                                return o1.getRoll().compareTo(o2.getRoll());
//
//                            }
//                        });
//
//                        studentAdapter.notifyDataSetChanged();
//                        break;
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//
//    }


    public static void debug(String msg) {
        Log.d("aaaaaaaaa", msg);
    }

}
