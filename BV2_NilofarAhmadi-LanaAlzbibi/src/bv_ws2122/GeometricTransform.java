// BV Ue2 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-22
 		   		     	

package bv_ws2122;


public class GeometricTransform {
 		   		     	
	public enum InterpolationType { 
		NEAREST("Nearest Neighbour"), 
		BILINEAR("Bilinear");
		
		private final String name;       
	    private InterpolationType(String s) { name = s; }
	    public String toString() { return this.name; }
	};
	
	public void perspective(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion, InterpolationType interpolation) {
		switch(interpolation) {
		case NEAREST:
			perspectiveNearestNeighbour(src, dst, angle, perspectiveDistortion);
			break;
		case BILINEAR:
			perspectiveBilinear(src, dst, angle, perspectiveDistortion);
			break;
		default:
			break;	
		}
		
	}
 		   		     	
	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	public void perspectiveNearestNeighbour(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {
 		   		     	
		// TODO: implement the geometric transformation using nearest neighbour image rendering
		
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant

		//Das Ausgangsbild soll mit dem eingestellten Winkel um die x-Achse gekippt werden.
		// Als Interpolationsmethode soll die einfache Pixelwiederholung verwendet werden. Die Hintergrundfarbe soll weiß sein.

		//bild

        for(int y=0; y< dst.height;y++){
        	for(int x=0; x< dst.width;x++){
        		//position
				int pos = y* dst.width+x;

				//erste Berechnung

				double xd = x - (dst.width/2);
				double yd = y - (dst.height/2);

				double rad = Math.toRadians(angle);
				double s = perspectiveDistortion;

				//zweite Berechnung
				//kippen-formular
				double yys = yd / (Math.cos(rad) - s * Math.sin(rad) * yd);
				double xxs = xd * (s * Math.sin(rad) * yys + 1);

				//dritte Berechnung

				double ys = (int)yys + (src.height/2);
				double xs =(int) xxs + (src.width/2);




				int posS = (int) Math.round(ys * src.width + xs);
				//farbeLesen

				if(ys<0){
					dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
				}
				if(xs<0){
					dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
				}
				if(ys> src.height){
					dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
				}
				if(xs> src.width){
					dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
				}
				else {
					try {
						dst.argb[pos] = src.argb[posS];
					} catch (ArrayIndexOutOfBoundsException e) {

					}
				}



			}
		}
		
	}


	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	public void perspectiveBilinear(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {

		// TODO: implement the geometric transformation using bilinear interpolation

		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant

		for(int y=0; y< dst.height;y++){
			for(int x=0; x< dst.width;x++){
				//position
				int pos = y* dst.width+x;

				//erste Berechnung

				double xd = x - (dst.width/2);
				double yd = y - (dst.height/2);

				double rad = Math.toRadians(angle);
				double s = perspectiveDistortion;

				//zweite Berechnung
				//kippen-formular
				double yys = yd / (Math.cos(rad) - s * Math.sin(rad) * yd);
				double xxs = xd * (s * Math.sin(rad) * yys + 1);

				//dritte Berechnung

				double ys = yys + (src.height/2);
				double xs = xxs + (src.width/2);


				int ysl =(int) Math.floor(ys);
				int xsl = (int) Math.floor(xs);


				//Abstand
				double h=xs-xsl;
				double v=ys-ysl;

						//farbeLesen
						if(xsl<0) {

							dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
						}
						if(xsl>=src.width) {

							dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
						}

						if(ysl<0) {
							dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
						}
						if(ysl>=src.height) {

							dst.argb[pos]=(0xFF<<24) | (255<<16) | (255<<8) | 255;
						}
						else {
							try {
//								dst.argb[pos]+=src.argb[ya* src.width+xa]*a[posY];

//								dst.argb[pos]=(int) (src.argb[ya* src.width+xa]+(src.argb[0](1-v)(1-h)+src.argb[1]*(1-v)*h
//										+src.argb[2]v(1-h)+src.argb[3]*v*h));
								//punkt A
								int rA = (src.argb[(int) (ysl* src.width +xsl)] >> 16) & 0xff;
								int gA = (src.argb[(int) (ysl* src.width+xsl)] >> 8) & 0xff;
								int bA = src.argb[(int) (ysl* src.width+xsl)] & 0xff;

								//punkt B
								int rB = (src.argb[(int) (ysl* src.width+xsl+1)] >> 16) & 0xff;
								int gB = (src.argb[(int) (ysl* src.width+xsl+1)] >> 8) & 0xff;
								int bB = src.argb[(int) (ysl* src.width+xsl+1)] & 0xff;

								//punkt C
								int rC = (src.argb[(int) ((ysl+1)* src.width+xsl)] >> 16) & 0xff;
								int gC = (src.argb[(int) ((ysl+1)* src.width+xsl)] >> 8) & 0xff;
								int bC = src.argb[(int) ((ysl+1)* src.width+xsl)] & 0xff;

								//punkt D
								int rD = (src.argb[(int) ((ysl+1)* src.width+xsl+1)] >> 16) & 0xff;
								int gD = (src.argb[(int) ((ysl+1)* src.width+xsl+1)] >> 8) & 0xff;
								int bD = src.argb[(int) ( (ysl+1)* src.width+xsl+1)] & 0xff;

								int pR= (int) (rA*(1-h)*(1-v)+rB*h*(1-v)+rC*(1-h)*v+rD*h*v);
								int pG=(int) (gA*(1-h)*(1-v)+gB*h*(1-v)+gC*(1-h)*v+gD*h*v);
								int pB=(int) (bA*(1-h)*(1-v)+bB*h*(1-v)+bC*(1-h)*v+bD*h*v);
								dst.argb[pos]=(0xFF<<24) | (pR<<16) | (pG<<8) | pB;
//								dst.argb[pos]=src.argb[pB];
//								dst.argb[pos]=src.argb[pG];

							} catch (ArrayIndexOutOfBoundsException e) {

							}

						}


		}

	}
	}

}