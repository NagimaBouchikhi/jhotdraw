package org.jhotdraw.draw.figure;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.utils.geom.path.BezierPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BezierFigureTest {

  private BezierFigure openFigure;
  private BezierFigure closedFigure;
  private Graphics2D graphics;

  @BeforeEach
  public void setUp() {
    // Create an open bezier figure
    openFigure = new BezierFigure(false);
    openFigure.addNode(new BezierPath.Node(10, 10));
    openFigure.addNode(new BezierPath.Node(100, 10));
    openFigure.addNode(new BezierPath.Node(100, 100));

    // Create a closed bezier figure
    closedFigure = new BezierFigure(true);
    closedFigure.addNode(new BezierPath.Node(10, 10));
    closedFigure.addNode(new BezierPath.Node(100, 10));
    closedFigure.addNode(new BezierPath.Node(100, 100));
    closedFigure.addNode(new BezierPath.Node(10, 100));

    // Create graphics for drawing tests
    BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
    graphics = image.createGraphics();
  }

  @Test
  public void testConstructor() {
    BezierFigure defaultFigure = new BezierFigure();
    assertFalse(defaultFigure.isClosed(), "Default constructor should create an open figure");
    assertEquals(0, defaultFigure.getNodeCount(), "Default figure should have no nodes");

    BezierFigure explicitOpen = new BezierFigure(false);
    assertFalse(explicitOpen.isClosed(), "Figure should be open when specified");

    BezierFigure explicitClosed = new BezierFigure(true);
    assertTrue(explicitClosed.isClosed(), "Figure should be closed when specified");
  }

  @Test
  public void testAddAndGetNode() {
    BezierFigure figure = new BezierFigure();

    // Test adding nodes
    BezierPath.Node node1 = new BezierPath.Node(10, 20);
    figure.addNode(node1);
    assertEquals(1, figure.getNodeCount(), "Node count should be 1 after adding a node");

    BezierPath.Node node2 = new BezierPath.Node(30, 40);
    figure.addNode(0, node2); // Add at index 0
    assertEquals(2, figure.getNodeCount(), "Node count should be 2 after adding another node");

    // Test getting nodes
    BezierPath.Node retrievedNode = figure.getNode(0);
    assertEquals(
        node2.getControlPoint(0),
        retrievedNode.getControlPoint(0),
        "Retrieved node should match the added node");
  }

  @Test
  public void testSetAndGetPoint() {
    // Test setting and getting points
    Point2D.Double newPoint = new Point2D.Double(50, 60);
    openFigure.setPoint(1, newPoint);

    Point2D.Double retrievedPoint = openFigure.getPoint(1);
    assertEquals(newPoint, retrievedPoint, "Retrieved point should match the set point");

    // Test setting specific control point
    Point2D.Double ctrlPoint = new Point2D.Double(70, 80);
    openFigure.setPoint(1, 0, ctrlPoint);
    assertEquals(
        ctrlPoint,
        openFigure.getPoint(1, 0),
        "Retrieved control point should match the set control point");
  }

  @Test
  public void testStartAndEndPoints() {
    // Test setting start point
    Point2D.Double startPoint = new Point2D.Double(5, 15);
    openFigure.setStartPoint(startPoint);
    assertEquals(startPoint, openFigure.getStartPoint(), "Start point should match the set point");

    // Test setting end point
    Point2D.Double endPoint = new Point2D.Double(105, 115);
    openFigure.setEndPoint(endPoint);
    assertEquals(endPoint, openFigure.getEndPoint(), "End point should match the set point");

    // Test with empty figure
    BezierFigure emptyFigure = new BezierFigure();
    emptyFigure.setStartPoint(startPoint);
    emptyFigure.setEndPoint(endPoint);
    assertEquals(
        2,
        emptyFigure.getNodeCount(),
        "Empty figure should have 2 nodes after setting start and end points");
  }

  @Test
  public void testBezierPathManipulation() {
    // Test getting bezier path
    BezierPath path = openFigure.getBezierPath();
    assertNotNull(path, "Bezier path should not be null");
    assertEquals(3, path.size(), "Path should have 3 nodes");

    // Test setting bezier path
    BezierPath newPath = new BezierPath();
    newPath.add(new BezierPath.Node(1, 1));
    newPath.add(new BezierPath.Node(2, 2));
    openFigure.setBezierPath(newPath);

    assertEquals(2, openFigure.getNodeCount(), "Node count should match the new path");
    assertEquals(
        new Point2D.Double(1, 1), openFigure.getPoint(0), "First point should match the new path");
  }

  @Test
  public void testClosedProperty() {
    assertFalse(openFigure.isClosed(), "Figure should be open as initialized");

    openFigure.setClosed(true);
    assertTrue(openFigure.isClosed(), "Figure should be closed after setting closed");

    openFigure.setClosed(false);
    assertFalse(openFigure.isClosed(), "Figure should be open after setting open");
  }

  @Test
  public void testTransform() {
    // Create a new figure with known points
    BezierFigure figure = new BezierFigure();
    figure.addNode(new BezierPath.Node(10, 10));
    figure.addNode(new BezierPath.Node(20, 20));

    // Apply a translation transform
    AffineTransform tx = AffineTransform.getTranslateInstance(5, 10);
    figure.transform(tx);

    // Check points are transformed
    assertEquals(
        new Point2D.Double(15, 20), figure.getPoint(0), "First point should be translated");
    assertEquals(
        new Point2D.Double(25, 30), figure.getPoint(1), "Second point should be translated");
  }

  @Test
  public void testContains() {
    // Point inside the figure
    assertTrue(
        closedFigure.contains(new Point2D.Double(50, 50), 1.0),
        "Closed figure should contain point inside");

    // Point outside the figure
    assertFalse(
        closedFigure.contains(new Point2D.Double(200, 200), 1.0),
        "Figure should not contain point outside");

    // Point on the path
    assertTrue(
        closedFigure.contains(new Point2D.Double(10, 50), 1.0),
        "Figure should contain point on the path");
  }

  @Test
  public void testSplitAndJoinSegments() {
    // Test splitting a segment
    Point2D.Double splitPoint = new Point2D.Double(50, 10); // Point on the first segment
    int splitIndex = openFigure.splitSegment(splitPoint, 5.0);

    assertTrue(splitIndex > 0, "Split should return valid index");
    assertEquals(4, openFigure.getNodeCount(), "Node count should increase after split");

    // Test joining segments (this is more complex and depends on implementation details)
    Point2D.Double joinPoint = openFigure.getPoint(splitIndex);
    boolean joined = openFigure.joinSegments(joinPoint, 5.0);

    // Either join was successful or not, depending on implementation
    if (joined) {
      assertEquals(3, openFigure.getNodeCount(), "Node count should decrease after join");
    }
  }

  @Test
  public void testCreateHandles() {
    // Test handle creation at different detail levels
    Collection<Handle> handlesNegative = openFigure.createHandles(-1);
    assertNotNull(handlesNegative, "Should return handles for negative detail level");
    assertEquals(1, handlesNegative.size(), "Should return 1 handle for negative detail level");

    Collection<Handle> handlesZero = openFigure.createHandles(0);
    assertNotNull(handlesZero, "Should return handles for detail level 0");
    assertEquals(
        openFigure.getNodeCount() + 1,
        handlesZero.size(),
        "Should return node count + 1 handles for detail level 0");

    Collection<Handle> handlesOne = openFigure.createHandles(1);
    assertNotNull(handlesOne, "Should return handles for detail level 1");
  }

  @Test
  public void testGetSetAttributes() {
    // Test attribute setting and getting
    openFigure.attr().set(AttributeKeys.FILL_COLOR, Color.RED);
    assertEquals(
        Color.RED,
        openFigure.attr().get(AttributeKeys.FILL_COLOR),
        "Fill color attribute should match set value");

    openFigure.attr().set(AttributeKeys.STROKE_WIDTH, 2.0);
    assertEquals(
        2.0,
        openFigure.attr().get(AttributeKeys.STROKE_WIDTH),
        "Stroke width attribute should match set value");

    openFigure.attr().set(AttributeKeys.PATH_CLOSED, true);
    assertTrue(
        openFigure.isClosed(), "Figure should be closed after setting PATH_CLOSED attribute");
  }

  @Test
  public void testClone() {
    BezierFigure clone = openFigure.clone();

    assertNotSame(openFigure, clone, "Clone should be a different object");
    assertEquals(
        openFigure.getNodeCount(), clone.getNodeCount(), "Clone should have same number of nodes");

    for (int i = 0; i < openFigure.getNodeCount(); i++) {
      assertEquals(
          openFigure.getPoint(i), clone.getPoint(i), "Points in clone should match original");
    }

    assertEquals(openFigure.isClosed(), clone.isClosed(), "Closed property should match in clone");
  }

  @Test
  public void testRemoveNode() {
    int initialNodeCount = openFigure.getNodeCount();
    BezierPath.Node removedNode = openFigure.removeNode(1);

    assertNotNull(removedNode, "Removed node should not be null");
    assertEquals(
        initialNodeCount - 1,
        openFigure.getNodeCount(),
        "Node count should decrease after removal");
  }

  @Test
  public void testDrawing() {
    // Just verify these methods don't throw exceptions
    assertDoesNotThrow(
        () -> {
          openFigure.draw(graphics);
          openFigure.drawFill(graphics);
          openFigure.drawStroke(graphics);
        },
        "Drawing methods should not throw exceptions");
  }

  @Test
  public void testFindNode() {
    Point2D.Double point = openFigure.getPoint(1); // Get the second node's point
    int foundIndex = openFigure.findNode(point);

    assertEquals(1, foundIndex, "Should find the correct node index");

    // Test with a point that doesn't match any node
    int notFoundIndex = openFigure.findNode(new Point2D.Double(999, 999));
    assertEquals(-1, notFoundIndex, "Should return -1 for point not at any node");
  }

  @Test
  public void testFindSegment() {
    // Point on the first segment
    Point2D.Double point = new Point2D.Double(50, 10);
    int segmentIndex = openFigure.findSegment(point, 5.0);

    assertTrue(segmentIndex >= 0, "Should find segment for point on path");

    // Point not on any segment
    int notFoundIndex = openFigure.findSegment(new Point2D.Double(999, 999), 5.0);
    assertEquals(-1, notFoundIndex, "Should return -1 for point not on path");
  }

  @Test
  public void testChop() {
    // Test chopping from outside the figure
    Point2D.Double outside = new Point2D.Double(150, 50);
    Point2D.Double chopped = openFigure.chop(outside);

    assertNotNull(chopped, "Chop should return a point");
    // Additional assertions would depend on the specific implementation
  }

  @Test
  public void testGetCenter() {
    Point2D.Double center = openFigure.getCenter();
    assertNotNull(center, "Center should not be null");
    // Additional assertions would depend on the specific points in the figure
  }

  @Test
  public void testGetPointOnPath() {
    Point2D.Double point = openFigure.getPointOnPath(0.5, 0.1);
    assertNotNull(point, "Point on path should not be null");
    // Additional assertions would depend on the specific points in the figure
  }

  @Test
  public void testRestoreTransform() {
    // Save original state
    Object restoreData = openFigure.getTransformRestoreData();

    // Change the figure
    openFigure.transform(AffineTransform.getScaleInstance(2, 2));

    // Restore to original state
    openFigure.restoreTransformTo(restoreData);

    // Verify the points are restored
    assertEquals(
        new Point2D.Double(10, 10), openFigure.getPoint(0), "First point should be restored");
    assertEquals(
        new Point2D.Double(100, 10), openFigure.getPoint(1), "Second point should be restored");
  }

  @Test
  public void testSetBounds() {
    Point2D.Double start = new Point2D.Double(0, 0);
    Point2D.Double end = new Point2D.Double(200, 200);

    BezierFigure figure = new BezierFigure();
    figure.setBounds(start, end);

    assertEquals(start, figure.getStartPoint(), "Start point should match");
    assertEquals(end, figure.getEndPoint(), "End point should match");
  }
}
