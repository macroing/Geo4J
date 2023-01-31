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
package org.macroing.geo4j.shape.disk;

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

import org.macroing.geo4j.common.AngleF;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Disk3FUnitTests {
	public Disk3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Disk3F disk = new Disk3F();
		
		assertTrue(disk.accept(new NodeHierarchicalVisitorMock(node -> false,             node -> node.equals(disk))));
		assertTrue(disk.accept(new NodeHierarchicalVisitorMock(node -> node.equals(disk), node -> node.equals(disk))));
		
		assertThrows(NodeTraversalException.class, () -> disk.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> disk.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Disk3F disk = new Disk3F();
		
		assertThrows(NodeTraversalException.class, () -> disk.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> disk.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(7, Disk3F.ID);
		assertEquals("Disk", Disk3F.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Disk3F disk = new Disk3F();
		
		assertEquals(AngleF.degrees(360.0F), disk.getPhiMax());
		assertEquals(0.0F, disk.getRadiusInner());
		assertEquals(1.0F, disk.getRadiusOuter());
		assertEquals(0.0F, disk.getZMax());
	}
	
	@Test
	public void testConstructorAngleF() {
		final Disk3F disk = new Disk3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), disk.getPhiMax());
		assertEquals(0.0F, disk.getRadiusInner());
		assertEquals(1.0F, disk.getRadiusOuter());
		assertEquals(0.0F, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3F(null));
	}
	
	@Test
	public void testConstructorAngleFFloat() {
		final Disk3F disk = new Disk3F(AngleF.degrees(180.0F), 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), disk.getPhiMax());
		assertEquals(2.0F, disk.getRadiusInner());
		assertEquals(1.0F, disk.getRadiusOuter());
		assertEquals(0.0F, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3F(null, 2.0F));
	}
	
	@Test
	public void testConstructorAngleFFloatFloat() {
		final Disk3F disk = new Disk3F(AngleF.degrees(180.0F), 2.0F, 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), disk.getPhiMax());
		assertEquals(2.0F, disk.getRadiusInner());
		assertEquals(2.0F, disk.getRadiusOuter());
		assertEquals(0.0F, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3F(null, 2.0F, 2.0F));
	}
	
	@Test
	public void testConstructorAngleFFloatFloatFloat() {
		final Disk3F disk = new Disk3F(AngleF.degrees(180.0F), 2.0F, 2.0F, 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), disk.getPhiMax());
		assertEquals(2.0F, disk.getRadiusInner());
		assertEquals(2.0F, disk.getRadiusOuter());
		assertEquals(2.0F, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3F(null, 2.0F, 2.0F, 2.0F));
	}
	
	@Test
	public void testEquals() {
		final Disk3F a = new Disk3F(AngleF.degrees(360.0F), 0.0F, 1.0F, 0.0F);
		final Disk3F b = new Disk3F(AngleF.degrees(360.0F), 0.0F, 1.0F, 0.0F);
		final Disk3F c = new Disk3F(AngleF.degrees(360.0F), 0.0F, 1.0F, 2.0F);
		final Disk3F d = new Disk3F(AngleF.degrees(360.0F), 0.0F, 2.0F, 0.0F);
		final Disk3F e = new Disk3F(AngleF.degrees(360.0F), 2.0F, 1.0F, 0.0F);
		final Disk3F f = new Disk3F(AngleF.degrees(180.0F), 0.0F, 1.0F, 0.0F);
		final Disk3F g = null;
		
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
		final Disk3F disk = new Disk3F();
		
		assertEquals(Disk3F.ID, disk.getID());
	}
	
	@Test
	public void testGetName() {
		final Disk3F disk = new Disk3F();
		
		assertEquals(Disk3F.NAME, disk.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Disk3F disk = new Disk3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), disk.getPhiMax());
	}
	
	@Test
	public void testGetRadiusInner() {
		final Disk3F disk = new Disk3F(AngleF.degrees(360.0F), 2.0F);
		
		assertEquals(2.0F, disk.getRadiusInner());
	}
	
	@Test
	public void testGetRadiusOuter() {
		final Disk3F disk = new Disk3F(AngleF.degrees(360.0F), 0.0F, 2.0F);
		
		assertEquals(2.0F, disk.getRadiusOuter());
	}
	
	@Test
	public void testGetZMax() {
		final Disk3F disk = new Disk3F(AngleF.degrees(360.0F), 0.0F, 1.0F, 2.0F);
		
		assertEquals(2.0F, disk.getZMax());
	}
	
	@Test
	public void testHashCode() {
		final Disk3F a = new Disk3F();
		final Disk3F b = new Disk3F();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final Disk3F disk = new Disk3F(AngleF.degrees(360.0F), 1.0F, 1.0F, 1.0F);
		
		assertEquals("new Disk3F(AngleF.degrees(360.0F, 0.0F, 360.0F), 1.0F, 1.0F, 1.0F)", disk.toString());
	}
	
	@Test
	public void testWrite() {
		final Disk3F a = new Disk3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Disk3F b = new Disk3FReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}