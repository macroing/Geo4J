/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.geo4j.
 * 
 * org.macroing.geo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.geo4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.geo4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.geo4j.shape.ls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.UncheckedIOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.bv.BoundingVolume3D;
import org.macroing.geo4j.bv.aabb.AxisAlignedBoundingBox3D;
import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class LineSegment3DUnitTests {
	public LineSegment3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Point3D a = new Point3D(10.0D, 10.0D, 10.0D);
		final Point3D b = new Point3D(20.0D, 10.0D, 10.0D);
		
		final LineSegment3D lineSegment = new LineSegment3D(a, b);
		
		assertTrue(lineSegment.accept(new NodeHierarchicalVisitorMock(node -> false,                                                        node -> node.equals(lineSegment))));
		assertTrue(lineSegment.accept(new NodeHierarchicalVisitorMock(node -> node.equals(lineSegment),                                     node -> node.equals(lineSegment))));
		assertTrue(lineSegment.accept(new NodeHierarchicalVisitorMock(node -> node.equals(lineSegment) || node.equals(a),                   node -> node.equals(lineSegment) || node.equals(a))));
		assertTrue(lineSegment.accept(new NodeHierarchicalVisitorMock(node -> node.equals(lineSegment) || node.equals(a) || node.equals(b), node -> node.equals(lineSegment) || node.equals(a) || node.equals(b))));
		
		assertThrows(NodeTraversalException.class, () -> lineSegment.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> lineSegment.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertThrows(NodeTraversalException.class, () -> lineSegment.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> lineSegment.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(9, LineSegment3D.ID);
		assertEquals("Line Segment", LineSegment3D.NAME);
	}
	
	@Test
	public void testConstructor() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals(new Point3D(10.0D, 10.0D, 10.0D), lineSegment.getA());
		assertEquals(new Point3D(20.0D, 10.0D, 10.0D), lineSegment.getB());
		
		assertThrows(NullPointerException.class, () -> new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), null));
		assertThrows(NullPointerException.class, () -> new LineSegment3D(null, new Point3D(20.0D, 10.0D, 10.0D)));
	}
	
	@Test
	public void testContains() {
		final List<LineSegment3D> lineSegments = LineSegment3D.fromPoints(new Point3D(10.0D, 0.0D, 0.0D), new Point3D(20.0D, 0.0D, 0.0D), new Point3D(30.0D, 10.0D, 0.0D), new Point3D(30.0D, 20.0D, 0.0D), new Point3D(20.0D, 30.0D, 0.0D), new Point3D(10.0D, 30.0D, 0.0D), new Point3D(0.0D, 20.0D, 0.0D), new Point3D(0.0D, 10.0D, 0.0D));
		
		for(final LineSegment3D lineSegment : lineSegments) {
			assertTrue(lineSegment.contains(lineSegment.getA()));
			assertTrue(lineSegment.contains(Point3D.midpoint(lineSegment.getA(), lineSegment.getB())));
			assertTrue(lineSegment.contains(lineSegment.getB()));
			
			assertFalse(lineSegment.contains(new Point3D(lineSegment.getA().x + 100.0D, lineSegment.getA().y, lineSegment.getA().z)));
			assertFalse(lineSegment.contains(new Point3D(lineSegment.getA().x - 100.0D, lineSegment.getA().y, lineSegment.getA().z)));
			
			assertFalse(lineSegment.contains(new Point3D(lineSegment.getA().x, lineSegment.getA().y + 100.0D, lineSegment.getA().z)));
			assertFalse(lineSegment.contains(new Point3D(lineSegment.getA().x, lineSegment.getA().y - 100.0D, lineSegment.getA().z)));
			
			assertFalse(lineSegment.contains(new Point3D(lineSegment.getA().x, lineSegment.getA().y, lineSegment.getA().z + 100.0D)));
			assertFalse(lineSegment.contains(new Point3D(lineSegment.getA().x, lineSegment.getA().y, lineSegment.getA().z - 100.0D)));
		}
		
		assertThrows(NullPointerException.class, () -> new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D)).contains(null));
	}
	
	@Test
	public void testEquals() {
		final LineSegment3D a = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		final LineSegment3D b = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		final LineSegment3D c = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(30.0D, 30.0D, 30.0D));
		final LineSegment3D d = new LineSegment3D(new Point3D(30.0D, 30.0D, 30.0D), new Point3D(20.0D, 10.0D, 10.0D));
		final LineSegment3D e = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
	}
	
	@Test
	public void testFromPoints() {
		final List<LineSegment3D> lineSegments = LineSegment3D.fromPoints(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(10.0D, 0.0D, 0.0D), new Point3D(10.0D, 10.0D, 0.0D), new Point3D(0.0D, 10.0D, 0.0D));
		
		assertNotNull(lineSegments);
		
		assertEquals(4, lineSegments.size());
		
		assertEquals(new LineSegment3D(new Point3D( 0.0D,  0.0D, 0.0D), new Point3D(10.0D,  0.0D, 0.0D)), lineSegments.get(0));
		assertEquals(new LineSegment3D(new Point3D(10.0D,  0.0D, 0.0D), new Point3D(10.0D, 10.0D, 0.0D)), lineSegments.get(1));
		assertEquals(new LineSegment3D(new Point3D(10.0D, 10.0D, 0.0D), new Point3D( 0.0D, 10.0D, 0.0D)), lineSegments.get(2));
		assertEquals(new LineSegment3D(new Point3D( 0.0D, 10.0D, 0.0D), new Point3D( 0.0D,  0.0D, 0.0D)), lineSegments.get(3));
		
		assertThrows(IllegalArgumentException.class, () -> LineSegment3D.fromPoints(new Point3D()));
		assertThrows(NullPointerException.class, () -> LineSegment3D.fromPoints((Point3D[])(null)));
		assertThrows(NullPointerException.class, () -> LineSegment3D.fromPoints(new Point3D[] {new Point3D(), null}));
	}
	
	@Test
	public void testGetA() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals(new Point3D(10.0D, 10.0D, 10.0D), lineSegment.getA());
	}
	
	@Test
	public void testGetB() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals(new Point3D(20.0D, 10.0D, 10.0D), lineSegment.getB());
	}
	
	@Test
	public void testGetBoundingVolume() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		final BoundingVolume3D a = new AxisAlignedBoundingBox3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		final BoundingVolume3D b = lineSegment.getBoundingVolume();
		
		assertEquals(a, b);
	}
	
	@Test
	public void testGetID() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals(LineSegment3D.ID, lineSegment.getID());
	}
	
	@Test
	public void testGetName() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals(LineSegment3D.NAME, lineSegment.getName());
	}
	
	@Test
	public void testGetSurfaceArea() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals(0.0D, lineSegment.getSurfaceArea());
	}
	
	@Test
	public void testHashCode() {
		final LineSegment3D a = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		final LineSegment3D b = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersectionT() {
		assertEquals(2.0D, new LineSegment3D(new Point3D(0.0D, 5.0D, 7.0D), new Point3D(9.0D, 5.0D, 7.0D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 1000.0D));
		assertEquals(2.0D, new LineSegment3D(new Point3D(0.0D, 5.0D, 7.0D), new Point3D(5.0D, 5.0D, 7.0D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 1000.0D));
		assertEquals(2.0D, new LineSegment3D(new Point3D(5.0D, 5.0D, 7.0D), new Point3D(9.0D, 5.0D, 7.0D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 1000.0D));
		
		assertEquals(0.0000000001000000082740371D, new LineSegment3D(new Point3D(5.0D, 5.0D, 5.0000000001D), new Point3D(9.0D, 5.0D, 5.0000000001D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 1000.0D));
		
		assertEquals(Double.NaN, new LineSegment3D(new Point3D(0.0D, 5.0D, 7.0D), new Point3D(4.0D, 5.0D, 7.0D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 1000.0D));
		assertEquals(Double.NaN, new LineSegment3D(new Point3D(6.0D, 5.0D, 7.0D), new Point3D(9.0D, 5.0D, 7.0D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 1000.0D));
		
		assertEquals(Double.NaN, new LineSegment3D(new Point3D(0.0D, 5.0D, 7.0D), new Point3D(9.0D, 5.0D, 7.0D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 1.0D));
		assertEquals(Double.NaN, new LineSegment3D(new Point3D(0.0D, 5.0D, 7.0D), new Point3D(9.0D, 5.0D, 7.0D)).intersectionT(new Ray3D(new Point3D(5.0D, 5.0D, 5.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 3.0D, 1000.0D));
		
		assertThrows(NullPointerException.class, () -> new LineSegment3D(new Point3D(0.0D, 5.0D, 7.0D), new Point3D(9.0D, 5.0D, 7.0D)).intersectionT(null, 0.0D, 1000.0D));
	}
	
	@Test
	public void testToString() {
		final LineSegment3D lineSegment = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		assertEquals("new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D))", lineSegment.toString());
	}
	
	@Test
	public void testWrite() {
		final LineSegment3D a = new LineSegment3D(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 10.0D, 10.0D));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final LineSegment3D b = new LineSegment3DReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}