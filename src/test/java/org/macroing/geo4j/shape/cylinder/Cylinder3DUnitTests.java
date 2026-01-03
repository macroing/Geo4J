/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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
package org.macroing.geo4j.shape.cylinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.UncheckedIOException;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.AngleD;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.java.lang.Doubles;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Cylinder3DUnitTests {
	public Cylinder3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Cylinder3D cylinder = new Cylinder3D();
		
		assertTrue(cylinder.accept(new NodeHierarchicalVisitorMock(node -> false,                 node -> node.equals(cylinder))));
		assertTrue(cylinder.accept(new NodeHierarchicalVisitorMock(node -> node.equals(cylinder), node -> node.equals(cylinder))));
		
		assertThrows(NodeTraversalException.class, () -> cylinder.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> cylinder.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Cylinder3D cylinder = new Cylinder3D();
		
		assertThrows(NodeTraversalException.class, () -> cylinder.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> cylinder.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(6, Cylinder3D.ID);
		assertEquals("Cylinder", Cylinder3D.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Cylinder3D cylinder = new Cylinder3D();
		
		assertEquals(AngleD.degrees(360.0D), cylinder.getPhiMax());
		assertEquals(+1.0D, cylinder.getRadius());
		assertEquals(+1.0D, cylinder.getZMax());
		assertEquals(-1.0D, cylinder.getZMin());
	}
	
	@Test
	public void testConstructorAngleD() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), cylinder.getPhiMax());
		assertEquals(+1.0D, cylinder.getRadius());
		assertEquals(+1.0D, cylinder.getZMax());
		assertEquals(-1.0D, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3D(null));
	}
	
	@Test
	public void testConstructorAngleDDouble() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(180.0D), 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), cylinder.getPhiMax());
		assertEquals(+2.0D, cylinder.getRadius());
		assertEquals(+1.0D, cylinder.getZMax());
		assertEquals(-1.0D, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3D(null, 2.0D));
	}
	
	@Test
	public void testConstructorAngleDDoubleDouble() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(180.0D), 2.0D, 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), cylinder.getPhiMax());
		assertEquals(+2.0D, cylinder.getRadius());
		assertEquals(+2.0D, cylinder.getZMax());
		assertEquals(-1.0D, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3D(null, 2.0D, 2.0D));
	}
	
	@Test
	public void testConstructorAngleDDoubleDoubleDouble() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(180.0D), 2.0D, 2.0D, 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), cylinder.getPhiMax());
		assertEquals(+2.0D, cylinder.getRadius());
		assertEquals(+2.0D, cylinder.getZMax());
		assertEquals(+2.0D, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3D(null, 2.0D, 2.0D, 2.0D));
	}
	
	@Test
	public void testEquals() {
		final Cylinder3D a = new Cylinder3D(AngleD.degrees(360.0D), 1.0D, 1.0D, -1.0D);
		final Cylinder3D b = new Cylinder3D(AngleD.degrees(360.0D), 1.0D, 1.0D, -1.0D);
		final Cylinder3D c = new Cylinder3D(AngleD.degrees(360.0D), 1.0D, 1.0D, -2.0D);
		final Cylinder3D d = new Cylinder3D(AngleD.degrees(360.0D), 1.0D, 2.0D, -1.0D);
		final Cylinder3D e = new Cylinder3D(AngleD.degrees(360.0D), 2.0D, 1.0D, -1.0D);
		final Cylinder3D f = new Cylinder3D(AngleD.degrees(180.0D), 1.0D, 1.0D, -1.0D);
		final Cylinder3D g = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
		assertNotEquals(a, g);
		assertNotEquals(g, a);
	}
	
	@Test
	public void testGetID() {
		final Cylinder3D cylinder = new Cylinder3D();
		
		assertEquals(Cylinder3D.ID, cylinder.getID());
	}
	
	@Test
	public void testGetName() {
		final Cylinder3D cylinder = new Cylinder3D();
		
		assertEquals(Cylinder3D.NAME, cylinder.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), cylinder.getPhiMax());
	}
	
	@Test
	public void testGetRadius() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(360.0D), 2.0D);
		
		assertEquals(2.0D, cylinder.getRadius());
	}
	
	@Test
	public void testGetSurfaceArea() {
		final Cylinder3D cylinder = new Cylinder3D();
		
		assertEquals(Doubles.PI_MULTIPLIED_BY_4, cylinder.getSurfaceArea());
	}
	
	@Test
	public void testGetZMax() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(360.0D), 1.0D, 2.0D);
		
		assertEquals(2.0D, cylinder.getZMax());
	}
	
	@Test
	public void testGetZMin() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(360.0D), 1.0D, 1.0D, 2.0D);
		
		assertEquals(2.0D, cylinder.getZMin());
	}
	
	@Test
	public void testHashCode() {
		final Cylinder3D a = new Cylinder3D();
		final Cylinder3D b = new Cylinder3D();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final Cylinder3D cylinder = new Cylinder3D(AngleD.degrees(360.0D), 1.0D, 1.0D, -1.0D);
		
		assertEquals("new Cylinder3D(AngleD.degrees(360.0D, 0.0D, 360.0D), 1.0D, 1.0D, -1.0D)", cylinder.toString());
	}
	
	@Test
	public void testWrite() {
		final Cylinder3D a = new Cylinder3D();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Cylinder3D b = new Cylinder3DReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}