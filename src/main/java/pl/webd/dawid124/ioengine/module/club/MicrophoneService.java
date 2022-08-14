package pl.webd.dawid124.ioengine.module.club;

import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

@Service
public class MicrophoneService {



    public static void main(String[] args) throws LineUnavailableException {
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
        TargetDataLine microphone2 = getTargetDataLineForRecord(format);


        SourceDataLine speakers;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();

            int bytesRead = 0;
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();





            byte [] buffer = new byte[2000];
            while (true) {
                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                out.write(data, 0, numBytesRead);
                short max;
                if (numBytesRead >=0) {
                    max = (short) (buffer[0] + (buffer[1] << 8));
                    for (int p=2;p<bytesRead-1;p+=2) {
                        short thisValue = (short) (buffer[p] + (buffer[p+1] << 8));
                        if (thisValue>max) max=thisValue;
                    }
                    System.out.println("Max value is "+max);
                }

            }

//
//            while (bytesRead < 100000) {
//                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
//                bytesRead += numBytesRead;
//                // write the mic data to a stream for use later
//                out.write(data, 0, numBytesRead);
//                // write mic data to stream for immediate playback
//                speakers.write(data, 0, numBytesRead);
//            }
//            speakers.drain();
//            speakers.close();
//            microphone.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static TargetDataLine getTargetDataLineForRecord(AudioFormat format) throws LineUnavailableException {
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            return null;
        }
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format, line.getBufferSize());
        return line;
    }
}
