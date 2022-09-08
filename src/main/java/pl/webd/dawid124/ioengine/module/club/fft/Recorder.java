package pl.webd.dawid124.ioengine.module.club.fft;

import javax.sound.sampled.*;

import static pl.webd.dawid124.ioengine.module.club.fft.Visualizer.BAR_NUM;

public class Recorder {


    static final int BUFFER_SIZE = 1024 * 2;
    private TargetDataLine audioLine;
    private AudioFormat format;

    public static volatile boolean running;
    public static volatile BitValue[] values = new BitValue[BAR_NUM];


    public Recorder() {
        System.out.println("Recorder ---- created");
        for (int i = 0; i < BAR_NUM; i++) {
            values[i] = new BitValue();
        }
    }

    AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public TargetDataLine getMicrophonLine() throws LineUnavailableException {
        Mixer.Info[] mi = AudioSystem.getMixerInfo();

        for (Mixer.Info info : mi) {
            if (info.getDescription().equals("Direct Audio Device: Creative Live! Cam Sync 1080p V, USB Audio, USB Audio")) {
                Mixer m = AudioSystem.getMixer(info);
                Line.Info[] sl = m.getTargetLineInfo();
                for (Line.Info info2 : sl) {
                    Line line = AudioSystem.getLine(info2);
                    if (line instanceof TargetDataLine) {
                        return (TargetDataLine) line;
                    }
                }
            }
        }



//        Mixer mixer = AudioSystem.getMixer(AudioSystem.getMixerInfo()[5]);
//        Line.Info[] lineInfos = mixer.getTargetLineInfo();
//        assert lineInfos.length > 0 : "Strange, there are no more source lines for mixer: " + mixer;
//        Line.Info lineInfo = lineInfos[0];
//        TargetDataLine line = null;
//        try {
//            line = (TargetDataLine) mixer.getLine(lineInfo);
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
        return AudioSystem.getTargetDataLine(format);
    }

    public void start() {
        try {
            format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                throw new LineUnavailableException("The system does not support the specified format.");
            }

//            audioLine = AudioSystem.getTargetDataLine(format);
            audioLine = getMicrophonLine();
            audioLine.open(format);
            audioLine.start();

            running = true;

            final int normalBytes = Player.normalBytesFromBits(format.getSampleSizeInBits());

            float[] samples = new float[BUFFER_SIZE * format.getChannels()];
            long[] transfer = new long[samples.length];
            byte[] bytes = new byte[samples.length * normalBytes];

            int bread = BUFFER_SIZE * format.getChannels();

            while (running) {
                audioLine.read(bytes, 0, bytes.length);
                samples = Player.unpack(bytes, transfer, samples, bread, format);
                samples = Player.hamming(samples, bread, format);
                Visualizer.drawSpectrum2(samples, values);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            running = false;
            audioLine.flush();
            audioLine.drain();
            audioLine.close();
            audioLine = null;
            System.out.println("STOPPED");
        }
    }

    public void stop() {
        running = false;
        if (audioLine != null) {
            audioLine.flush();
            audioLine.drain();
            audioLine.close();
            audioLine = null;
        }
    }
}

