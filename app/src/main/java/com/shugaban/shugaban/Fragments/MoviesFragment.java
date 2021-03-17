package com.shugaban.shugaban.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shugaban.shugaban.Activities.MovieDetailActivity;
import com.shugaban.shugaban.DataGenerators.CategoriesListDataGenerator;
import com.shugaban.shugaban.DataGenerators.NewonshugabanListDataGenerator;
import com.shugaban.shugaban.DataGenerators.RecommendedListDataGenerator;
import com.shugaban.shugaban.Inc.Util;
import com.shugaban.shugaban.Models.CategoryModel;
import com.shugaban.shugaban.Models.MovieModel;
import com.shugaban.shugaban.R;

import org.w3c.dom.Text;

public class MoviesFragment extends Fragment  implements View.OnClickListener {

    private TextView m_recommended_textview, m_newonshugaban_textview, m_categories_textview;
    private RecyclerView m_recommended_list_recyclerview, m_newonshugaban_list_recyclerview, m_categories_list_recyclerview;
    private LinearLayoutManager m_recommended_list_linearlayoutmanager, m_newonshugaban_list_linearlayoutmanager, m_categories_list_linearlayoutmanager;
    private ImageView m_reload_imageview;
    private ProgressBar m_loading_progressbar;
    private Thread network_thread = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        m_loading_progressbar = view.findViewById(R.id.fragment_movies_contentloadingprogressbar);
        m_reload_imageview = view.findViewById(R.id.fragment_movies_reload_imageview);
        m_recommended_textview = view.findViewById(R.id.fragment_movies_recommended_for_you_textview);
        m_newonshugaban_textview = view.findViewById(R.id.fragment_movies_new_releases);
        m_categories_textview = view.findViewById(R.id.fragment_movies_cats_textview);
        m_recommended_list_recyclerview = view.findViewById(R.id.fragment_movies_recommended_for_you_list_recyclerview);
        m_newonshugaban_list_recyclerview = view.findViewById(R.id.fragment_movies_new_releases_list_recyclerview);
        m_categories_list_recyclerview = view.findViewById(R.id.fragment_movies_cats_list_recyclerview);


        m_recommended_list_linearlayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        m_recommended_list_linearlayoutmanager.setOrientation(RecyclerView.HORIZONTAL);
        m_newonshugaban_list_linearlayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        m_newonshugaban_list_linearlayoutmanager.setOrientation(RecyclerView.HORIZONTAL);
        m_categories_list_linearlayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        m_categories_list_linearlayoutmanager.setOrientation(RecyclerView.HORIZONTAL);

        m_recommended_list_recyclerview.setItemViewCacheSize(20);
        m_recommended_list_recyclerview.setDrawingCacheEnabled(true);
        m_recommended_list_recyclerview.setHasFixedSize(true);
        m_recommended_list_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        m_recommended_list_recyclerview.setLayoutManager(m_recommended_list_linearlayoutmanager);
        m_recommended_list_recyclerview.setAdapter(new RecommendedListRecyclerViewAdapter());

        m_newonshugaban_list_recyclerview.setItemViewCacheSize(20);
        m_newonshugaban_list_recyclerview.setDrawingCacheEnabled(true);
        m_newonshugaban_list_recyclerview.setHasFixedSize(true);
        m_newonshugaban_list_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        m_newonshugaban_list_recyclerview.setLayoutManager(m_newonshugaban_list_linearlayoutmanager);
        m_newonshugaban_list_recyclerview.setAdapter(new NewonshugabanListRecyclerViewAdapter());

        m_categories_list_recyclerview.setItemViewCacheSize(20);
        m_categories_list_recyclerview.setDrawingCacheEnabled(true);
        m_categories_list_recyclerview.setHasFixedSize(true);
        m_categories_list_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        m_categories_list_recyclerview.setLayoutManager(m_categories_list_linearlayoutmanager);
        m_categories_list_recyclerview.setAdapter(new CategoriesListRecyclerViewAdapter());


        network_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                populate_movies();
                populate_cats();
                //call_api("Bearer " + getSharedPreferenceString(getActivity().getApplicationContext(), SHARED_PREF_KEY_USER_TOKEN));
            }
        });
        network_thread.start();
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == m_reload_imageview.getId()) {
            populate_movies();
            /*
            network_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    call_api("Bearer " + getSharedPreferenceString(getActivity().getApplicationContext(), SHARED_PREF_KEY_USER_TOKEN));
                }
            });
            network_thread.start();
            */
        }
    }


    private void allOnClickHandlers(View v, int position) {
        if (v.getId() == R.id.holder_parent || v.getId() == R.id.movie_poster) {
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_NAME, String.valueOf(RecommendedListDataGenerator.getAllData().get(position).getMovie_name()));
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_YEAR, RecommendedListDataGenerator.getAllData().get(position).getMovie_year());
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_CATEGORY, RecommendedListDataGenerator.getAllData().get(position).getMovie_category());
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_DURATION, RecommendedListDataGenerator.getAllData().get(position).getMovie_duration());
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_DESCRIPTION, RecommendedListDataGenerator.getAllData().get(position).getMovie_description());
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_PLOT, RecommendedListDataGenerator.getAllData().get(position).getMovie_plot());
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_PRICE_NAIRA, RecommendedListDataGenerator.getAllData().get(position).getMovie_amount_naira());
            Util.setSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_MOVIE_PRICE_DOLLAR, RecommendedListDataGenerator.getAllData().get(position).getMovie_amount_dollar());
            Intent intent = new Intent(getActivity().getApplicationContext(), MovieDetailActivity.class);
            startActivity(intent);
        }
    }


    private class RecommendedListRecyclerViewAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {
            if (RecommendedListDataGenerator.getAllData().get(position).getRowId() == 0) {
                return 0;
            }
            return 1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_pposter_portrait, parent, false);
            vh = new MoviePortraitViewHolder(v);

            return vh;
        }


        public class MoviePortraitViewHolder extends RecyclerView.ViewHolder {
            private ConstraintLayout m_parent_holder_constraintlayout;
            private ImageView m_movie_poster;

            private View.OnClickListener innerClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allOnClickHandlers(v, getAdapterPosition());
                }
            };

            public MoviePortraitViewHolder(View v) {
                super(v);
                m_parent_holder_constraintlayout = v.findViewById(R.id.holder_parent);
                m_movie_poster = v.findViewById(R.id.movie_poster);

                m_parent_holder_constraintlayout.setOnClickListener(innerClickListener);
                m_movie_poster.setOnClickListener(innerClickListener);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

            if (
                    !getActivity().isFinishing()
                            && !RecommendedListDataGenerator.getAllData().get(position).getMovie_portrait().equalsIgnoreCase("")
            ) {

                //Util.loadImageView(getActivity().getApplicationContext(), RecommendedListDataGenerator.getAllData().get(position).getMovie_portrait().trim(), ((MoviePortraitViewHolder) holder).m_movie_poster, null);
                if (position == 0) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait1);
                } else if (position == 1) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait2);
                } else if (position == 2) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait3);
                } else if (position == 3) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait4);
                } else if (position == 4) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait5);
                } else if (position == 5) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait6);
                } else if (position == 6) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait7);
                } else if (position == 7) {
                    ((RecommendedListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait8);
                }
                Util.show_log_in_console("RECOMEMNRECY", "REACHED HERE");
            } else {
                ((MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.bg);
            }
        }

        @Override
        public int getItemCount() {
            return RecommendedListDataGenerator.getAllData().size();
        }

    }


    private class NewonshugabanListRecyclerViewAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {
            if (NewonshugabanListDataGenerator.getAllData().get(position).getRowId() == 0) {
                return 0;
            }
            return 1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_pposter_portrait, parent, false);
            vh = new MoviePortraitViewHolder(v);

            return vh;
        }


        public class MoviePortraitViewHolder extends RecyclerView.ViewHolder {
            private ConstraintLayout m_parent_holder_constraintlayout;
            private ImageView m_movie_poster;

            private View.OnClickListener innerClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allOnClickHandlers(v, getAdapterPosition());
                }
            };

            public MoviePortraitViewHolder(View v) {
                super(v);
                m_parent_holder_constraintlayout = v.findViewById(R.id.holder_parent);
                m_movie_poster = v.findViewById(R.id.movie_poster);

                m_parent_holder_constraintlayout.setOnClickListener(innerClickListener);
                m_movie_poster.setOnClickListener(innerClickListener);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

            if (
                    !getActivity().isFinishing()
                            && !NewonshugabanListDataGenerator.getAllData().get(position).getMovie_portrait().equalsIgnoreCase("")
            ) {
                //Util.loadImageView(getActivity().getApplicationContext(), NewonshugabanListDataGenerator.getAllData().get(position).getMovie_portrait().trim(), ((MoviePortraitViewHolder) holder).m_movie_poster, null);
                if (position == 0) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait1);
                } else if (position == 1) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait2);
                } else if (position == 2) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait3);
                } else if (position == 3) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait4);
                } else if (position == 4) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait5);
                } else if (position == 5) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait6);
                } else if (position == 6) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait7);
                } else if (position == 7) {
                    ((NewonshugabanListRecyclerViewAdapter.MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.portrait8);
                }
            } else {
                ((MoviePortraitViewHolder) holder).m_movie_poster.setImageResource(R.drawable.bg);
            }
        }

        @Override
        public int getItemCount() {
            return NewonshugabanListDataGenerator.getAllData().size();
        }

    }


    private class CategoriesListRecyclerViewAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {
            if (CategoriesListDataGenerator.getAllData().get(position).getRowId() == 0) {
                return 0;
            }
            return 1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
            vh = new CategoryViewHolder(v);

            return vh;
        }


        public class CategoryViewHolder extends RecyclerView.ViewHolder {
            private ConstraintLayout m_parent_holder_constraintlayout;
            private TextView m_movie_category;

            private View.OnClickListener innerClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allOnClickHandlers(v, getAdapterPosition());
                }
            };

            public CategoryViewHolder(View v) {
                super(v);
                m_parent_holder_constraintlayout = v.findViewById(R.id.holder_parent);
                m_movie_category = v.findViewById(R.id.movie_category);

                m_parent_holder_constraintlayout.setOnClickListener(innerClickListener);
                m_movie_category.setOnClickListener(innerClickListener);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

            if (
                    !getActivity().isFinishing()
                            && !CategoriesListDataGenerator.getAllData().get(position).getCategory_name().equalsIgnoreCase("")
            ) {
                //Util.loadImageView(getActivity().getApplicationContext(), NewonshugabanListDataGenerator.getAllData().get(position).getMovie_portrait().trim(), ((MoviePortraitViewHolder) holder).m_movie_poster, null);
                ((CategoryViewHolder) holder).m_movie_category.setText(CategoriesListDataGenerator.getAllData().get(position).getCategory_name());
            } else {
                ((CategoryViewHolder) holder).m_movie_category.setText("CATEGORY");
            }
        }

        @Override
        public int getItemCount() {
            return CategoriesListDataGenerator.getAllData().size();
        }

    }

    private void populate_movies() {

        for (int i = 0; i < 8; i++) {
            MovieModel mine1 = new MovieModel();

            if (i == 0) {
                mine1.setMovie_id(1);
                mine1.setMovie_name("The Unchained");
                mine1.setMovie_year("2020");
                mine1.setMovie_plot("Knowing the best things in life are free. ");
                mine1.setMovie_duration("1hr 20mins");
                mine1.setMovie_portrait("portrait1");
                mine1.setMovie_landscape("landscape1");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }

            if (i == 1) {
                mine1.setMovie_id(2);
                mine1.setMovie_name("Flawless");
                mine1.setMovie_year("2021");
                mine1.setMovie_plot("Feeling the power there in, Mrs Masai made her move. ");
                mine1.setMovie_duration("2hr 10mins");
                mine1.setMovie_portrait("portrait2");
                mine1.setMovie_landscape("landscape2");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }

            if (i == 2) {
                mine1.setMovie_id(3);
                mine1.setMovie_name("First King");
                mine1.setMovie_year("2018");
                mine1.setMovie_plot("Feeling the power there in, Mrs Masai made her move. ");
                mine1.setMovie_duration("2hr 10mins");
                mine1.setMovie_portrait("portrait3");
                mine1.setMovie_landscape("landscape2");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }

            if (i == 3) {
                mine1.setMovie_id(4);
                mine1.setMovie_name("Hells Heaven");
                mine1.setMovie_year("2017");
                mine1.setMovie_plot("Feeling the power there in, Mrs Masai made her move. ");
                mine1.setMovie_duration("2hr 10mins");
                mine1.setMovie_portrait("portrait4");
                mine1.setMovie_landscape("landscape2");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }

            if (i == 4) {
                mine1.setMovie_id(5);
                mine1.setMovie_name("A Case");
                mine1.setMovie_year("2016");
                mine1.setMovie_plot("Feeling the power there in, Mrs Masai made her move. ");
                mine1.setMovie_duration("2hr 10mins");
                mine1.setMovie_portrait("portrait5");
                mine1.setMovie_landscape("landscape2");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }


            if (i == 5) {
                mine1.setMovie_id(6);
                mine1.setMovie_name("Love Potion");
                mine1.setMovie_year("2016");
                mine1.setMovie_plot("Feeling the power there in, Mrs Masai made her move. ");
                mine1.setMovie_duration("2hr 10mins");
                mine1.setMovie_portrait("portrait6");
                mine1.setMovie_landscape("landscape2");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }


            if (i == 6) {
                mine1.setMovie_id(6);
                mine1.setMovie_name("The Prisoner");
                mine1.setMovie_year("2016");
                mine1.setMovie_plot("Feeling the power there in, Mrs Masai made her move. ");
                mine1.setMovie_duration("2hr 10mins");
                mine1.setMovie_portrait("portrait6");
                mine1.setMovie_landscape("landscape2");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }


            if (i == 7) {
                mine1.setMovie_id(6);
                mine1.setMovie_name("Power Spree");
                mine1.setMovie_year("2016");
                mine1.setMovie_plot("Feeling the power there in, Mrs Masai made her move. ");
                mine1.setMovie_duration("2hr 10mins");
                mine1.setMovie_portrait("portrait6");
                mine1.setMovie_landscape("landscape2");
                mine1.setMovie_trailer_link("trailer_link");
                mine1.setMovie_amount_naira("₦ 225");
                mine1.setMovie_amount_dollar("$1");
            }

            RecommendedListDataGenerator.addOneData(mine1);
            NewonshugabanListDataGenerator.addOneData(mine1);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (!getActivity().isFinishing() && m_recommended_list_recyclerview != null) {
                        m_recommended_list_recyclerview.getAdapter().notifyItemInserted(RecommendedListDataGenerator.getAllData().size());
                        m_newonshugaban_list_recyclerview.getAdapter().notifyItemInserted(NewonshugabanListDataGenerator.getAllData().size());
                        //m_loading_progressbar.setVisibility(View.INVISIBLE);
                        //m_reload_imageview.setVisibility(View.INVISIBLE);
                        m_recommended_list_recyclerview.setVisibility(View.VISIBLE);
                        m_newonshugaban_list_recyclerview.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }


    private void populate_cats() {

        for (int i = 0; i < 8; i++) {
            CategoryModel mine1 = new CategoryModel();

            if(i == 0){
                mine1.setCat_id(1);
                mine1.setCategory_name("Drama");
            } else if(i == 1){
                mine1.setCat_id(2);
                mine1.setCategory_name("Igbo");
            } else if(i == 2){
                mine1.setCat_id(3);
                mine1.setCategory_name("Comedy");
            } else if(i == 3){
                mine1.setCat_id(4);
                mine1.setCategory_name("Hausa");
            } else if(i == 4){
                mine1.setCat_id(5);
                mine1.setCategory_name("Yuroba");
            } else if(i == 5){
                mine1.setCat_id(6);
                mine1.setCategory_name("Short Films");
            } else if(i == 6){
                mine1.setCat_id(7);
                mine1.setCategory_name("Documentary");
            } else if(i == 7){
                mine1.setCat_id(8);
                mine1.setCategory_name("Action Drama");
            }

            CategoriesListDataGenerator.addOneData(mine1);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (!getActivity().isFinishing() && m_recommended_list_recyclerview != null) {
                        m_categories_list_recyclerview.getAdapter().notifyItemInserted(RecommendedListDataGenerator.getAllData().size());
                        m_recommended_list_recyclerview.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }
}
