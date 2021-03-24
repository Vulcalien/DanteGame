/*
 * This code is licensed under MIT License by Vulcalien (see LICENSE)
 */
package vulc.dantegame.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import vulc.dantegame.Game;

public class GameFrame extends Frame {

	private static final long serialVersionUID = 1L;

	public final Canvas canvas = new Canvas();

	private final Screen screen;

	private int xRend, yRend, wRend, hRend;

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
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				calculateRenderValues();
			}
		});

		if(isFullScreen) {
			// Windows 10 needs this
			setUndecorated(true);

			// While Linux (tested Debian GNOME) needs this
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

		calculateRenderValues();
	}

	public void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		Game.render(screen);

		Graphics g = bs.getDrawGraphics();
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // this should fix flickering on the gray bars
		g.drawImage(img, xRend, yRend, wRend, hRend, null);
		g.dispose();
		bs.show();
	}

	public void calculateRenderValues() {
		Dimension size = canvas.getSize();

		xRend = 0;
		yRend = 0;

		wRend = size.width;
		hRend = size.height;

		// check what has the lowest scale
		if(1.0 * wRend / Game.WIDTH > 1.0 * hRend / Game.HEIGHT) {
			wRend = (int) (hRend * Game.SCREEN_RATIO);
		} else {
			hRend = (int) (1.0 * wRend / Game.SCREEN_RATIO);
		}

		xRend = (size.width - wRend) / 2;
		yRend = (size.height - hRend) / 2;
	}

	public void initFrame() {
		Game.INPUT.init(canvas);
	}

	public void destroyFrame() {
		Game.INPUT.remove(canvas);
		setVisible(false);
	}

}
