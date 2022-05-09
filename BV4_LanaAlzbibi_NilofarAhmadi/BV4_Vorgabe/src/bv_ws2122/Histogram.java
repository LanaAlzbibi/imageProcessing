// BV Ue4 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-23

package bv_ws2122;

import java.util.Arrays;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Histogram {

	private static final int grayLevels = 256;

	private GraphicsContext gc;
	private int maxHeight;
	private int size;
	private int[] histogram = new int[grayLevels];

	public Histogram() {
	}

	public Histogram(GraphicsContext gc, int maxHeight) {
		this.gc = gc;
		this.maxHeight = maxHeight;
	}

	public int[] getValues() {
		return histogram;
	}

	public void setImageRegion(RasterImage image, int regionStartX, int regionStartY, int regionWidth,
							   int regionHeight) {
		Arrays.fill(histogram, 0);
		size = image.width * image.height;
		for (int y = regionStartY; y < regionStartY + regionHeight; y++) {
			for (int x = regionStartX; x < regionStartX + regionWidth; x++) {
				int pos = y * image.width + x;

				int r = (image.argb[pos] >> 16) & 0xff;

				histogram[r] += 1;

			}
		}
		// TODO: calculate histogram[] out of the gray values found the given image
		// region

	}

	// UEBUNG 5
	public Integer getMaximum() {

		int vergleichIndex=0;
		// Will be used in Exercise 5.
		for (int x = 0; x < grayLevels; x++) {
			if (histogram[x]!=0) {
				vergleichIndex=x;
			}
		}

		return vergleichIndex;

	}

	public Integer getMinimum() {
		int vergleichWert =0;
		// Will be used in Exercise 5.
		for (int x = 1; x < grayLevels; x++) {
			if (histogram[x]!=0) {
				vergleichWert=x;
				break;
			}
		}
		return vergleichWert;
	}

	public Double getMean() {

		int temp[] = histogram;
		double sum = 0;
		for (int x = 0; x < temp.length; x++) {
			sum += (double) temp[x] * x;
		}
		sum = sum / size;
		return sum;
	}

	public double getMedian() {
		// Will be used in Exercise 5.
		int temp[] = histogram;

		int med = 0;

		int mitte = 0;
		int sum = 0;
		int forMitte = 0;
		int y = 0;
		if (size % 2 != 0) {
			mitte = (size - 1) / 2;
			for (int x = 0; x < temp.length; x++) {
				sum += temp[x];
				if (mitte > sum) {
					med = x;
					System.out.println("med found an un-even array " + med);
					return med;
				}

			}

		} else {
			mitte = size / 2;
			for (int x = 0; x < temp.length; x++) {

				sum += temp[x];

				if (mitte == sum) {
					forMitte = x;
					y = 1;
				}
				if (mitte < sum) {
					if (y != 0)
						med = (forMitte + x) / 2;
					else
						med = x;
					System.out.println("med found from an even array " + med);
					return med;
				}
			}



		}

		return med;
	}

	public Double getVariance() {
		// Will be used in Exercise 5.
		int temp[] = histogram;
		double[] p = new double[temp.length + 1];
		double v = 0;
		double sum = 0, sumP = 0;
		double m = 0, y = 0;
		for (int x = 0; x < temp.length; x++) {
			sum += (double) temp[x] * x;
		}
		m = sum / size;

		for (int x = 0; x < temp.length; x++) {
			p[x] = (double) temp[x] / size;
			y = x - m;
			y = Math.pow(y, 2);
			sumP += p[x] * y;
		}

		return sumP;
	}

	public Double getEntropy() {
		// Will be used in Exercise 5.
		int temp[] = histogram;
		double[] p = new double[temp.length + 2];
		double sum = 0;
		for (int x = 0; x < temp.length; x++) {
			p[x] = (double) temp[x] / size;
			if (p[x] != 0)
				sum += p[x] * (Math.log(p[x]) / Math.log(2));
		}

		sum = -sum;
		return sum;
	}

	public void draw(Color lineColor) {
		if (gc == null)
			return;
		int maxValue = histogram[0];
		for (int i = 1; i < 256; i++) {
			if (histogram[i] > maxValue) {
				maxValue = histogram[i];
			}
		}

		double shift = 0.5;
		double greyHäufigkeit = 0;
		gc.clearRect(0, 0, grayLevels, maxHeight);
		gc.setStroke(lineColor);
		gc.setLineWidth(1);

		System.out.println("Maximum Value is: " + maxValue);
		for (int j = 0; j < 256; j++) {
			greyHäufigkeit = (double) ((histogram[j] / (float) maxValue) * maxHeight);
			gc.strokeLine(j, maxHeight - 0, j + shift, maxHeight - greyHäufigkeit + shift);

		}

		// TODO: draw histogram[] into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel
		// centers

		// Remark: This is some dummy code to give you an idea for line drawing
		// gc.strokeLine(shift, shift, grayLevels-1 + shift, maxHeight-1 + shift);
		// gc.strokeLine(grayLevels-1 + shift, shift, shift, maxHeight-1 + shift);

	}

}
