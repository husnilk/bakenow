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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
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

    @BindView(R.id.recipe_video_not_found)
    ImageView mImageNoVideo;

    private long stepId;
    private long currentVideoPosition = C.INDEX_UNSET;
    private int currentWindowPosition = C.INDEX_UNSET;
    private Uri mediaUri;
    SimpleExoPlayer mExoPlayer;

    public StepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, view);

        mExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.dining_icon));

        releasePlayer();
        clearResumePosition();

        Log.d(TAG, "Create View : " + String.valueOf(currentWindowPosition) + " , " + String.valueOf(currentVideoPosition));

        prepareMedia();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Util.SDK_INT <= 23 || mExoPlayer == null){
            initializePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        //releasePlayer();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT > 23){
            releasePlayer();
        }
    }

    public void setStepId(long stepId) {
        this.stepId = stepId;
    }

    public void prepareMedia(){
        Step step = Step.findById(Step.class, stepId);
        if(step != null){
            mediaUri = Uri.parse(step.getVideoURL())
                    .buildUpon()
                    .build();
            mDescription.setText(step.getDescription());
        }
    }

    public void initializePlayer(){
        if(mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector, loadControl);
            mExoPlayer.addListener(this);
            mExoPlayerView.setPlayer(mExoPlayer);
        }
        String userAgent = Util.getUserAgent(getContext(), "Bakenow");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

        //resuming
        mExoPlayer.setPlayWhenReady(true);
        boolean resuming = currentWindowPosition != C.INDEX_UNSET;
        if(resuming) {
            Log.d(TAG, "Initialize Player resuming : " + String.valueOf(currentWindowPosition) + " , " + String.valueOf(currentVideoPosition));
            mExoPlayer.seekTo(currentWindowPosition, currentVideoPosition);
        }
        mExoPlayer.prepare(mediaSource, !resuming, false);
        Log.d(TAG, "Initialize Player resuming : " + String.valueOf(mExoPlayer.getCurrentWindowIndex()) + " , " + String.valueOf(mExoPlayer.getContentPosition()));

    }

    public void releasePlayer(){
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            updateResumePosition();
            mExoPlayer.release();
            mExoPlayer.removeListener(this);
            mExoPlayer = null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        releasePlayer();
        Log.d(TAG, "OnSaveInstanceState : " + String.valueOf(currentWindowPosition) + " , " + String.valueOf(currentVideoPosition));
        outState.putLong(getString(R.string.current_video_position_key), currentVideoPosition);
        outState.putInt(getString(R.string.current_window_position_key), currentWindowPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null) {
            clearResumePosition();
        }else{
            currentVideoPosition = savedInstanceState.getLong(getString(R.string.current_video_position_key));
            currentWindowPosition = savedInstanceState.getInt(getString(R.string.current_window_position_key));
        }
        Log.d(TAG, "OnActivityCreated : " + String.valueOf(currentWindowPosition) + " , " + String.valueOf(currentVideoPosition));
    }

    public void updateResumePosition(){
        if(mExoPlayer != null) {
            currentWindowPosition = mExoPlayer.getCurrentWindowIndex();
            currentVideoPosition = Math.max(0, mExoPlayer.getContentPosition());
            Log.d(TAG, "Update Resume Position : " + String.valueOf(currentWindowPosition) + " , " + String.valueOf(currentVideoPosition));
        }
    }

    public void clearResumePosition(){
        currentVideoPosition = C.INDEX_UNSET;
        currentWindowPosition = C.INDEX_UNSET;
        Log.d(TAG, "Clear Resume Position : " + String.valueOf(currentWindowPosition) + " , " + String.valueOf(currentVideoPosition));
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
        mExoPlayerView.setVisibility(View.GONE);
        releasePlayer();
        mImageNoVideo.setVisibility(View.VISIBLE);
        mImageNoVideo.setImageResource(R.drawable.no_video_image);
        Toast.makeText(getContext(), "Couldn't load video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
