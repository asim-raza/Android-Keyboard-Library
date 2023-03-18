package com.contactbackup.cloud.keyboard.keyboard;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.text.method.MetaKeyKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.contactbackup.cloud.keyboard.R;
import com.contactbackup.cloud.keyboard.keyboard.database.DatabaseManager;
//import com.keyboard.MainActivity;
//import com.keyboard.MainApplication;
//import com.keyboard.R;
//import com.keyboard.database.DatabaseManager;
//import com.keyboard.helpers.FontsOverride;
//import com.keyboard.helpers.MyPrafrances;

import java.util.ArrayList;
import java.util.List;

import github.ankushsachdeva.emojicon.EmojiconGridView.OnEmojiconClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnEmojiconBackspaceClickedListener;
import github.ankushsachdeva.emojicon.EmojiconsPopup.OnSoftKeyboardOpenCloseListener;
import github.ankushsachdeva.emojicon.emoji.Emojicon;

public abstract class SoftKeyboard extends InputMethodService implements OnKeyboardActionListener {
    static final boolean PROCESS_HARD_KEYS = true;
    static final String TAG = "SoftKeyboard";
    public static String mActiveKeyboard;
    private int count = 0;
    private DatabaseManager db;
    private int[] iThemes = new int[]{R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6};
    private ArrayList<String> list;
    AudioManager mAudioManager;
    private View mCandidateView;
    private boolean mCapsLock;
    private boolean mCompletionOn;
    private CompletionInfo[] mCompletions;
    private StringBuilder mComposing = new StringBuilder();
    private LatinKeyboard mCurKeyboard;
    Drawable mDrawableTheme;
    InputConnection mInputConnection;
    private InputMethodManager mInputMethodManager;
    private LatinKeyboardView mInputView;
    protected int mLastDisplayWidth;
    private long mLastShiftTime;
    MediaPlayer mMediaPlayer;
    private long mMetaState;
    private boolean mPredictionOn;
    MyPrafrances mPrefs;
    public LatinKeyboard mQwertyKeyboard;
    private boolean mSound;
    private SuggestionsAdapter mSuggestionsAdapter;

    protected LatinKeyboard mSymbolsKeyboard;
    protected LatinKeyboard mSymbolsShiftedKeyboard;
    protected LatinKeyboard mSymbolsShiftedUrKeyboard;
    protected LatinKeyboard mSymbolsUrKeyboard;
    protected LatinKeyboard mUrduKeyboard;
    protected LatinKeyboard mUrduKeyboardC;
    protected LatinKeyboard mNumbersKeyboard;
    protected LatinKeyboard mPhoneKeyboard;


    private String mWordSeparators;
    private EmojiconsPopup popupWindow = null;
    SharedPreferences sharedPreferences;

//    class C03971 implements OnTouchListener {
//        C03971() {
//        }
//
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == 0) {
//                SoftKeyboard.this.mInputView.closing();
//            }
//            return false;
//        }
//    }
//
//    class C03982 implements OnClickListener {
//        C03982() {
//        }
//
//        public void onClick(View view) {
////            Intent intent = new Intent(SoftKeyboard.this, MainActivity.class);
//////            intent.addFlags(268435456);
////            intent.putExtra("val","theme");
////            SoftKeyboard.this.startActivity(intent);
//        }
//    }

//    class C03993 implements OnClickListener {
//        C03993() {
//        }
//
//        public void onClick(View view) {
//            Intent intent = new Intent(SoftKeyboard.this, Keyboard_ThemeActivity.class);
//            intent.addFlags(268435456);
//            SoftKeyboard.this.startActivity(intent);
//        }
//    }

    class C04004 implements Runnable {
        C04004() {
        }

        public void run() {
            try {
                SoftKeyboard.this.mMediaPlayer.start();
            } catch (Exception e) {
                Log.d("SimpleIME", e.toString());
            }
        }
    }

    private class SelectDataTask extends AsyncTask<String, Void, ArrayList<String>> {
        private ArrayList<String> list;
        private String subType;

        private SelectDataTask() {
        }

        void getSubtype(LatinKeyboard mCurKeyboard) {
            if (mCurKeyboard == SoftKeyboard.this.mQwertyKeyboard) {
                this.subType = "english";
            } else if (mCurKeyboard == SoftKeyboard.this.mUrduKeyboard) {
                this.subType = "pashto";
            } else {
                this.subType = "farsi";
            }
        }

        protected ArrayList<String> doInBackground(String... str) {
            this.list = SoftKeyboard.this.db.getAllRow(str[0], this.subType);
            return this.list;
        }

        protected void onPostExecute(ArrayList<String> result) {
            this.list = result;

            if(mPrefs.getShowSugession()){
                setSuggestions(result, SoftKeyboard.PROCESS_HARD_KEYS, SoftKeyboard.PROCESS_HARD_KEYS);
            }
        }
    }

    class C05265 implements OnSoftKeyboardOpenCloseListener {
        C05265() {
        }

        public void onKeyboardOpen(int keyBoardHeight) {
        }

        public void onKeyboardClose() {
            if (SoftKeyboard.this.popupWindow.isShowing()) {
                SoftKeyboard.this.popupWindow.dismiss();
            }
        }
    }

    class C05276 implements OnEmojiconClickedListener {
        C05276() {
        }

        public void onEmojiconClicked(Emojicon emojicon) {
            SoftKeyboard.this.mComposing.append(emojicon.getEmoji());
            SoftKeyboard.this.commitTyped(SoftKeyboard.this.getCurrentInputConnection());
        }
    }

    class C05287 implements OnEmojiconBackspaceClickedListener {
        C05287() {
        }

        public void onEmojiconBackspaceClicked(View v) {
            KeyEvent event = new KeyEvent(0, 0, 0, 67, 0, 0, 0, 0, 6);
            SoftKeyboard.this.handleBackspace();
        }
    }

    class C05298 implements OnEmojiconBackspaceClickedListener {
        C05298() {
        }

        public void onEmojiconBackspaceClicked(View v) {
            KeyEvent event = new KeyEvent(0, 0, 0, 67, 0, 0, 0, 0, 6);
            SoftKeyboard.this.handleBackspace();
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView textSuggestion;

        public CustomViewHolder(View itemView) {
            super(itemView);
            try {
                this.textSuggestion = (TextView) itemView.findViewById(R.id.text_suggestion);
            } catch (Exception e) {
                Log.d(SoftKeyboard.TAG, e.toString());
            }
        }
    }

    class SuggestionsAdapter extends RecyclerView.Adapter {
        private List<String> listSuggestion;

        public SuggestionsAdapter(List listSuggestion) {
            this.listSuggestion = listSuggestion;
        }

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomViewHolder(SoftKeyboard.this.getLayoutInflater().inflate(R.layout.keyboard_item_suggestion, null));
        }

        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            try {
                ((CustomViewHolder) holder).textSuggestion.setText((CharSequence) this.listSuggestion.get(position));
                ((CustomViewHolder) holder).itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            SoftKeyboard.this.pickSuggestionManually(((CustomViewHolder) holder).textSuggestion.getText().toString().trim());
                        } catch (Exception e) {
                            Log.d(SoftKeyboard.TAG, e.toString());
                        }
                    }
                });
            } catch (Exception e) {
                Log.d(SoftKeyboard.TAG, e.toString());
            }
        }

        public int getItemCount() {
            return this.listSuggestion == null ? 0 : this.listSuggestion.size();
        }

        public void clearData() {
            this.listSuggestion.clear();
            notifyDataSetChanged();
        }

        public void setSuggestionsList(List<String> listSuggestion) {
            this.listSuggestion = listSuggestion;
            notifyDataSetChanged();
        }
    }

    private void setKTheme(int icount) {
        if (icount < 6) {
            this.mDrawableTheme = getResources().getDrawable(this.iThemes[this.count]);
            if (VERSION.SDK_INT >= 16) {
                this.mInputView.setBackground(this.mDrawableTheme);
                return;
            } else {
                this.mInputView.setBackgroundDrawable(this.mDrawableTheme);
                return;
            }
        }
        this.count = 0;
    }

    public void onCreate() {
        try {
            super.onCreate();
            FontsOverride.setDefaultFont(getApplicationContext(), "DEFAULT", "fonts/simpo.ttf");
            this.mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            this.mWordSeparators = getResources().getString(R.string.word_separators);
            this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            mPrefs =  new MyPrafrances(getApplicationContext());


        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }
    }

    public abstract void onInitializeInterface();

    public View onCreateInputView() {
        FontsOverride.setDefaultFont(getApplicationContext(), "DEFAULT", "fonts/simpo.ttf");
        this.mPrefs = new MyPrafrances(this);
        View v = getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        this.mInputView = (LatinKeyboardView) v.findViewById(R.id.keyboard);
        //  this.mInputView.
        this.mInputView.setBackground(null);
        if (this.mPrefs == null) {
            this.mPrefs = MainApplication.getPrefrences();
        }
        this.mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        this.mInputView.setOnKeyboardActionListener(this);
        try {
            this.mInputView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 0) {
                        SoftKeyboard.this.mInputView.closing();
                    }
                    return false;
                }
            });
        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }
        return v;
    }

    public void onComputeInsets(Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    private void setLatinKeyboard(LatinKeyboard nextKeyboard) {
        nextKeyboard.setLanguageSwitchKeyVisibility(PROCESS_HARD_KEYS);
        if (this.mInputView != null) {
            this.mInputView.setKeyboard(nextKeyboard);
        }
    }

    public View onCreateCandidatesView() {
        try {
            this.mCandidateView = getLayoutInflater().inflate(R.layout.keyboard_candidate_view, null);
            RecyclerView recyclerView = (RecyclerView) this.mCandidateView.findViewById(R.id.list_suggestions);
            ImageView imgThemes = (ImageView) this.mCandidateView.findViewById(R.id.img_themes);

            ImageView imgSettings = (ImageView) this.mCandidateView.findViewById(R.id.img_settings);

            //((ImageView) this.mCandidateView.findViewById(R.id.img_settings)).setOnClickListener(new C03982());
            //imgThemes.setOnClickListener(new C03993());
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            if (this.mSuggestionsAdapter == null) {
                this.mSuggestionsAdapter = new SuggestionsAdapter(new ArrayList());
            }
            recyclerView.setAdapter(this.mSuggestionsAdapter);

//            imgThemes.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e(TAG,"Calling Main Activity with theme");
//                    Intent intent = new Intent(SoftKeyboard.this, MainActivity.class);
//                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("val","theme");
//                    SoftKeyboard.this.startActivity(intent);
//
//                }
//            });
//
//            imgSettings.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(SoftKeyboard.this, MainActivity.class);
//                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("val","setting");
//                    SoftKeyboard.this.startActivity(intent);
//
//                }
//            });

        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
        }
        return this.mCandidateView;
    }

    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        if (this.mInputView != null && this.mInputView.getParent() == null) {
            setInputView(this.mInputView);
        }
        this.mComposing.setLength(0);
        updateCandidates();
        if (!restarting) {
            this.mMetaState = 0;
        }
        this.mPredictionOn = false;
        this.mCompletionOn = false;
        this.mCompletions = null;
        switch (attribute.inputType & 15) {
            case 1:
                this.mCurKeyboard =mQwertyKeyboard;
                this.mPredictionOn = this.sharedPreferences.getBoolean("suggestion", PROCESS_HARD_KEYS);

                Log.e(TAG,String.valueOf(mPredictionOn));

                int variation = attribute.inputType & 4080;
                if (variation == 128 || variation == 144) {
                    this.mPredictionOn = false;
                }
                if (variation == 32 || variation == 16 || variation == 176) {
                    this.mPredictionOn = false;
                    mActiveKeyboard = "fr";
                    this.mCurKeyboard = this.mQwertyKeyboard;
                }
                if ((attribute.inputType & 65536) != 0) {
                    this.mPredictionOn = false;
                    this.mCompletionOn = isFullscreenMode();
                }
                updateShiftKeyState(attribute);
                break;
            case 2:
                this.mCurKeyboard = this.mNumbersKeyboard;
                break;
            case 3:
                this.mCurKeyboard = this.mPhoneKeyboard;
                break;
            case 4:
                this.mCurKeyboard = this.mSymbolsKeyboard;
                break;
            default:
                this.mCurKeyboard = mQwertyKeyboard;
                updateShiftKeyState(attribute);
                break;
        }
        if (this.mPredictionOn) {
            this.db = new DatabaseManager(this);
        }
        this.mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
        this.mSound = this.sharedPreferences.getBoolean("sound", PROCESS_HARD_KEYS);


        if(mPrefs.getSound()){
            if(!mPrefs.getSoundName().isEmpty()){
                this.mMediaPlayer = MediaPlayer.create(getApplicationContext(), RawWrapper.getRaw(this.mPrefs.getSoundName()));
            }
        }
        setLatinKeyboard(this.mCurKeyboard);
    }

    public void onFinishInput() {
        super.onFinishInput();
        clearCandidateView();
        this.mComposing.setLength(0);
        updateCandidates();
        setCandidatesViewShown(false);
        this.mCurKeyboard = this.mQwertyKeyboard;
        if (this.mInputView != null) {
            this.mInputView.closing();
        }
        if (this.db != null) {
            this.db.close();
        }
    }

    public void onStartInputView(EditorInfo attribute, boolean restarting) {


        Log.e(TAG,"onStartInputView");

        try {
            super.onStartInputView(attribute, restarting);
            setCandidatesViewShown(PROCESS_HARD_KEYS);
            closeEmoticons();
            this.mInputView.closing();
            this.mInputView.setSubtypeOnSpaceKey(this.mInputMethodManager.getCurrentInputMethodSubtype());
            if (this.mPrefs != null) {
                if (this.mPrefs.getLastOpenedKeyboard().equals("english")) {
                    setLatinKeyboard(this.mQwertyKeyboard);
                } else {
                    setLatinKeyboard(this.mUrduKeyboard);
                }
            }
            String theme = this.mPrefs.getTheme();
            Log.e(TAG,  "theme: Name" + theme);
            if (theme != null) {
                if (mPrefs.getCustomTheme()) {
                    Log.e(TAG,"getCustomTheme true");
                    BitmapDrawable bd = null;
                    try {
                        Bitmap bitmap = Media.getBitmap(getContentResolver(), Uri.parse(theme));
                        if (bitmap != null) {
                            bd = new BitmapDrawable(getResources(), bitmap);
                        }else{
                            Log.e(TAG,"bitmap is null");
                        }
                        if (bd != null) {
                            this.mInputView.setBackground(bd);
                        }else{
                            Log.e(TAG,"bd is null");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.mInputView.setBackgroundResource(getResources().getIdentifier(theme, "drawable", getPackageName()));
                    }
                } else if (theme.contains("#")) {
                    this.mInputView.setBackgroundResource(0);
                    this.mInputView.setBackgroundColor(Color.parseColor(theme));
                } else {
                    this.mInputView.setBackgroundResource(getResources().getIdentifier(theme, "drawable", getPackageName()));
                }
            }
            if (this.mCurKeyboard != null) {
                Log.d("COUNT", this.mCurKeyboard.getKeys().size() + "");
            }
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        }
    }

    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype) {
        try {
            if (this.mInputView != null) {
                this.mInputView.setSubtypeOnSpaceKey(subtype);
                String s = subtype.getLocale();
                int obj = 0;
                switch (s.hashCode()) {
                    case 96646644:
                        if (s.equals("en_US")) {
                            obj = 1;
                            break;
                        }
                        break;
                    case 111541981:
                        if (s.equals("ur_PK")) {
                            obj = 2;
                            break;
                        }
                        break;
                }
                switch (obj) {
                    case 2:
                        mActiveKeyboard = "ur_PK";
                        this.mCurKeyboard = this.mUrduKeyboard;
                        break;
                    case 1:
                        mActiveKeyboard = "en_US";
                        this.mCurKeyboard = this.mQwertyKeyboard;
                        break;
                    default:
                        mActiveKeyboard = "ur_PK";
                        this.mCurKeyboard = this.mUrduKeyboard;
                        break;
                }
                setLatinKeyboard(this.mCurKeyboard);
            }
        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        if (this.mComposing.length() <= 0) {
            return;
        }
        if (newSelStart != candidatesEnd || newSelEnd != candidatesEnd) {
            this.mComposing.setLength(0);
            updateCandidates();
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }

    public void onDisplayCompletions(CompletionInfo[] completions) {
        int i = 0;
        if (this.mCompletionOn) {
            this.mCompletions = completions;
            if (completions == null) {
                setSuggestions(null, false, false);
                return;
            }
            List<String> stringList = new ArrayList();
            int length = completions.length;
            while (i < length) {
                CompletionInfo ci = completions[i];
                if (ci != null) {
                    stringList.add(ci.getText().toString());
                }
                i++;
            }
            if (mPrefs.getShowSugession()) {
                setSuggestions(stringList, PROCESS_HARD_KEYS, PROCESS_HARD_KEYS);
            }
        }
    }

    private boolean translateKeyDown(int keyCode, KeyEvent event) {
        this.mMetaState = MetaKeyKeyListener.handleKeyDown(this.mMetaState, keyCode, event);
        int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(this.mMetaState));
        this.mMetaState = MetaKeyKeyListener.adjustMetaAfterKeypress(this.mMetaState);
        InputConnection ic = getCurrentInputConnection();
        if (c == 0 || ic == null) {
            return false;
        }
        if ((Integer.MIN_VALUE & c) != 0) {
            // c &= ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        if (this.mComposing.length() > 0) {
            int composed = KeyEvent.getDeadChar(this.mComposing.charAt(this.mComposing.length() - 1), c);
            if (composed != 0) {
                c = composed;
                this.mComposing.setLength(this.mComposing.length() - 1);
            }
        }
        onKey(c, null);
        return PROCESS_HARD_KEYS;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
        switch (paramInt)
        {
            default:
                if ((paramInt == 62) && ((0x2 & paramKeyEvent.getMetaState()) != 0))
                {
                    InputConnection localInputConnection = getCurrentInputConnection();
                    if (localInputConnection != null)
                    {
                        localInputConnection.clearMetaKeyStates(2);
                        keyDownUp(29);
                        keyDownUp(42);
                        keyDownUp(32);
                        keyDownUp(46);
                        keyDownUp(43);
                        keyDownUp(37);
                        keyDownUp(32);
                        return true;
                    }
                }
                break;
            case 4:
                if ((paramKeyEvent.getRepeatCount() != 0) || (this.mInputView == null)) {
                    break;
                }
        }
        while ((!this.mPredictionOn) || (!translateKeyDown(paramInt, paramKeyEvent)))
        {
            do
            {
                return super.onKeyDown(paramInt, paramKeyEvent);
            } while (this.mComposing.length() <= 0);
            ///    onKey(-5, null);
            // return true;
            // return false;
        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (this.mPredictionOn) {
            this.mMetaState = MetaKeyKeyListener.handleKeyUp(this.mMetaState, keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    private void commitTyped(InputConnection inputConnection) {
        if (this.mComposing.length() > 0) {
            if (this.db != null) {
                this.db.updateFreq(this.mComposing.toString().trim());
            }
            inputConnection.commitText(this.mComposing, 1);
            this.mComposing.setLength(0);
            updateCandidates();
        }
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null) {
            try {
                if (this.mInputView != null && this.mQwertyKeyboard == this.mInputView.getKeyboard()) {
                    int caps = 0;
                    EditorInfo ei = getCurrentInputEditorInfo();
                    if (!(ei == null || ei.inputType == 0)) {
                        caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
                    }
                    LatinKeyboardView latinKeyboardView = this.mInputView;
                    boolean z = (this.mCapsLock || caps != 0) ? PROCESS_HARD_KEYS : false;
                    latinKeyboardView.setShifted(z);
                    updateShiftIcon();
                }
            } catch (RuntimeException e) {
                Log.d(TAG, e.toString());
            }
        }
    }

    private boolean isAlphabet(int code) {
        return Character.isLetter(code);
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, keyEventCode));
    }

    private void sendKey(int keyCode) {
        switch (keyCode) {
            case 10:
                keyDownUp(66);
                return;
            default:
                if (keyCode < 48 || keyCode > 57) {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                    return;
                } else {
                    keyDownUp((keyCode - 48) + 7);
                    return;
                }
        }
    }

    public void onKey(int primaryCode, int[] keyCodes) {

        if (isWordSeparator(primaryCode)) {
            if (this.mComposing.length() > 0) {
                commitTyped(getCurrentInputConnection());
            }
            if (primaryCode == 32 && this.list != null) {
                clearCandidateView();
            }
            sendKey(primaryCode);
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (primaryCode == -5) {
            handleBackspace();
        } else if (primaryCode == -1) {
            handleShift();
        } else if (primaryCode == -3) {
            handleClose();
        } else if (primaryCode == -101) {
            handleLanguageSwitch();
        } else if (primaryCode == -100) {
        } else {
            if (primaryCode == -2 && this.mInputView != null) {
                handleSymbols();
            } else if (primaryCode == -10000) {
                showEmoticons();
            } else if (primaryCode == -10001) {
                this.mComposing.append("‌");
                getCurrentInputConnection().setComposingText(this.mComposing, 1);
            } else if (primaryCode == -10002) {
                this.mComposing.append("ẋ");
                getCurrentInputConnection().setComposingText(this.mComposing, 1);
            } else if (primaryCode == -10003) {
                this.mComposing.append("Ẋ");
                getCurrentInputConnection().setComposingText(this.mComposing, 1);
            } else if (primaryCode == 1567) {
                this.mComposing.append("؟");
                getCurrentInputConnection().setComposingText(this.mComposing, 1);
            } else {
                handleCharacter(primaryCode, keyCodes);
            }
        }
    }

    private void playClick(int keyCode) {
        try {
            if (this.mAudioManager == null) {
                return;
            }
//
//            if (this.mPrefs.getKeySound() == null) {
//                switch (keyCode) {
//                    case -5:
//                        this.mAudioManager.playSoundEffect(7);
//                        return;
//                    case FontRequestCallback.FAIL_REASON_SECURITY_VIOLATION /*-4*/:
//                        this.mAudioManager.playSoundEffect(8);
//                        return;
//                    case 32:
//                        this.mAudioManager.playSoundEffect(6);
//                        return;
//                    default:
//                        this.mAudioManager.playSoundEffect(5);
//                        return;
//                }
//                //Log.d(TAG, e.toString());
//            }
//
            new Thread(new C04004()).start();
        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }
    }

    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.beginBatchEdit();
            if (this.mComposing.length() > 0) {
                commitTyped(ic);
            }
            ic.commitText(text, 0);
            ic.endBatchEdit();
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
    }

    private void updateCandidates() {
        if (!this.mCompletionOn && this.mPredictionOn) {
            if (this.mComposing.length() > 0) {
                SelectDataTask selectDataTask = new SelectDataTask();
                this.list = new ArrayList();
                this.list.add(this.mComposing.toString());
                if (this.mSuggestionsAdapter != null && mPrefs.getShowSugession()) {
                    this.mSuggestionsAdapter.setSuggestionsList(this.list);
                }
                selectDataTask.getSubtype(this.mCurKeyboard);
                selectDataTask.execute(new String[]{this.mComposing.toString()});
                return;
            }
            setSuggestions(null, false, false);
        }
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
        if (mPrefs != null) {
            if (suggestions != null && suggestions.size() > 0 && mPrefs.getShowSugession()) {
                setCandidatesViewShown(PROCESS_HARD_KEYS);
                this.mSuggestionsAdapter.setSuggestionsList(suggestions);
            } else if (isExtractViewShown()) {
                setCandidatesViewShown(false);
            }
        }
        if (this.mSuggestionsAdapter != null) {
            if (mPrefs.getShowSugession()) {
                this.mSuggestionsAdapter.setSuggestionsList(suggestions);
            }
            this.mSuggestionsAdapter.notifyDataSetChanged();
        }
    }

    private void handleBackspace() {
        int length = this.mComposing.length();
        if (length > 1) {
            this.mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(this.mComposing, 1);
            updateCandidates();
        } else if (length > 0) {
            this.mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else {
            keyDownUp(67);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private void handleShift() {
        boolean z = false;
        if (this.mInputView != null) {
            Keyboard currentKeyboard = this.mInputView.getKeyboard();
            if (this.mQwertyKeyboard == currentKeyboard) {
                checkToggleCapsLock();
                LatinKeyboardView latinKeyboardView = this.mInputView;
                if (this.mCapsLock || !this.mInputView.isShifted()) {
                    z = PROCESS_HARD_KEYS;
                }
                latinKeyboardView.setShifted(z);
            }


            if (currentKeyboard == this.mUrduKeyboard) {
                this.mInputView.setKeyboard(this.mUrduKeyboardC);
                this.mCapsLock = PROCESS_HARD_KEYS;
            }
            if (currentKeyboard == this.mUrduKeyboardC) {
                this.mInputView.setKeyboard(this.mUrduKeyboard);
                this.mCapsLock = PROCESS_HARD_KEYS;
            }




            else if (currentKeyboard == this.mSymbolsKeyboard) {
                this.mSymbolsKeyboard.setShifted(PROCESS_HARD_KEYS);
                setLatinKeyboard(this.mSymbolsShiftedKeyboard);
                this.mSymbolsShiftedKeyboard.setShifted(PROCESS_HARD_KEYS);
            }
            else if (currentKeyboard == this.mSymbolsUrKeyboard) {
                this.mSymbolsKeyboard.setShifted(PROCESS_HARD_KEYS);
                setLatinKeyboard(this.mSymbolsShiftedUrKeyboard);
                this.mSymbolsShiftedKeyboard.setShifted(PROCESS_HARD_KEYS);
            }



            else if (currentKeyboard == this.mSymbolsShiftedUrKeyboard) {
                this.mSymbolsShiftedUrKeyboard.setShifted(false);
                setLatinKeyboard(this.mSymbolsUrKeyboard);
                this.mSymbolsUrKeyboard.setShifted(false);
            }
            else if (currentKeyboard == this.mSymbolsShiftedKeyboard) {
                this.mSymbolsShiftedKeyboard.setShifted(false);
                setLatinKeyboard(this.mSymbolsKeyboard);
                this.mSymbolsKeyboard.setShifted(false);
            }
            updateShiftIcon();
        }
    }

    private void handleSymbols() {
        try {
            if (this.mInputView != null) {
                Keyboard currentKeyboard = this.mInputView.getKeyboard();
                if (this.mQwertyKeyboard == currentKeyboard) {
                    this.mSymbolsShiftedUrKeyboard.setShifted(false);
                    setLatinKeyboard(this.mSymbolsKeyboard);
                    this.mSymbolsUrKeyboard.setShifted(false);
                } else if (currentKeyboard == this.mSymbolsKeyboard || currentKeyboard == this.mSymbolsShiftedKeyboard) {
                    this.mSymbolsShiftedUrKeyboard.setShifted(false);
                    setLatinKeyboard(this.mQwertyKeyboard);
                    this.mSymbolsUrKeyboard.setShifted(false);
                } else if (currentKeyboard == this.mUrduKeyboard) {
                    this.mSymbolsShiftedUrKeyboard.setShifted(false);
                    setLatinKeyboard(this.mSymbolsUrKeyboard);
                    this.mSymbolsUrKeyboard.setShifted(false);
                }

                else if (currentKeyboard == this.mUrduKeyboardC) {
                    this.mSymbolsShiftedUrKeyboard.setShifted(false);
                    setLatinKeyboard(this.mSymbolsUrKeyboard);
                    this.mSymbolsUrKeyboard.setShifted(false);
                }



                else if (currentKeyboard == this.mSymbolsShiftedUrKeyboard || currentKeyboard == this.mSymbolsUrKeyboard) {
                    this.mSymbolsShiftedUrKeyboard.setShifted(false);
                    setLatinKeyboard(this.mUrduKeyboard);
                    this.mSymbolsUrKeyboard.setShifted(false);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void updateShiftIcon() {
        try {
            List<Key> keys = this.mQwertyKeyboard.getKeys();
            for (int i = 0; i < keys.size() - 1; i++) {
                Key currentKey = (Key) keys.get(i);
                this.mInputView.invalidateAllKeys();
                if (currentKey.codes[0] == -1) {
                    currentKey.label = null;

                    if (this.mCapsLock) {
                        currentKey.icon = getResources().getDrawable(R.drawable.shift_double);
                        return;
                    } else if (this.mInputView.isShifted()) {
                        currentKey.icon = getResources().getDrawable(R.drawable.shift_empty);
                        return;
                    } else {
                        currentKey.icon = getResources().getDrawable(R.drawable.ic_shift);
                        return;
                    }

                }
            }
        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        try {
            if (isInputViewShown() && this.mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
            if (isAlphabet(primaryCode) && this.mPredictionOn) {
                this.mComposing.append((char) primaryCode);
                getCurrentInputConnection().setComposingText(this.mComposing, 1);
                updateShiftKeyState(getCurrentInputEditorInfo());
                updateCandidates();
            } else {
                this.mComposing.append((char) primaryCode);
                getCurrentInputConnection().setComposingText(this.mComposing, 1);
            }

            if (this.mPrefs.getVibration()) {
                vibratePhone();
            }
            if (this.mPrefs.getSound()) {
                playClick(primaryCode);
            }

        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        this.mInputView.closing();
    }

    private IBinder getToken() {
        Dialog dialog = getWindow();
        if (dialog == null) {
            return null;
        }
        Window window = dialog.getWindow();
        if (window != null) {
            return window.getAttributes().token;
        }
        return null;
    }

    private void handleLanguageSwitch() {
        try {
            if (this.mInputView != null) {
                Keyboard keyboard = this.mInputView.getKeyboard();
                if (keyboard == this.mUrduKeyboard || keyboard == this.mSymbolsUrKeyboard
                        || keyboard == this.mSymbolsShiftedUrKeyboard|| keyboard == this.mUrduKeyboardC) {
                    setLatinKeyboard(this.mQwertyKeyboard);
                    this.mPrefs.setLastOpenedKeyboard("english");
                    return;
                }
                setLatinKeyboard(this.mUrduKeyboard);
                this.mPrefs.setLastOpenedKeyboard("urdu");
            }
        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (this.mLastShiftTime + 800 > now) {
            this.mCapsLock = !this.mCapsLock ? PROCESS_HARD_KEYS : false;
            this.mLastShiftTime = 0;
            return;
        }
        this.mLastShiftTime = now;
    }

    private String getWordSeparators() {
        return this.mWordSeparators;
    }

    public boolean isWordSeparator(int code) {
        return getWordSeparators().contains(String.valueOf((char) code));
    }

    public void pickDefaultCandidate() {
        pickSuggestionManually("");
    }

    public void pickSuggestionManually(String text) {
        this.mComposing.setLength(0);
        this.mComposing.append(text);
        commitTyped(getCurrentInputConnection());
    }

    public void swipeRight() {
        if (this.mCompletionOn) {
            pickDefaultCandidate();
        }
    }

    public void swipeLeft() {
        handleBackspace();
    }

    public void swipeDown() {
        handleClose();
    }

    public void swipeUp() {
    }

    public void onPress(int primaryCode) {
        this.mInputView.setPreviewEnabled(PROCESS_HARD_KEYS);
        if (primaryCode == -1 || primaryCode == -5 || primaryCode == -2 || primaryCode == -10000 || primaryCode == -101 || primaryCode == 32) {
            this.mInputView.setPreviewEnabled(false);
        }
    }

    public void onRelease(int primaryCode) {
    }

    public void showEmoticons() {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.popupWindow = new EmojiconsPopup(layoutInflater.inflate(R.layout.keyboard_emoji_listview_layout, null), this);
            this.popupWindow.setSizeForSoftKeyboard();
            this.popupWindow.setSize(-1, this.mInputView.getHeight());
            this.popupWindow.showAtLocation(this.mInputView.getRootView(), 80, 0, 0);
            this.popupWindow.setOnSoftKeyboardOpenCloseListener(new C05265());
            this.popupWindow.setOnEmojiconClickedListener(new C05276());
            this.popupWindow.setOnEmojiconBackspaceClickedListener(new C05287());
        }
    }

    public void closeEmoticons() {
        if (this.popupWindow != null) {
            this.popupWindow.dismiss();
        }
    }

    public void clearCandidateView() {
        if (this.list != null) {
            this.list.clear();
        }
    }

    private void vibratePhone() {
        try {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(23);
        } catch (Exception e) {
            Log.d("SimpleIME", e.toString());
        }
    }

    public void onWindowShown() {
        super.onWindowShown();
    }
    
    public void onWindowHidden() {
        super.onWindowHidden();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 1) {
            onWindowShown();
        } else {
            onWindowShown();
        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? false : PROCESS_HARD_KEYS;
    }
}
