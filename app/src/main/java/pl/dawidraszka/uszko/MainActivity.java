package pl.dawidraszka.uszko;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.dawidraszka.uszko.R;
import pl.dawidraszka.uszko.adapters.MessageAdapter;
import pl.dawidraszka.uszko.beans.Message;
import pl.dawidraszka.uszko.dialogs.PassPhoneDialog;
import pl.dawidraszka.uszko.utils.DragShadowBuilder;
import pl.dawidraszka.uszko.utils.NavigationDragEventListener;

public class MainActivity extends Activity implements PassPhoneDialog.PassPhoneDialogListener {

    private ImageView quickResponseImageView;
    private TextView quickResponseTextView1;
    private TextView quickResponseTextView2;
    private TextView quickResponseTextView3;

    private RecyclerView messagesRecyclerView;
    private MessageAdapter messageAdapter;

    private ImageView shadowImageView;

    private ImageButton sendButton;
    private EditText messageEditText;
    private ImageButton passPhoneButton;

    private TextToSpeech textToSpeech;
    private final List<Message> messages = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quickResponseImageView = findViewById(R.id.quickResponseImageView);
        quickResponseTextView1 = findViewById(R.id.quickResponseTextView1);
        quickResponseTextView2 = findViewById(R.id.quickResponseTextView2);
        quickResponseTextView3 = findViewById(R.id.quickResponseTextView3);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

        shadowImageView = findViewById(R.id.shadowImageView);

        sendButton = findViewById(R.id.sendButton);
        messageEditText = findViewById(R.id.messageEditText);
        passPhoneButton = findViewById(R.id.passPhoneButton);

        quickResponseImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Instantiates the drag shadow builder.
                View.DragShadowBuilder dragShadowBuilder = new DragShadowBuilder(quickResponseImageView);

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(quickResponseImageView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                messageEditText.clearFocus();

                // Starts the drag
                v.startDrag(null,  // the data to be dragged
                        dragShadowBuilder,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );
                return false;
            }
        });

        NavigationDragEventListener navigationDragEventListener = new NavigationDragEventListener(this, shadowImageView);

        quickResponseTextView1.setOnDragListener(navigationDragEventListener);
        quickResponseTextView2.setOnDragListener(navigationDragEventListener);
        quickResponseTextView3.setOnDragListener(navigationDragEventListener);

        messageAdapter = new MessageAdapter(this, messages);
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();

                sendMessage(message, true);
                messageEditText.setText("");
            }
        });

        passPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassPhoneDialog passPhoneDialog = new PassPhoneDialog();
                passPhoneDialog.show(getFragmentManager(), "PassPhoneDialog");
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String messageString) {
        sendMessage(messageString, false);
    }

    public void sendMessage(String messageString, boolean isOwner) {

        if(messageString.equals("")){
            return;
        }

        Message message = new Message(messageString, isOwner);
        messages.add(message);
        messageAdapter.notifyDataSetChanged();
        messagesRecyclerView.scrollToPosition(messagesRecyclerView.getAdapter().getItemCount() - 1);

        if(isOwner){
            textToSpeech.speak(messageString, TextToSpeech.QUEUE_ADD, null, null);
        }
    }
}