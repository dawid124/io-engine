package pl.webd.dawid124.ioengine.module.club.fft;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Visualizer {

	private static Visualizer instance;
	
	public static final int BAR_NUM = 7;

//	private static Rectangle[] rec, peaks;
	
	private static double[] buffer = new double[BAR_NUM];
	private static double[] bufferDecrease = new double[BAR_NUM];
	private static double[] magnitudes = new double[BAR_NUM];
	
	private static double frequencyBins[];
//	public static double frequencyBins[] = {0.0, 19.0, 46.0, 89.0, 155.0, 257.0, 413.0, 653.0, 1024.0};


	public static Visualizer get(){
		if (instance == null)
			new Visualizer();
		return instance;
	}

	private Visualizer(){
		calculateBins();
	}

	//constructing frequency bins
	private void calculateBins(){
		double maxFreq = 22050;
		double time = (Recorder.BUFFER_SIZE/2)/maxFreq;
		double minFreq = 1/time;
		
		frequencyBins = new double[BAR_NUM + 2];
		frequencyBins[0] = minFreq;
		frequencyBins[frequencyBins.length-1] = maxFreq;
		
		minFreq = melTransform(minFreq);
		maxFreq = melTransform(maxFreq);
		
		double amount = (maxFreq - minFreq)/(BAR_NUM + 1);
		
		/*
		Mel's scale is logarithmic so we can set the distances between
		frequencies to be linear, once we convert the values back 
		from Mel's scale we get logarithmic distances between frequencies 
		the distance increases as the frequencies increase
		which corresponds to how humans hear sound
		(we can detect fewer differences in higher frequencies) 
		*/
		
		for (int i = 1; i < frequencyBins.length-1; i++){
			frequencyBins[i] = iMelTransform(minFreq + i * amount);
		}
		
		//triangulation
		//frequencyBins[0] = 0;
		int index = 0;
		System.out.println(Arrays.toString(frequencyBins));
		for (int i = 1; i <= Recorder.BUFFER_SIZE/2; i++){
			double freq = i / time;
			if (freq >= frequencyBins[index]){
				frequencyBins[index++] = i-1;
			}
			if (index==(BAR_NUM+2)) break;
		}
		frequencyBins[frequencyBins.length-1] = Recorder.BUFFER_SIZE/2;
		System.out.println(Arrays.toString(frequencyBins));
	}
	
	private double melTransform(double freq){
		return 1125 * Math.log(1 + freq/(float)700);
	}
	
	private double iMelTransform(double freq){
		return 700 * (Math.pow(Math.E, freq/(float)1125) - 1);
	}

	static void drawSpectrum2(float samples[], BitValue[] bitValues){
		Complex data[] = new Complex[samples.length];
		for (int i = 0; i < samples.length; i++){
			data[i] = new Complex(samples[i], 0);
		}
		Complex niz[] = FFT.fft(data);

		for (int i = 0; i < magnitudes.length; i++){
			int startIndex = (int)frequencyBins[i];
			int endIndex = (int)frequencyBins[i+2];
			
			int amount = (endIndex - startIndex) / 2;
			int amountFull = endIndex - startIndex;
			
			double maxFreq =  0;
			
			for (int j = startIndex; j < endIndex; j++){

				double freq = 0;
				if (j <= startIndex + amount) {
					freq = (((j - (startIndex - 1)) * 1.0) / (amount + 1)) * (niz[j].re() * niz[j].re() + niz[j].im() * niz[j].im());
				}
				else {
					freq = (((amountFull - (j - startIndex)) * 1.0) / amount) * (niz[j].re() * niz[j].re() + niz[j].im() * niz[j].im());
				}
		
				if (freq > maxFreq) maxFreq = freq;
			}
			
			magnitudes[i] = Math.max(20 * Math.log10(maxFreq), 0);
			
			// default 0.5 & 1.8
			
			if (magnitudes[i] > buffer[i]){
				buffer[i] = magnitudes[i];
				bufferDecrease[i] = 0.9;
			}
			
			if (magnitudes[i] < buffer[i]){
				buffer[i] -= bufferDecrease[i];
				bufferDecrease[i] *= 1.3;
			}
			
		}

		for (int i = 0; i < buffer.length; i++) {
			bitValues[i].addValue(buffer[i]);
		}

//		System.out.println(Arrays.toString(buffer));
		double scale = 150/110.0;
//		double scale = 2;
//		for (int i = 0; i < magnitudes.length; i++){
//			rec[i].setHeight((int)(buffer[i] * scale));
//			rec[i].setY(149-rec[i].getHeight());
//			if (peaks[i].getY()>rec[i].getY()-1){
//				peaks[i].setY(rec[i].getY()-1);
//			}
//		}
	}

}
