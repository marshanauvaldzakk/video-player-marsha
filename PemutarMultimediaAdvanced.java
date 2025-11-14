import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class PemutarMultimediaAdvanced extends Application {
    private MediaPlayer backgroundAudioPlayer;
    private MediaPlayer videoPlayer;
    private MediaView mediaView;
    private Label status;
    
    @Override
    public void start(Stage stage) {
        Label title = new Label("PEMUTAR MULTIMEDIA - KONTROL TERPISAH");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // ==================== TOMBOL BACKGROUND AUDIO ====================
        Label audioTitle = new Label("BACKGROUND MUSIC");
        audioTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #9b59b6;");
        
        Button playAudioBtn = new Button("Putar Background Music");
        Button stopAudioBtn = new Button("Hentikan Background Music");
        Button pauseAudioBtn = new Button("Jeda Background Music");
        
        // Volume slider untuk background audio
        Label volumeLabel = new Label("Volume Background Music:");
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        
        // ==================== TOMBOL VIDEO ====================
        Label videoTitle = new Label("VIDEO PLAYER");
        videoTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        
        Button playVideoBtn = new Button("Putar Video");
        Button stopVideoBtn = new Button("Hentikan Video");
        Button pauseVideoBtn = new Button("Jeda Video");
        Button videoNoAudioBtn = new Button("Video Tanpa Suara");
        Button videoWithAudioBtn = new Button("Video Dengan Suara");
        
        // ==================== TOMBOL KONTROL SEMUA ====================
        Label controlTitle = new Label("KONTROL SEMUA");
        controlTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        
        Button playAllBtn = new Button("Putar Semua");
        Button stopAllBtn = new Button("Hentikan Semua");
        Button pauseAllBtn = new Button("Jeda Semua");
        
        // Status
        status = new Label("Status: Aplikasi siap digunakan");
        status.setStyle("-fx-font-size: 14px; -fx-text-fill: #27ae60;");
        
        // Media View untuk video
        mediaView = new MediaView();
        mediaView.setFitWidth(500);
        mediaView.setFitHeight(300);
        mediaView.setStyle("-fx-border-color: #3498db; -fx-border-width: 2px;");
        
        // ==================== AKSI TOMBOL BACKGROUND AUDIO ====================
        playAudioBtn.setOnAction(e -> playBackgroundAudio());
        stopAudioBtn.setOnAction(e -> stopBackgroundAudio());
        pauseAudioBtn.setOnAction(e -> pauseBackgroundAudio());
        
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (backgroundAudioPlayer != null) {
                backgroundAudioPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });
        
        // ==================== AKSI TOMBOL VIDEO ====================
        playVideoBtn.setOnAction(e -> playVideoWithAudio());
        stopVideoBtn.setOnAction(e -> stopVideo());
        pauseVideoBtn.setOnAction(e -> pauseVideo());
        videoNoAudioBtn.setOnAction(e -> playVideoWithoutAudio());
        videoWithAudioBtn.setOnAction(e -> playVideoWithAudio());
        
        // ==================== AKSI TOMBOL KONTROL SEMUA ====================
        playAllBtn.setOnAction(e -> playAllMedia());
        stopAllBtn.setOnAction(e -> stopAllMedia());
        pauseAllBtn.setOnAction(e -> pauseAllMedia());
        
        // ==================== STYLING TOMBOL ====================
        playAudioBtn.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px;");
        stopAudioBtn.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-padding: 8px;");
        pauseAudioBtn.setStyle("-fx-background-color: #7d3c98; -fx-text-fill: white; -fx-padding: 8px;");
        
        playVideoBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px;");
        stopVideoBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 8px;");
        pauseVideoBtn.setStyle("-fx-background-color: #2471a3; -fx-text-fill: white; -fx-padding: 8px;");
        videoNoAudioBtn.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-padding: 8px;");
        videoWithAudioBtn.setStyle("-fx-background-color: #d35400; -fx-text-fill: white; -fx-padding: 8px;");
        
        playAllBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
        stopAllBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
        pauseAllBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
        
        // ==================== LAYOUT ====================
        // Background Audio Section
        VBox audioControlBox = new VBox(10, 
            audioTitle,
            new HBox(10, playAudioBtn, pauseAudioBtn, stopAudioBtn),
            volumeLabel,
            volumeSlider
        );
        audioControlBox.setStyle("-fx-padding: 15; -fx-border-color: #9b59b6; -fx-border-width: 2px; -fx-background-color: #f4ecf7;");
        
        // Video Control Section
        VBox videoControlBox = new VBox(10, 
            videoTitle,
            new HBox(10, playVideoBtn, pauseVideoBtn, stopVideoBtn),
            new HBox(10, videoNoAudioBtn, videoWithAudioBtn)
        );
        videoControlBox.setStyle("-fx-padding: 15; -fx-border-color: #3498db; -fx-border-width: 2px; -fx-background-color: #ebf5fb;");
        
        // Global Control Section
        VBox globalControlBox = new VBox(10, 
            controlTitle,
            new HBox(10, playAllBtn, pauseAllBtn, stopAllBtn)
        );
        globalControlBox.setStyle("-fx-padding: 15; -fx-border-color: #e74c3c; -fx-border-width: 2px; -fx-background-color: #fdedec;");
        
        // Media Display Section
        VBox mediaBox = new VBox(10, 
            new Label("TAMPILAN VIDEO:"),
            mediaView
        );
        mediaBox.setStyle("-fx-padding: 15; -fx-border-color: #bdc3c7; -fx-border-width: 1px;");
        
        // Main Layout
        VBox root = new VBox(15, 
            title, 
            audioControlBox, 
            videoControlBox, 
            globalControlBox,
            mediaBox, 
            status
        );
        root.setStyle("-fx-padding: 20; -fx-background-color: #ffffff;");
        
        Scene scene = new Scene(root, 650, 900);
        stage.setTitle("Pemutar Multimedia - Kontrol Terpisah");
        stage.setScene(scene);
        stage.show();
    }
    
    // ==================== BACKGROUND AUDIO METHODS ====================
    private void playBackgroundAudio() {
        try {
            if (backgroundAudioPlayer != null) {
                backgroundAudioPlayer.stop();
            }
            
            String audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
            Media audioMedia = new Media(audioUrl);
            backgroundAudioPlayer = new MediaPlayer(audioMedia);
            backgroundAudioPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundAudioPlayer.setVolume(0.5);
            backgroundAudioPlayer.play();
            
            status.setText("Status: Background music diputar");
            status.setStyle("-fx-text-fill: #9b59b6;");
            System.out.println("BACKGROUND AUDIO: Musik diputar");
            
        } catch (Exception e) {
            status.setText("Status: Error memutar background music");
            status.setStyle("-fx-text-fill: #e74c3c;");
            System.out.println("Error background audio: " + e.getMessage());
        }
    }
    
    private void stopBackgroundAudio() {
        if (backgroundAudioPlayer != null) {
            backgroundAudioPlayer.stop();
            status.setText("Status: Background music dihentikan");
            status.setStyle("-fx-text-fill: #e74c3c;");
            System.out.println("BACKGROUND AUDIO: Dihentikan");
        }
    }
    
    private void pauseBackgroundAudio() {
        if (backgroundAudioPlayer != null) {
            backgroundAudioPlayer.pause();
            status.setText("Status: Background music dijeda");
            status.setStyle("-fx-text-fill: #f39c12;");
            System.out.println("BACKGROUND AUDIO: Dijeda");
        }
    }
    
    // ==================== VIDEO METHODS ====================
    private void playVideoWithAudio() {
        playVideo(true);
    }
    
    private void playVideoWithoutAudio() {
        playVideo(false);
    }
    
    private void playVideo(boolean withAudio) {
        try {
            if (videoPlayer != null) {
                videoPlayer.stop();
            }
            
            String videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
            Media videoMedia = new Media(videoUrl);
            videoPlayer = new MediaPlayer(videoMedia);
            mediaView.setMediaPlayer(videoPlayer);
            
            if (!withAudio) {
                videoPlayer.setVolume(0.0); // Mute video audio
            } else {
                videoPlayer.setVolume(1.0); // Full volume video audio
            }
            
            videoPlayer.play();
            
            String audioStatus = withAudio ? "dengan suara" : "tanpa suara";
            status.setText("Status: Video diputar " + audioStatus);
            status.setStyle("-fx-text-fill: #3498db;");
            System.out.println("VIDEO: Diputar " + audioStatus);
            
            videoPlayer.setOnEndOfMedia(() -> {
                status.setText("Status: Video selesai diputar");
                status.setStyle("-fx-text-fill: #27ae60;");
            });
            
        } catch (Exception e) {
            status.setText("Status: Error memutar video");
            status.setStyle("-fx-text-fill: #e74c3c;");
            System.out.println("Error video: " + e.getMessage());
        }
    }
    
    private void stopVideo() {
        if (videoPlayer != null) {
            videoPlayer.stop();
            status.setText("Status: Video dihentikan");
            status.setStyle("-fx-text-fill: #e74c3c;");
            System.out.println("VIDEO: Dihentikan");
        }
    }
    
    private void pauseVideo() {
        if (videoPlayer != null) {
            videoPlayer.pause();
            status.setText("Status: Video dijeda");
            status.setStyle("-fx-text-fill: #f39c12;");
            System.out.println("VIDEO: Dijeda");
        }
    }
    
    // ==================== GLOBAL CONTROL METHODS ====================
    private void playAllMedia() {
        playBackgroundAudio();
        playVideoWithAudio();
        status.setText("Status: Semua media diputar");
        status.setStyle("-fx-text-fill: #27ae60;");
    }
    
    private void stopAllMedia() {
        stopBackgroundAudio();
        stopVideo();
        status.setText("Status: Semua media dihentikan");
        status.setStyle("-fx-text-fill: #e74c3c;");
    }
    
    private void pauseAllMedia() {
        if (backgroundAudioPlayer != null) {
            backgroundAudioPlayer.pause();
        }
        if (videoPlayer != null) {
            videoPlayer.pause();
        }
        status.setText("Status: Semua media dijeda");
        status.setStyle("-fx-text-fill: #f39c12;");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}