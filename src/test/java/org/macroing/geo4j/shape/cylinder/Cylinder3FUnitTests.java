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
package org.macroing.geo4j.shape.cylinder;

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

import org.macroing.geo4j.common.AngleF;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.java.lang.Floats;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Cylinder3FUnitTests {
	public Cylinder3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Cylinder3F cylinder = new Cylinder3F();
		
		assertTrue(cylinder.accept(new NodeHierarchicalVisitorMock(node -> false,                 node -> node.equals(cylinder))));
		assertTrue(cylinder.accept(new NodeHierarchicalVisitorMock(node -> node.equals(cylinder), node -> node.equals(cylinder))));
		
		assertThrows(NodeTraversalException.class, () -> cylinder.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> cylinder.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Cylinder3F cylinder = new Cylinder3F();
		
		assertThrows(NodeTraversalException.class, () -> cylinder.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> cylinder.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(6, Cylinder3F.ID);
		assertEquals("Cylinder", Cylinder3F.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Cylinder3F cylinder = new Cylinder3F();
		
		assertEquals(AngleF.degrees(360.0F), cylinder.getPhiMax());
		assertEquals(+1.0F, cylinder.getRadius());
		assertEquals(+1.0F, cylinder.getZMax());
		assertEquals(-1.0F, cylinder.getZMin());
	}
	
	@Test
	public void testConstructorAngleF() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), cylinder.getPhiMax());
		assertEquals(+1.0F, cylinder.getRadius());
		assertEquals(+1.0F, cylinder.getZMax());
		assertEquals(-1.0F, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3F(null));
	}
	
	@Test
	public void testConstructorAngleFFloat() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(180.0F), 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), cylinder.getPhiMax());
		assertEquals(+2.0F, cylinder.getRadius());
		assertEquals(+1.0F, cylinder.getZMax());
		assertEquals(-1.0F, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3F(null, 2.0F));
	}
	
	@Test
	public void testConstructorAngleFFloatFloat() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(180.0F), 2.0F, 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), cylinder.getPhiMax());
		assertEquals(+2.0F, cylinder.getRadius());
		assertEquals(+2.0F, cylinder.getZMax());
		assertEquals(-1.0F, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3F(null, 2.0F, 2.0F));
	}
	
	@Test
	public void testConstructorAngleFFloatFloatFloat() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(180.0F), 2.0F, 2.0F, 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), cylinder.getPhiMax());
		assertEquals(+2.0F, cylinder.getRadius());
		assertEquals(+2.0F, cylinder.getZMax());
		assertEquals(+2.0F, cylinder.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Cylinder3F(null, 2.0F, 2.0F, 2.0F));
	}
	
	@Test
	public void testGetID() {
		final Cylinder3F cylinder = new Cylinder3F();
		
		assertEquals(Cylinder3F.ID, cylinder.getID());
	}
	
	@Test
	public void testGetName() {
		final Cylinder3F cylinder = new Cylinder3F();
		
		assertEquals(Cylinder3F.NAME, cylinder.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), cylinder.getPhiMax());
	}
	
	@Test
	public void testGetRadius() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(360.0F), 2.0F);
		
		assertEquals(2.0F, cylinder.getRadius());
	}
	
	@Test
	public void testGetSurfaceArea() {
		final Cylinder3F cylinder = new Cylinder3F();
		
		assertEquals(Floats.PI_MULTIPLIED_BY_4, cylinder.getSurfaceArea());
	}
	
	@Test
	public void testGetZMax() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(360.0F), 1.0F, 2.0F);
		
		assertEquals(2.0F, cylinder.getZMax());
	}
	
	@Test
	public void testGetZMin() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(360.0F), 1.0F, 1.0F, 2.0F);
		
		assertEquals(2.0F, cylinder.getZMin());
	}
	
	@Test
	public void testToString() {
		final Cylinder3F cylinder = new Cylinder3F(AngleF.degrees(360.0F), 1.0F, 1.0F, -1.0F);
		
		assertEquals("new Cylinder3F(AngleF.degrees(360.0F, 0.0F, 360.0F), 1.0F, 1.0F, -1.0F)", cylinder.toString());
	}
	
	@Test
	public void testWrite() {
		final Cylinder3F a = new Cylinder3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Cylinder3F b = new Cylinder3FReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}