package eu.alfred.tutorial.ui.activity;

import android.animation.Animator;
import android.app.ActionBar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import eu.alfred.tutorial.R;
import eu.alfred.tutorial.util.Constants;
import eu.alfred.tutorial.util.Prefs;
import eu.alfred.tutorial.util.StringUtils;
import eu.alfred.ui.BackToPAButton;
import eu.alfred.ui.CircleButton;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final long DEFAULT_STEP_ANIMATION_DURATION = 1000;

    @InjectView(R.id.imageButtonLocalSettings)
    ImageButton imageButtonLocalSettings;
    @InjectView(R.id.editTextSetAddress)
    EditText editTextSetAddress;
    @InjectView(R.id.buttonSetAddress)
    ImageButton buttonSetAddress;

    @InjectView(R.id.buttonPlayASound)
    TextView buttonPlayASound;
    @InjectView(R.id.buttonShowAnImage)
    TextView buttonShowAnImage;
    @InjectView(R.id.textViewSystem)
    TextView textViewSystem;
    @InjectView(R.id.imageSystem)
    ImageView imageSystem;
    @InjectView(R.id.imageViewAlfred)
    ImageView imageViewAlfred;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        init();
    }

    private void init() {
        initViews();
        setListeners();
    }

    private void initViews() {
        initActionBar();
        circleButton = (CircleButton) findViewById(R.id.voiceControlBtn);
        backToPAButton = (BackToPAButton) findViewById(R.id.backControlBtn);
        editTextSetAddress.setText(Prefs.getString(Constants.KEY_CADE_URL, Constants.LOCAL_CADE_URL));

        buttonPlayASound.setVisibility(View.GONE);
        buttonShowAnImage.setVisibility(View.GONE);
        imageSystem.setVisibility(View.GONE);
        imageViewAlfred.setVisibility(View.GONE);
        textViewSystem.setText(Html.fromHtml(getString(R.string.start_tutorial)));
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void setListeners() {
        circleButton.setOnTouchListener(new MicrophoneTouchListener());
        backToPAButton.setOnTouchListener(new BackTouchListener());
        imageButtonLocalSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSetAddress.getVisibility() == View.GONE) {
                    editTextSetAddress.setVisibility(View.VISIBLE);
                    buttonSetAddress.setVisibility(View.VISIBLE);
                } else {
                    editTextSetAddress.setVisibility(View.GONE);
                    buttonSetAddress.setVisibility(View.GONE);
                }
            }
        });
        editTextSetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Prefs.setString(Constants.KEY_CADE_URL, editTextSetAddress.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        buttonSetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cade.SetCadeBackendUrl(editTextSetAddress.getText().toString());
            }
        });
    }

    private void startStep() {
        imageViewAlfred.setAlpha(0f);
        imageViewAlfred.setVisibility(View.VISIBLE);
        imageViewAlfred.animate().alpha(1f).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
        textViewSystem.animate().alpha(0).setDuration(DEFAULT_STEP_ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String textSystem = getString(R.string.dialogue_1);
                Spanned spanned = Html.fromHtml(textSystem);
                textViewSystem.setText(spanned);
                textViewSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
        ;
        // TODO
        buttonPlayASound.setVisibility(View.GONE);
        buttonShowAnImage.setVisibility(View.GONE);
        imageSystem.setVisibility(View.GONE);
    }

    private void continueStep() {
        textViewSystem.animate().alpha(0).setDuration(DEFAULT_STEP_ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String textSystem = getString(R.string.dialogue_2);
                Spanned spanned = Html.fromHtml(textSystem);
                textViewSystem.setText(spanned);
                textViewSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
        ;
    }

    private void iUnderstandStep() {
        textViewSystem.animate().alpha(0).setDuration(DEFAULT_STEP_ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String textSystem = getString(R.string.dialogue_3);
                Spanned spanned = Html.fromHtml(textSystem);
                textViewSystem.setText(spanned);
                buttonPlayASound.setVisibility(View.VISIBLE);
                buttonShowAnImage.setVisibility(View.VISIBLE);
                textViewSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void playASound() {
        buttonPlayASound.setVisibility(View.GONE);
        buttonShowAnImage.setVisibility(View.GONE);
        textViewSystem.animate().alpha(0).setDuration(DEFAULT_STEP_ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String textSystem = getString(R.string.dialogue_4_1);
                Spanned spanned = Html.fromHtml(textSystem);
                textViewSystem.setText(spanned);
                textViewSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
                buttonPlayASound.setVisibility(View.GONE);
                buttonShowAnImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        }).start();
        playSound();
    }

    private void playSound() {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.start();
        }
    }

    private void showAnImage() {
        imageSystem.setAlpha(0f);
        imageSystem.setVisibility(View.VISIBLE);
        imageSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
        textViewSystem.animate().alpha(0).setDuration(DEFAULT_STEP_ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String textSystem = getString(R.string.dialogue_4_2);
                Spanned spanned = Html.fromHtml(textSystem);
                textViewSystem.setText(spanned);
                textViewSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
                buttonPlayASound.setVisibility(View.GONE);
                buttonShowAnImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void whatIsYourName(final String name) {
        imageSystem.setVisibility(View.GONE);
        textViewSystem.setVisibility(View.VISIBLE);
        textViewSystem.animate().alpha(0).setDuration(DEFAULT_STEP_ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String textSystem = (!TextUtils.isEmpty(name) ? " " + name + ", " : "") + getString(R.string.dialogue_5);
                Spanned spanned = Html.fromHtml(textSystem);
                textViewSystem.setText(spanned);
                textViewSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void age(final String age) {
        imageSystem.setVisibility(View.GONE);
        textViewSystem.setVisibility(View.VISIBLE);
        textViewSystem.animate().alpha(0).setDuration(DEFAULT_STEP_ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String textSystem = (!TextUtils.isEmpty(age) ? age : "") + ".<br>" + getString(R.string.dialogue_6);
                Spanned spanned = Html.fromHtml(textSystem);
                textViewSystem.setText(spanned);
                textViewSystem.animate().alpha(1).setDuration(DEFAULT_STEP_ANIMATION_DURATION).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    @Override
    public void performAction(String command, Map<String, String> map) {
        Log.d(TAG, "performAction command: #" + command + "#, map: #" + StringUtils.getReadableString(map) + "#");
        if (TextUtils.equals(command, Constants.CADE_ACTION_COMMAND_START)) {
            startStep();
        } else if (TextUtils.equals(command, Constants.CADE_ACTION_COMMAND_CONTINUE)) {
            continueStep();
        } else if (TextUtils.equals(command, Constants.CADE_ACTION_COMMAND_I_UNDERSTAND)) {
            iUnderstandStep();
        } else if (TextUtils.equals(command, Constants.CADE_ACTION_COMMAND_PLAY_A_SOUND)) {
            playASound();
        } else if (TextUtils.equals(command, Constants.CADE_ACTION_COMMAND_SHOW_AN_IMAGE)) {
            showAnImage();
        } else if (TextUtils.equals(command, Constants.CADE_ACTION_COMMAND_WHAT_IS_YOUR_NAME)) {
            String name = map.get("selected_name");
            if (!TextUtils.isEmpty(name)) {
                name = name.replace("_", " ");
            }
            whatIsYourName(name);
        } else if (TextUtils.equals(command, Constants.CADE_ACTION_COMMAND_WHAT_AGE)) {
            String age = map.get("selected_age");
            if (!TextUtils.isEmpty(age)) {
                age = age.replace("_", " ");
            }
            age(age);
        }
        cade.sendActionResult(true);
    }

    @Override
    public void performWhQuery(String command, Map<String, String> map) {
    }

    @Override
    public void performValidity(String command, Map<String, String> map) {
    }

    @Override
    public void performEntityRecognizer(String command, Map<String, String> map) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextSetAddress.setText(Prefs.getString(Constants.KEY_CADE_URL, Constants.LOCAL_CADE_URL));
        startMediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    private void startMediaPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
        player = MediaPlayer.create(this, R.raw.trumpet);
    }

    private void releaseMediaPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

}
