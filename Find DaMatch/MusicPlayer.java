package cmpt276.project;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {
    private static MusicPlayer instance;
    MediaPlayer mediaPlayer = new MediaPlayer();
    int player = 0;
    int cardsSaved = 0;

    public int getCardsSaved() {
        return cardsSaved;
    }

    public void setCardsSaved(int cardsSaved) {
        this.cardsSaved = cardsSaved;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public MusicPlayer() {
    }

    public void start(int music, Context context){
        if(mediaPlayer != null){
        mediaPlayer = MediaPlayer.create(context,music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();}
    }

    public void pause(){
        if(mediaPlayer != null)
            mediaPlayer.pause();
    }

    public void stop(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static MusicPlayer getInstance(){
        if(instance == null)
            instance = new MusicPlayer();
        return  instance;
    }

    public boolean isPlaying() {
        return (mediaPlayer != null);
    }
}
