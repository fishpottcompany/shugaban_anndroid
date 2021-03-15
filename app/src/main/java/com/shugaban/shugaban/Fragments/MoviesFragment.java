package com.shugaban.shugaban.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shugaban.shugaban.R;

public class MoviesFragment extends Fragment  implements View.OnClickListener {

    private TextView m_recommended_textview, m_newonshugaban_textview, m_categories_textview;
    private RecyclerView m_recommended_list_recyclerview, m_newonshugaban_list_recyclerview, m_categories_list_recyclerview;
    private LinearLayoutManager m_recommended_list_linearlayoutmanager, m_newonshugaban_list_linearlayoutmanager, m_categories_list_linearlayoutmanager;
    private ImageView m_reload_imageview;
    private ProgressBar m_loading_progressbar;

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

        m_loading_progressbar = view.findViewById(R.id.fragment_movies_recommended_for_you_textview);
        m_reload_imageview = view.findViewById(R.id.fragment_movies_recommended_for_you_textview);
        m_recommended_textview = view.findViewById(R.id.fragment_movies_recommended_for_you_textview);
        m_newonshugaban_textview = view.findViewById(R.id.fragment_movies_new_releases);
        m_categories_textview = view.findViewById(R.id.fragment_movies_cats_textview);
        m_recommended_list_recyclerview = view.findViewById(R.id.fragment_movies_recommended_for_you_list_recyclerview);
        m_newonshugaban_list_recyclerview = view.findViewById(R.id.fragment_movies_new_releases_list_recyclerview);
        m_categories_list_recyclerview = view.findViewById(R.id.fragment_movies_cats_list_recyclerview);


        m_recommended_list_linearlayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        m_newonshugaban_list_linearlayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        m_categories_list_linearlayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());

        m_recommended_list_recyclerview.setItemViewCacheSize(20);
        m_recommended_list_recyclerview.setDrawingCacheEnabled(true);
        m_recommended_list_recyclerview.setHasFixedSize(true);
        m_recommended_list_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        m_recommended_list_recyclerview.setLayoutManager(m_recommended_list_linearlayoutmanager);
        m_recommended_list_recyclerview.setAdapter(new RecommendedListRecyclerViewAdapter());

        m_recommended_list_recyclerview.setItemViewCacheSize(20);
        m_recommended_list_recyclerview.setDrawingCacheEnabled(true);
        m_recommended_list_recyclerview.setHasFixedSize(true);
        m_recommended_list_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        m_recommended_list_recyclerview.setLayoutManager(m_recommended_list_linearlayoutmanager);
        m_recommended_list_recyclerview.setAdapter(new NewonshugabanListRecyclerViewAdapter());

        m_recommended_list_recyclerview.setItemViewCacheSize(20);
        m_recommended_list_recyclerview.setDrawingCacheEnabled(true);
        m_recommended_list_recyclerview.setHasFixedSize(true);
        m_recommended_list_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        m_recommended_list_recyclerview.setLayoutManager(m_recommended_list_linearlayoutmanager);
        m_recommended_list_recyclerview.setAdapter(new CategoriesListRecyclerViewAdapter());


        return view;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == m_reload_imageview.getId()){
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


    private void allOnClickHandlers(View v, int position){
        if(v.getId() == R.id.article_parent
                || v.getId() == R.id.fragment_today_heraldofglorytitle_textview
                || v.getId() == R.id.fragment_today_heraldofglorybody_textview
                || v.getId() == R.id.tag_holder
                || v.getId() == R.id.fragment_today_heraldofgloryimage_roundedcornerimageview
                || v.getId() == R.id.fragment_today_heraldofglorylabel_textview){

            setSharedPreferenceString(getActivity().getApplicationContext(), SHARED_PREF_KEY_IMAGE_ARTICLE_IMG_URL, String.valueOf(ArticleListDataGenerator.getAllData().get(position).getArticle_image()));
            setSharedPreferenceString(getActivity().getApplicationContext(), SHARED_PREF_KEY_IMAGE_ARTICLE_TAG_TEXT, ArticleListDataGenerator.getAllData().get(position).getArticle_type());
            setSharedPreferenceString(getActivity().getApplicationContext(), SHARED_PREF_KEY_IMAGE_ARTICLE_UPLOAD_TIME, ArticleListDataGenerator.getAllData().get(position).getCreated_at());
            setSharedPreferenceString(getActivity().getApplicationContext(), SHARED_PREF_KEY_IMAGE_ARTICLE_ARTICLE_TITLE, ArticleListDataGenerator.getAllData().get(position).getArticle_title());
            setSharedPreferenceString(getActivity().getApplicationContext(), SHARED_PREF_KEY_IMAGE_ARTICLE_ARTICLE_BODY, ArticleListDataGenerator.getAllData().get(position).getArticle_body());
            Intent intent = new Intent(getActivity().getApplicationContext(), ImageArticleActivity.class);
            startActivity(intent);
        }
    }


    private class RecommendedListRecyclerViewAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {
            if(RecommendedListDataGenerator.getAllData().get(position).getRowId() == 0){
                return 0;
            }
            return 1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_read, parent, false);
            vh = new ArticleViewHolder(v);

            return vh;
        }


        public class RecommendedMoviewPortraitViewHolder extends RecyclerView.ViewHolder  {
            private ConstraintLayout m_parent_holder_constraintlayout, m_tag_holder_constraintlayout;
            private ImageView m_audio_image;
            private TextView m_title_textview, m_body_textview, m_tag_textview;

            private View.OnClickListener innerClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allOnClickHandlers(v, getAdapterPosition());
                }
            };

            public ArticleViewHolder(View v) {
                super(v);
                m_parent_holder_constraintlayout = v.findViewById(R.id.article_parent);
                m_title_textview = v.findViewById(R.id.fragment_today_heraldofglorytitle_textview);
                m_body_textview = v.findViewById(R.id.fragment_today_heraldofglorybody_textview);
                m_tag_holder_constraintlayout = v.findViewById(R.id.tag_holder);
                m_audio_image = v.findViewById(R.id.fragment_today_heraldofgloryimage_roundedcornerimageview);
                m_tag_textview = v.findViewById(R.id.fragment_today_heraldofglorylabel_textview);

                m_parent_holder_constraintlayout.setOnClickListener(innerClickListener);
                m_title_textview.setOnClickListener(innerClickListener);
                m_body_textview.setOnClickListener(innerClickListener);
                m_tag_holder_constraintlayout.setOnClickListener(innerClickListener);
                m_audio_image.setOnClickListener(innerClickListener);
                m_tag_textview.setOnClickListener(innerClickListener);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

            if(
                    !getActivity().isFinishing()
                            && !ArticleListDataGenerator.getAllData().get(position).getArticle_image().equalsIgnoreCase("")
            ){

                loadImageView(getActivity().getApplicationContext(), ArticleListDataGenerator.getAllData().get(position).getArticle_image().trim(), ((ArticleViewHolder) holder).m_audio_image, null);

            } else {
                ((ArticleViewHolder) holder).m_audio_image.setImageResource(R.drawable.testimg);
            }

            if(ArticleListDataGenerator.getAllData().get(position).getArticle_title().length() > 20){
                ((ArticleViewHolder) holder).m_body_textview.setText(ArticleListDataGenerator.getAllData().get(position).getArticle_title().substring(0, 19).trim() + "...");
            } else {
                ((ArticleViewHolder) holder).m_title_textview.setText(ArticleListDataGenerator.getAllData().get(position).getArticle_title());
            }

            if(ArticleListDataGenerator.getAllData().get(position).getArticle_body().length() > 200){
                ((ArticleViewHolder) holder).m_body_textview.setText(ArticleListDataGenerator.getAllData().get(position).getArticle_body().substring(0, 199).trim() + "...");
            } else {
                ((ArticleViewHolder) holder).m_body_textview.setText(ArticleListDataGenerator.getAllData().get(position).getArticle_body());
            }

            ((ArticleViewHolder) holder).m_tag_textview.setText(ArticleListDataGenerator.getAllData().get(position).getArticle_type());

            if(ArticleListDataGenerator.getAllData().get(position).getArticle_type().equalsIgnoreCase("HERALD OF GLORY")){
                //((ArticleViewHolder) holder).m_tag_holder_constraintlayout.setBackground(getActivity().getResources().getDrawable(R.drawable.curved_bottom_left_corners_gold, null));
            } else if(ArticleListDataGenerator.getAllData().get(position).getArticle_type().equalsIgnoreCase("SPECIAL ARTICLE")){
                //((ArticleViewHolder) holder).m_tag_holder_constraintlayout.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corners_specialarticle, null));
            } else if(ArticleListDataGenerator.getAllData().get(position).getArticle_type().equalsIgnoreCase("GLORY NEWS")){
                //((ArticleViewHolder) holder).m_tag_holder_constraintlayout.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corners_glorynews, null));
            } else if(ArticleListDataGenerator.getAllData().get(position).getArticle_type().equalsIgnoreCase("BIBLE READING PLAN")){
                //((ArticleViewHolder) holder).m_tag_holder_constraintlayout.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corners_bible_reading, null));
            }

        }

        @Override
        public int getItemCount() {
            return ArticleListDataGenerator.getAllData().size();
        }

    }
}
