package com.centipede;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound extends Thread{

    volatile boolean shoot = false;
    private boolean mute = false;
    private boolean exit = false;
    private Clip shotClip;
    private Clip musicClip;
    private AudioInputStream audioin;

    @Override
    public void run(){
        try {
            audioin = AudioSystem.getAudioInputStream(new java.io.File("src/audio/centipede/shot.wav"));
            shotClip = AudioSystem.getClip();
            shotClip.open(audioin);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            audioin = AudioSystem.getAudioInputStream(new java.io.File("src/audio/centipede/music.wav"));
            musicClip = AudioSystem.getClip();
            musicClip.open(audioin);
        } catch (Exception e){
            e.printStackTrace();
        }

        musicClip.loop(Clip.LOOP_CONTINUOUSLY);

        while(!exit){
            if (shoot && !mute) {
                if (shotClip.isRunning()) {
                    shotClip.stop();   // Stop the player if it is still running
                }
                shotClip.setFramePosition(0); // rewind to the beginning
                shotClip.start();     // Start playing
                shoot = false;
            }
        }

        musicClip.stop();
    }

    public void shoot(){
        shoot = true;
    }
    public void mute(){
        mute = !mute;
        if(musicClip.isRunning()){
            musicClip.stop();
        }else{
            musicClip.start();
        }
    }
    public void stopMusic(){
        exit = true;
    }
}
