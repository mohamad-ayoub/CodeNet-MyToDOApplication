package com.example.mytodoapplication;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class MySoundPool {
    static private final int NUMBER_OF_SIMULTANEOUS_SOUNDS = 5;
    static private final float LEFT_VOLUME_VALUE = 1.0f;
    static private final float RIGHT_VOLUME_VALUE = 1.0f;
    static private final int MUSIC_LOOP = 0;
    static private final int SOUND_PLAY_PRIORITY = 0;
    static private final float PLAY_RATE = 1.0f;

    static Context context;
    static SoundPool pool;
    static int addClickCode=-1, deleteClickCode=-1;

    public MySoundPool(Context context) {
        create(context);
           }
     public static  void playAddSound(Context context) {
        if (addClickCode==-1) {
            create(context);
        }
        pool.play(addClickCode, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE, SOUND_PLAY_PRIORITY, MUSIC_LOOP, PLAY_RATE);
    }

    public static void playDeleteSound(Context context) {
         if (deleteClickCode==-1) {
             create(context);
         }
        pool.play(deleteClickCode, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE, SOUND_PLAY_PRIORITY, MUSIC_LOOP, PLAY_RATE);
    }

    static private void create(Context context) {
        MySoundPool.context=context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            pool = new SoundPool.Builder()
                    .setMaxStreams(NUMBER_OF_SIMULTANEOUS_SOUNDS)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            pool = new SoundPool(NUMBER_OF_SIMULTANEOUS_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        }

        addClickCode = pool.load(MySoundPool.context, R.raw.snd_click, SOUND_PLAY_PRIORITY);
        deleteClickCode=pool.load(MySoundPool.context, R.raw.bell, SOUND_PLAY_PRIORITY);
    }
}
