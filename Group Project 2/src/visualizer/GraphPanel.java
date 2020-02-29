/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package visualizer;

import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * This class displays the Graph panel with labels for the axis.
 * 
 * @author Bree S. Dinish-Lomelli
 */
public class GraphPanel extends JPanel {

	/**
	 * A generated Serial ID.
	 */
	private static final long serialVersionUID = -3881718946265569472L;
	/**
	 * The windows title.
	 */
	private String myTitle;
	/**
	 * The X label for the window.
	 */
	private String myXLabel;
	/**
	 * The Y label for the window.
	 */
	private String myYLabel;

	/**
	 * This is the constructor that takes in the title, the untils and a List of
	 * measurements data.
	 * 
	 * @param theTitle String
	 * @param theUnits double>
	 * @param theData  List<Integer)
	 */
	public GraphPanel(String theTitle, double theUnits, List<Double> theData) {
		setLayout(new BorderLayout());
		this.myTitle = theTitle;
		initSetup(theData);
	}

	/**
	 * This constructor sets up a graph with labels for x and y axis
	 * 
	 * @param yLabel
	 * @param xLabel
	 * @param theData
	 */

	public GraphPanel(String yLabel, String xLabel, List<Double> theData) {
		setLayout(new BorderLayout());
		this.myXLabel = xLabel;
		this.myYLabel = yLabel;
		initSetup(theData);
	}

	/**
	 * This method setsup the Graph panel with lables for the title, and the x and y
	 * axis.
	 * 
	 * @param theData
	 */
	private void initSetup(List<Double> theData) {
		setLayout(new BorderLayout());
		JLabel title = new JLabel(myTitle);
		title.setFont(new Font("Arial", Font.BOLD, 25));
		title.setHorizontalAlignment(JLabel.CENTER);

		JPanel graphPanel = new Graph(theData);

		GraphPanel.YPanel YLabel = new GraphPanel.YPanel();

		GraphPanel.XPanel XLabel = new GraphPanel.XPanel();

		add(graphPanel, BorderLayout.CENTER);
		add(title, BorderLayout.NORTH);
		add(XLabel, BorderLayout.SOUTH);
		add(YLabel, BorderLayout.WEST);

	}

	/**
	 * This Class creates the actual graph.
	 *
	 */
	static class Graph extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3174412070034899983L;
		/**
		 * The Graphs width.
		 */
		private int width = 400;
		/**
		 * The Graphs height.
		 */
		private int heigth = 300;
		/**
		 * The padding that the graph uses for its edges.
		 */
		private int padding = 25;
		/**
		 * The padding on the graphs edges.
		 */
		private int labelPadding = 25;
		/**
		 * The color of the lines.
		 */
		private Color lineColor = Color.gray;
		/**
		 * The color of the points.
		 */
		private Color pointColor = Color.BLUE;
		/**
		 * The stroke of the lines.
		 */
		private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
		/**
		 * The size of the points.
		 */
		private int pointSize = 4;
		
		/**
		 * The number of vertical divisions.
		 */
		private int numYDivisions = 10;
		
		/**
		 * The current data.
		 */
		private List<Double> myData;

		/**
		 * Initializes a new graph.
		 * @param theData The data used to set up the graphing points.
		 */
		public Graph(List<Double> theData) {
			this.myData = theData;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (myData.size() - 1);
			double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMax() - getMin());

			List<Point> graphPoints = new ArrayList<>();
			for (int i = 0; i < myData.size(); i++) {
				int x1 = (int) (i * xScale + padding + labelPadding);
				int y1 = (int) ((getMax() - myData.get(i)) * yScale + padding);
				graphPoints.add(new Point(x1, y1));
			}

			// creates background
			g2.setColor(Color.WHITE);
			g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
					getHeight() - 2 * padding - labelPadding);
			g2.setColor(Color.BLACK);

			// create hatch marks for y axis.
			for (int i = 0; i < numYDivisions + 1; i++) {
				int x0 = padding + labelPadding;
				int x1 = pointSize + padding + labelPadding;
				int y0 = getHeight()
						- ((i * (getHeight() - padding * 2 - labelPadding)) / numYDivisions + padding + labelPadding);
				int y1 = y0;
				if (myData.size() > 0) {
					g2.setColor(Color.BLACK);
					String yLabel = ((int) ((getMin() + (getMax() - getMin()) * ((i * 1.0) / numYDivisions)) * 100))
							/ 100.0 + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(yLabel);
					g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}

			// creates the hatch marks for x axis
			for (int i = 0; i < myData.size(); i++) {
				if (myData.size() > 1) {
					int x0 = i * (getWidth() - padding * 2 - labelPadding) / (myData.size() - 1) + padding
							+ labelPadding;
					int x1 = x0;
					int y0 = getHeight() - padding - labelPadding;
					int y1 = y0 - pointSize;
					if ((i % ((int) ((myData.size() / 20.0)) + 1)) == 0) {
						g2.setColor(Color.BLACK);
						String xLabel = i - myData.size() + 1 + "";
						FontMetrics metrics = g2.getFontMetrics();
						int labelWidth = metrics.stringWidth(xLabel);
						g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
					}
					g2.drawLine(x0, y0, x1, y1);
				}
			}

			// create x and y axes
			g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
			g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
					getHeight() - padding - labelPadding);

			Stroke oldStroke = g2.getStroke();
			g2.setColor(lineColor);
			g2.setStroke(GRAPH_STROKE);
			for (int i = 0; i < graphPoints.size() - 1; i++) {
				int x1 = graphPoints.get(i).x;
				int y1 = graphPoints.get(i).y;
				int x2 = graphPoints.get(i + 1).x;
				int y2 = graphPoints.get(i + 1).y;
				g2.drawLine(x1, y1, x2, y2);
			}

			g2.setStroke(oldStroke);
			g2.setColor(pointColor);
			for (int i = 0; i < graphPoints.size(); i++) {
				int x = graphPoints.get(i).x - pointSize / 2;
				int y = graphPoints.get(i).y - pointSize / 2;
				int ovalW = pointSize;
				int ovalH = pointSize;
				g2.fillOval(x, y, ovalW, ovalH);
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(width, heigth);
		}

		/**
		 * This method returns the smallest number out of the arrayList.
		 * 
		 * @return double
		 */
		private double getMin() {
			double min = Double.MAX_VALUE;
			for (Double score : myData) {
				min = Math.min(min, score);
			}
			return min;
		}

		/**
		 * This method returns the largest number from the arrayList.
		 * 
		 * @return double
		 */
		private double getMax() {
			double max = Double.MIN_VALUE;
			for (Double score : myData) {
				max = Math.max(max, score);
			}
			return max;
		}
	}

	/**
	 * This was made for testing the Graph class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}

			/**
			 * Test the UI.
			 */
			private void createAndShowGui() {
				List<Double> nums = new ArrayList<>();
				Random random = new Random();
				int maxDataPoints = 25;
				int maxY = 130;
				for (int i = 0; i < maxDataPoints + 1; i++) {
					nums.add((double) random.nextDouble() * maxY);

				}

				GraphPanel mainPanel = new GraphPanel("Temperature", "Days", nums);

				JFrame frame = new JFrame();
				mainPanel.getPreferredSize();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(mainPanel);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});

	}

	/**
	 * This inner class creates a JPanel that will be used as a "label" for the
	 * y-axis.
	 */
	class YPanel extends JPanel {

		/**
		 * A automatically generated version ID.
		 */
		private static final long serialVersionUID = 4522594477633814736L;

		/**
		 * Constructor for YPanel class that initializes the Dimensions.
		 */
		public YPanel() {
			setPreferredSize(new Dimension(30, 0));
		}

		/**
		 * This draws the label being created.
		 */
		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Font font = new Font("Arial", Font.PLAIN, 15);

			String text = myYLabel;

			FontMetrics metrics = g.getFontMetrics(font);
			int width = metrics.stringWidth(text);
			metrics.getHeight();

			g2.setFont(font);

			rotate(g2, getWidth(), (getHeight() + width) / 2, 270, text);
		}

		/**
		 * This method takes in the Graphics 2D and translate and rotates it at an
		 * angle.
		 */
		private void rotate(Graphics2D g2, double x, double y, int angle, String text) {

			g2.translate(x, y);
			g2.rotate(Math.toRadians(angle));
			g2.drawString(text, 0, 0);
			g2.rotate(-Math.toRadians(angle));
			g2.translate(-x, -y);

		}

	}

	/**
	 * This inner class creates the JPanel that will be used as a label for the
	 * x-axis.
	 */
	class XPanel extends JPanel {

		/**
		 * A automatically generated version ID.
		 */
		private static final long serialVersionUID = -1241294994255528608L;

		/**
		 * Constructor for XPanel class that initializes the Dimensions.
		 */
		public XPanel() {
			setPreferredSize(new Dimension(0, 25));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Font font = new Font("Arial", Font.PLAIN, 15);
			String text = myXLabel;

			FontMetrics metrics = g.getFontMetrics(font);
			int width = metrics.stringWidth(text);
			metrics.getHeight();

			g2.setFont(font);
			g2.drawString(text, (getWidth() - width) / 2, 11);

		}

	}

}
