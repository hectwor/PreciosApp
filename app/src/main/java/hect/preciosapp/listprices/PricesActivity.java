package hect.preciosapp.listprices;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hect.preciosapp.models.Product;
import hect.preciosapp.R;


public class PricesActivity extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private RecyclerView recyclerView;
        private ListProductAdapter listproducts;
        private ArrayList<Product> products;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_prices, container, false);

            recyclerView = rootView.findViewById(R.id.recyclerView);

            recyclerView.setLayoutManager(new GridLayoutManager(inflater.getContext(), 3));
            products = new ArrayList<>();

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            listproducts = new ListProductAdapter(products, rootView.getContext());
            recyclerView.setAdapter(listproducts);

            TextView textView1 = rootView.findViewById(R.id.titleCategory);

            if (getArguments().getInt(ARG_SECTION_NUMBER)==1){
                textView1.setText(R.string.category_1);

                database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.removeAll(products);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product.getCategory().equals("Bebidas"))
                                products.add(product);
                        }
                        listproducts.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            if (getArguments().getInt(ARG_SECTION_NUMBER)==2){
                textView1.setText(R.string.category_2);
                database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.removeAll(products);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product.getCategory().equals("Snacks"))
                                products.add(product);
                        }
                        listproducts.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            if (getArguments().getInt(ARG_SECTION_NUMBER)==3){
                textView1.setText(R.string.category_3);
                database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.removeAll(products);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product.getCategory().equals("Embutidos"))
                                products.add(product);
                        }
                        listproducts.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            if (getArguments().getInt(ARG_SECTION_NUMBER)==4){
                textView1.setText(R.string.category_4);
                database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.removeAll(products);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product.getCategory().equals("Galletas y Dulces"))
                                products.add(product);
                        }
                        listproducts.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            if (getArguments().getInt(ARG_SECTION_NUMBER)==5){
                textView1.setText(R.string.category_5);
                database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.removeAll(products);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product.getCategory().equals("Útiles Escolares"))
                                products.add(product);
                        }
                        listproducts.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            if (getArguments().getInt(ARG_SECTION_NUMBER)==6){
                textView1.setText(R.string.category_6);
                database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.removeAll(products);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product.getCategory().equals("Uso personal"))
                                products.add(product);
                        }
                        listproducts.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            if (getArguments().getInt(ARG_SECTION_NUMBER)==7){
                textView1.setText(R.string.category_7);
                database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.removeAll(products);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product.getCategory().equals("Consumo personal"))
                                products.add(product);
                        }
                        listproducts.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }
    }
}
