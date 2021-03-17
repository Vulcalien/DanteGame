/*
 * This code is licensed under MIT License by Vulcalien (see LICENSE)
 */
package vulc.engine.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import vulc.engine.Game;

public class GameFrame extends Frame {

	private static final long serialVersionUID = 1L;

	public final Canvas canvas = new Canvas();

	private final Screen screen;

	private final BufferedImage img;
	protected final int[] pixels;

	public final int width;
	public final int height;
	public final int scale;

	public final boolean isFullScreen;

	public GameFrame(String title, int width, int height, int scale, boolean isFullScreen) {
		super(title);

		this.width = width;
		this.height = height;
		this.scale = scale;

		this.isFullScreen = isFullScreen;

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		screen = new Screen(width, height, pixels);

		// initialize GUI
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			};
		});

		if(isFullScreen) {
			GraphicsDevice d = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			d.setFullScreenWindow(this);
		}

		Dimension canvasSize;
		if(isFullScreen) canvasSize = Toolkit.getDefaultToolkit().getScreenSize();
		else canvasSize = new Dimension(width * scale, height * scale);

		canvas.setPreferredSize(canvasSize);
		canvas.setMaximumSize(canvasSize);
		canvas.setMinimumSize(canvasSize);

		canvas.setBackground(Color.GRAY);

		add(canvas);
		pack();
		setLocationRelativeTo(null);

		canvas.requestFocus();
	}

	public void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		Game.render(screen);

		Dimension size = canvas.getSize();

		// TODO consider calculating these values only when necessary (when the frame is resized)
		int rendx = 0;
		int rendy = 0;

		int rendWidth = size.width;
		int rendHeight = size.height;

		// check what has the lowest scale
		if(1.0 * rendWidth / Game.WIDTH > 1.0 * rendHeight / Game.HEIGHT) {
			rendWidth = (int) (rendHeight * Game.SCREEN_RATIO);
		} else {
			rendHeight = (int) (1.0 * rendWidth / Game.SCREEN_RATIO);
		}

		rendx = (size.width - rendWidth) / 2;
		rendy = (size.height - rendHeight) / 2;

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, rendx, rendy, rendWidth, rendHeight, null);
		g.dispose();
		bs.show();
	}

	public void initFrame() {
		Game.INPUT.init(canvas);
	}

	public void destroyFrame() {
		Game.INPUT.remove(canvas);
		setVisible(false);
	}

}
