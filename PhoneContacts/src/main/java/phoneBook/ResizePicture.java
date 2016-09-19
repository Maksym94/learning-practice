package phoneBook;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;




/**
 * edited by Maximka
 *This class is fully responsible for resize picture. There two ways to resize picture smart 
 *and with constant ratio. Smart means that picture will save proportions from the origin 
 *picture. With constant ratio means that we specify horizontal and vertical size of the 
 *picture in pixels and it will be the same for all pictures that we will bind with the 
 *contact. For convenience was chosen second way with the constant radio of the picture.
 */
public class ResizePicture extends JPanel{




		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private BufferedImage original;
		private BufferedImage scaled;

		
	

		/**
		 * This method builds the final path of the picture, resize picture and save it in 
		 * the resource folder with unique name of the picture, depending from the name
		 * of origin picture and index of current contact in database
		 * @param originalPath, path where the picture is currently located
		 * @param pathResources, path to resources, where folder resources is located.
		 * @param finalIndex from the database, which will be added to contact, preventing
		 * deleting picture from the different contacts with the same name of origin picture
		 * @return full path of the new location of the resize picture 
		 */
		protected String getNewImageLocation(String originalPath,String pathResources, 
				int finalIndex) {
			String finalPath=originalPath;
			try {
				original = ImageIO.read(new File(originalPath));
				//scaled = getScaledInstanceToFit(original, new Dimension(128, 128));
				
				scaled = new BufferedImage(128, 128,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = scaled.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawImage(original, 0, 0, 128, 128, this);
				g2d.dispose();
				
				if(finalPath.contains("\\")){
					finalPath=pathResources
							+finalIndex+finalPath.substring(finalPath.lastIndexOf("\\")+1);}
				else{finalPath=pathResources
						+finalIndex+finalPath.substring(finalPath.lastIndexOf("/")+1);}
				
			ImageIO.write(scaled, "jpg", new File(finalPath));
			
			
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return finalPath;
		}

		@Override
		public Dimension getPreferredSize() {

			Dimension size = super.getPreferredSize();
			if (original != null) {
				if (scaled != null) {
					size.width = original.getWidth() + scaled.getWidth();
					size.height = original.getHeight();
				} else {
					size.width = original.getWidth();
					size.height = original.getHeight();
				}
			}

			return size;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			if (original != null) {
				int x = 0;
				int y = (getHeight() - original.getHeight()) / 2;
				;
				if (scaled != null) {
					x = (getWidth() - (original.getWidth() + scaled.getWidth())) / 2;
				} else {
					x = (getWidth() - original.getWidth()) / 2;
				}
				g2d.drawImage(original, x, y, this);

				if (scaled != null) {
					x += original.getWidth();
					y = (getHeight() - scaled.getHeight()) / 2;
					g2d.drawImage(scaled, x, y, (ImageObserver) this);
				}
			}
			g2d.dispose();
		}

		public BufferedImage getScaledInstanceToFit(BufferedImage img,
				Dimension size) {
			float scaleFactor = getScaleFactorToFit(img, size);
			return getScaledInstance(img, scaleFactor);
		}

		public float getScaleFactorToFit(BufferedImage img, Dimension size) {
			float scale = 1f;
			if (img != null) {
				int imageWidth = img.getWidth();
				int imageHeight = img.getHeight();
				scale = getScaleFactorToFit(new Dimension(imageWidth,
						imageHeight), size);
			}
			return scale;
		}

		public float getScaleFactorToFit(Dimension original, Dimension toFit) {
			float scale = 1f;
			if (original != null && toFit != null) {
				float dScaleWidth = getScaleFactor(original.width, toFit.width);
				float dScaleHeight = getScaleFactor(original.height,
						toFit.height);
				scale = Math.min(dScaleHeight, dScaleWidth);
				System.out.println("dScaleHeight "+ dScaleHeight);
				System.out.println("dScaleWidth = "+dScaleWidth);
			}
			return scale;
		}

		public float getScaleFactor(int iMasterSize, int iTargetSize) {
			float scale = 1;
			if (iMasterSize > iTargetSize) {
				scale = (float) iTargetSize / (float) iMasterSize;
			} else {
				scale = (float) iTargetSize / (float) iMasterSize;
			}
			return scale;
		}

		public BufferedImage getScaledInstance(BufferedImage img,
				double dScaleFactor) {
			BufferedImage imgBuffer = null;
			imgBuffer = getScaledInstance(img, dScaleFactor,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
			return imgBuffer;
		}

		protected BufferedImage getScaledInstance(BufferedImage img,
				double dScaleFactor, Object hint, boolean higherQuality) {

			int targetWidth = (int) Math.round(img.getWidth() * dScaleFactor);
			int targetHeight = (int) Math.round(img.getHeight() * dScaleFactor);

			int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
					: BufferedImage.TYPE_INT_ARGB;

			BufferedImage ret = (BufferedImage) img;

			if (targetHeight > 0 || targetWidth > 0) {
				int w, h;
				if (higherQuality) {
					w = img.getWidth();
					h = img.getHeight();
				} else {
					w = targetWidth;
					h = targetHeight;
				}

				do {
					if (higherQuality && w > targetWidth) {
						w /= 2;
						if (w < targetWidth) {
							w = targetWidth;
						}
					}

					if (higherQuality && h > targetHeight) {
						h /= 2;
						if (h < targetHeight) {
							h = targetHeight;
						}
					}

					BufferedImage tmp = new BufferedImage(Math.max(w, 1),
							Math.max(h, 1), type);
					Graphics2D g2 = tmp.createGraphics();
					g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
					g2.drawImage(ret, 0, 0, w, h, null);
					g2.dispose();

					ret = tmp;
				} while (w != targetWidth || h != targetHeight);
			} else {
				ret = new BufferedImage(1, 1, type);
			}
			return ret;
		}
	
}