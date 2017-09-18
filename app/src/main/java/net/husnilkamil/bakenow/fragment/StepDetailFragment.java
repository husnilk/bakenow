package net.husnilkamil.bakenow.fragment;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import net.husnilkamil.bakenow.ExoListener;
import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.entities.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment implements Player.EventListener{

    public final static String TAG = "StepDetailFragment";

    @BindView(R.id.recipe_video_player)
    SimpleExoPlayerView mExoPlayerView;

    @BindView(R.id.description_text)
    TextView mDescription;

    private long stepId;
    SimpleExoPlayer mExoPlayer;

    public StepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, view);

        mExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.dining_icon));

        loadData();

        return view;
    }

    public void setStepId(long stepId) {
        this.stepId = stepId;
    }
    public void loadData(){
        Step step = Step.findById(Step.class, stepId);
        if(step != null){
            Uri mediaUri = Uri.parse(step.getVideoURL())
                    .buildUpon()
                    .build();

            initializePlayer(mediaUri);
            mDescription.setText(step.getDescription());
        }
    }

    public void initializePlayer(Uri mediaUri){
        if(mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector, loadControl);
            mExoPlayer.addListener(this);
            mExoPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "Bakenow");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            //mExoPlayer.setPlayWhenReady(true);
        }
    }

    public void releasePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer.removeListener(this);
        mExoPlayer = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState){
            case Player.STATE_READY:
                Log.d(TAG, "Player is ready");
                break;
            case Player.STATE_ENDED:
                Log.d(TAG, "Player is finishing video");
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        releasePlayer();
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
