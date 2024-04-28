package com.example.myapplication;

import android.Manifest;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;
import java.util.HashMap;
import android.net.Uri;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private SpeechRecognizer recognizer;
    private TextView tvResults;
    private TextToSpeech tts;
    private int RECORD_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setTitle("Working page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonRequest = findViewById(R.id.button2);
        buttonRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity2.this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity2.this, "You have already granted this permission!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestAudioPermission();
                }
            }
        });

        find_id();
        texttospeech();
        initialize_values();

    }

    private void requestAudioPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because Iris needs to hear you")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity2.this,
                                    new String[] {Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RECORD_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void texttospeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (tts.getEngines().size() == 0) {
                    Toast.makeText(MainActivity2.this, "Engine is not available", Toast.LENGTH_SHORT).show();
                }
                else{
                    speak("Welcome back boss");
                }
            }
        });
    }

    private void speak(String msg) {
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
    }


    private void find_id() {
        tvResults = (TextView)findViewById(R.id.textView);
    }

    private void initialize_values() {
        if (SpeechRecognizer.isRecognitionAvailable(this)){
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {

                    ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Toast.makeText(MainActivity2.this, "" + results.get(0), Toast.LENGTH_SHORT).show();
                    tvResults.setText(results.get(0));
                    response(results.get(0));

                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
        }
    }

    private void response(String msg) {

        Map<String, String> appPackageMap = new HashMap<>();
// Hash maps for apps that needs to be opened
        appPackageMap.put("whatsapp", "com.whatsapp");
        appPackageMap.put("snapchat", "com.snapchat.android");
        appPackageMap.put("youtube", "com.google.android.youtube");
        appPackageMap.put("twitter", "com.twitter.android");
        appPackageMap.put("facebook", "com.facebook.katana");
        appPackageMap.put("gmail", "com.google.android.gm");
        appPackageMap.put("google maps", "com.google.android.apps.maps");
        appPackageMap.put("linkedin", "com.linkedin.android");
        appPackageMap.put("spotify", "com.spotify.music");
        appPackageMap.put("instagram", "com.instagram.android");
        appPackageMap.put("telegram", "org.telegram.messenger");
        appPackageMap.put("reddit", "com.reddit.frontpage");
        appPackageMap.put("amazon", "in.amazon.mShop.android.shopping");
        appPackageMap.put("uber", "com.ubercab");
        appPackageMap.put("pinterest", "com.pinterest");
        appPackageMap.put("messenger", "com.facebook.orca");
// apps that need to be opened

// hashmaps for websites that needs to be opened
        Map<String, String> commandToUrlMap = new HashMap<>();
        commandToUrlMap.put("sap portal", "https://kiit.ac.in/sap/");
        commandToUrlMap.put("speed test", "https://www.speedtest.net/");

// websites that need to be opened

        String msgs = msg.toLowerCase();
        String userInput = msgs.toLowerCase();

        if (msgs.contains("hi")){
            speak("hello boss how are you");
        }
        else if (msgs.contains("how are you")){
            speak("i am fine boss how are you");
        }
        else if (msgs.contains("hello")){
            speak("hello boss");
        }
        else if (msgs.contains("my") && msgs.contains("name")){
            speak("it's Sankhya Patra");
        }
//        exit code
        else if(msgs.contains("exit")){
            speak("Bye boss");
            finish();
        }
//for opening apps
        for (Map.Entry<String, String> entry : appPackageMap.entrySet()) {
            String appName = entry.getKey();
            String packageName = entry.getValue();

            if (userInput.contains("open " + appName)) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(MainActivity2.this, "App not found", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
//for opening websites
        for (Map.Entry<String, String> entry : commandToUrlMap.entrySet()) {
            String command = entry.getKey();
            String urlToOpen = entry.getValue();
            if (userInput.contains(command)) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, urlToOpen);
                startActivity(intent);
                break;
            }
        }
    }

    public void startRecording(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        recognizer.startListening(intent);
    }
}