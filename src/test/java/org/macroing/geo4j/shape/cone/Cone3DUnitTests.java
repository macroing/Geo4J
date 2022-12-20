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
package org.macroing.geo4j.shape.cone;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Cone3DUnitTests {
	public Cone3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Cone3D cone = new Cone3D();
		
		assertTrue(cone.accept(new NodeHierarchicalVisitorMock(node -> false,             node -> node.equals(cone))));
		assertTrue(cone.accept(new NodeHierarchicalVisitorMock(node -> node.equals(cone), node -> node.equals(cone))));
		
		assertThrows(NodeTraversalException.class, () -> cone.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> cone.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Cone3D cone = new Cone3D();
		
		assertThrows(NodeTraversalException.class, () -> cone.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> cone.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(2, Cone3D.ID);
		assertEquals("Cone", Cone3D.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Cone3D cone = new Cone3D();
		
		assertEquals(AngleD.degrees(360.0D), cone.getPhiMax());
		assertEquals(1.0D, cone.getRadius());
		assertEquals(1.0D, cone.getZMax());
	}
	
	@Test
	public void testConstructorAngleD() {
		final Cone3D cone = new Cone3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), cone.getPhiMax());
		assertEquals(1.0D, cone.getRadius());
		assertEquals(1.0D, cone.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Cone3D(null));
	}
	
	@Test
	public void testConstructorAngleDDouble() {
		final Cone3D cone = new Cone3D(AngleD.degrees(180.0D), 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), cone.getPhiMax());
		assertEquals(2.0D, cone.getRadius());
		assertEquals(1.0D, cone.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Cone3D(null, 2.0D));
	}
	
	@Test
	public void testConstructorAngleDDoubleDouble() {
		final Cone3D cone = new Cone3D(AngleD.degrees(180.0D), 2.0D, 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), cone.getPhiMax());
		assertEquals(2.0D, cone.getRadius());
		assertEquals(2.0D, cone.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Cone3D(null, 2.0D, 2.0D));
	}
	
	@Test
	public void testGetID() {
		final Cone3D cone = new Cone3D();
		
		assertEquals(Cone3D.ID, cone.getID());
	}
	
	@Test
	public void testGetName() {
		final Cone3D cone = new Cone3D();
		
		assertEquals(Cone3D.NAME, cone.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Cone3D cone = new Cone3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), cone.getPhiMax());
	}
	
	@Test
	public void testGetRadius() {
		final Cone3D cone = new Cone3D(AngleD.degrees(360.0D), 2.0D);
		
		assertEquals(2.0D, cone.getRadius());
	}
	
	@Test
	public void testGetZMax() {
		final Cone3D cone = new Cone3D(AngleD.degrees(360.0D), 1.0D, 2.0D);
		
		assertEquals(2.0D, cone.getZMax());
	}
	
	@Test
	public void testToString() {
		final Cone3D cone = new Cone3D(AngleD.degrees(360.0D), 1.0D, 1.0D);
		
		assertEquals("new Cone3D(AngleD.degrees(360.0D, 0.0D, 360.0D), 1.0D, 1.0D)", cone.toString());
	}
	
	@Test
	public void testWrite() {
		final Cone3D a = new Cone3D();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Cone3D b = new Cone3DReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}