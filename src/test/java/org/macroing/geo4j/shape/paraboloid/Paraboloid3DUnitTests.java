/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
package org.macroing.geo4j.shape.paraboloid;

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
public final class Paraboloid3DUnitTests {
	public Paraboloid3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Paraboloid3D paraboloid = new Paraboloid3D();
		
		assertTrue(paraboloid.accept(new NodeHierarchicalVisitorMock(node -> false,                   node -> node.equals(paraboloid))));
		assertTrue(paraboloid.accept(new NodeHierarchicalVisitorMock(node -> node.equals(paraboloid), node -> node.equals(paraboloid))));
		
		assertThrows(NodeTraversalException.class, () -> paraboloid.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> paraboloid.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Paraboloid3D paraboloid = new Paraboloid3D();
		
		assertThrows(NodeTraversalException.class, () -> paraboloid.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> paraboloid.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(10, Paraboloid3D.ID);
		assertEquals("Paraboloid", Paraboloid3D.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Paraboloid3D paraboloid = new Paraboloid3D();
		
		assertEquals(AngleD.degrees(360.0D), paraboloid.getPhiMax());
		assertEquals(1.0D, paraboloid.getRadius());
		assertEquals(1.0D, paraboloid.getZMax());
		assertEquals(0.0D, paraboloid.getZMin());
	}
	
	@Test
	public void testConstructorAngleD() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), paraboloid.getPhiMax());
		assertEquals(1.0D, paraboloid.getRadius());
		assertEquals(1.0D, paraboloid.getZMax());
		assertEquals(0.0D, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3D(null));
	}
	
	@Test
	public void testConstructorAngleDDouble() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(180.0D), 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), paraboloid.getPhiMax());
		assertEquals(2.0D, paraboloid.getRadius());
		assertEquals(1.0D, paraboloid.getZMax());
		assertEquals(0.0D, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3D(null, 2.0D));
	}
	
	@Test
	public void testConstructorAngleDDoubleDouble() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(180.0D), 2.0D, 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), paraboloid.getPhiMax());
		assertEquals(2.0D, paraboloid.getRadius());
		assertEquals(2.0D, paraboloid.getZMax());
		assertEquals(0.0D, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3D(null, 2.0D, 2.0D));
	}
	
	@Test
	public void testConstructorAngleDDoubleDoubleDouble() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(180.0D), 2.0D, 2.0D, 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), paraboloid.getPhiMax());
		assertEquals(2.0D, paraboloid.getRadius());
		assertEquals(2.0D, paraboloid.getZMax());
		assertEquals(2.0D, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3D(null, 2.0D, 2.0D, 2.0D));
	}
	
	@Test
	public void testGetID() {
		final Paraboloid3D paraboloid = new Paraboloid3D();
		
		assertEquals(Paraboloid3D.ID, paraboloid.getID());
	}
	
	@Test
	public void testGetName() {
		final Paraboloid3D paraboloid = new Paraboloid3D();
		
		assertEquals(Paraboloid3D.NAME, paraboloid.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), paraboloid.getPhiMax());
	}
	
	@Test
	public void testGetRadius() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(360.0D), 2.0D);
		
		assertEquals(2.0D, paraboloid.getRadius());
	}
	
	@Test
	public void testGetZMax() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(360.0D), 1.0D, 2.0D);
		
		assertEquals(2.0D, paraboloid.getZMax());
	}
	
	@Test
	public void testGetZMin() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(360.0D), 1.0D, 1.0D, 2.0D);
		
		assertEquals(2.0D, paraboloid.getZMin());
	}
	
	@Test
	public void testToString() {
		final Paraboloid3D paraboloid = new Paraboloid3D(AngleD.degrees(360.0D), 1.0D, 1.0D, -1.0D);
		
		assertEquals("new Paraboloid3D(AngleD.degrees(360.0D, 0.0D, 360.0D), 1.0D, 1.0D, -1.0D)", paraboloid.toString());
	}
	
	@Test
	public void testWrite() {
		final Paraboloid3D a = new Paraboloid3D();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Paraboloid3D b = new Paraboloid3DReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}