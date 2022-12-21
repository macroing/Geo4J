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
package org.macroing.geo4j.shape.sphere;

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
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.bv.bs.BoundingSphere3F;
import org.macroing.geo4j.common.Point2F;
import org.macroing.geo4j.common.Point3F;
import org.macroing.geo4j.common.Vector3F;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.geo4j.onb.OrthonormalBasis33F;
import org.macroing.geo4j.ray.Ray3F;
import org.macroing.geo4j.shape.SurfaceIntersection3F;
import org.macroing.geo4j.shape.SurfaceSample3F;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Sphere3FUnitTests {
	public Sphere3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Sphere3F sphere = new Sphere3F();
		
		assertTrue(sphere.accept(new NodeHierarchicalVisitorMock(node -> false,               node -> node.equals(sphere))));
		assertTrue(sphere.accept(new NodeHierarchicalVisitorMock(node -> node.equals(sphere), node -> node.equals(sphere))));
		
		assertThrows(NodeTraversalException.class, () -> sphere.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> sphere.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Sphere3F sphere = new Sphere3F();
		
		assertThrows(NodeTraversalException.class, () -> sphere.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> sphere.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(16, Sphere3F.ID);
		assertEquals("Sphere", Sphere3F.NAME);
	}
	
	@Test
	public void testContains() {
		final Sphere3F sphere = new Sphere3F();
		
		assertTrue(sphere.contains(new Point3F(0.0F, 0.0F, 0.0F)));
		
		assertFalse(sphere.contains(new Point3F(2.0F, 2.0F, 2.0F)));
		
		assertThrows(NullPointerException.class, () -> sphere.contains(null));
	}
	
	@Test
	public void testEquals() {
		final Sphere3F a = new Sphere3F();
		final Sphere3F b = new Sphere3F();
		final Sphere3F c = null;
		
		assertEquals(a, a);
		
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
	}
	
	@Test
	public void testGetBoundingVolume() {
		final Sphere3F sphere = new Sphere3F();
		
		assertEquals(new BoundingSphere3F(1.0F, new Point3F(0.0F, 0.0F, 0.0F)), sphere.getBoundingVolume());
	}
	
	@Test
	public void testGetID() {
		final Sphere3F sphere = new Sphere3F();
		
		assertEquals(Sphere3F.ID, sphere.getID());
	}
	
	@Test
	public void testGetName() {
		final Sphere3F sphere = new Sphere3F();
		
		assertEquals(Sphere3F.NAME, sphere.getName());
	}
	
	@Test
	public void testGetSurfaceArea() {
		final Sphere3F sphere = new Sphere3F();
		
		assertEquals((float)(Math.PI) * 4.0F, sphere.getSurfaceArea());
	}
	
	@Test
	public void testHashCode() {
		final Sphere3F a = new Sphere3F();
		final Sphere3F b = new Sphere3F();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersectionRay3FFloatFloat() {
		final Sphere3F sphere = new Sphere3F();
		
//		Test intersection A:
		final Optional<SurfaceIntersection3F> optionalSurfaceIntersectionA = sphere.intersection(new Ray3F(new Point3F(0.0F, 0.0F, +2.0F), new Vector3F(0.0F, 0.0F, -1.0F)), 0.0F, 3.0F);
		
		assertNotNull(optionalSurfaceIntersectionA);
		
		assertTrue(optionalSurfaceIntersectionA.isPresent());
		
		final SurfaceIntersection3F surfaceIntersectionA = optionalSurfaceIntersectionA.get();
		
		assertEquals(new OrthonormalBasis33F(new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F)), surfaceIntersectionA.getOrthonormalBasisG());
		assertEquals(new OrthonormalBasis33F(new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F)), surfaceIntersectionA.getOrthonormalBasisS());
		assertEquals(new Ray3F(new Point3F(0.0F, 0.0F, 2.0F), new Vector3F(0.0F, 0.0F, -1.0F)), surfaceIntersectionA.getRay());
		assertEquals(sphere, surfaceIntersectionA.getShape());
		assertEquals(new Point3F(0.0F, 0.0F, 1.0F), surfaceIntersectionA.getSurfaceIntersectionPoint());
		assertEquals(new Vector3F(0.0F, 0.0F, 1.0F), surfaceIntersectionA.getSurfaceNormalG());
		assertEquals(new Vector3F(0.0F, 0.0F, 1.0F), surfaceIntersectionA.getSurfaceNormalS());
		assertEquals(1.0F, surfaceIntersectionA.getT());
		assertEquals(new Point2F(0.0F, 0.0F), surfaceIntersectionA.getTextureCoordinates());
		
//		Test intersection B:
		final Optional<SurfaceIntersection3F> optionalSurfaceIntersectionB = sphere.intersection(new Ray3F(new Point3F(0.0F, 0.0F, -2.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 3.0F);
		
		assertNotNull(optionalSurfaceIntersectionB);
		
		assertTrue(optionalSurfaceIntersectionB.isPresent());
		
		final SurfaceIntersection3F surfaceIntersectionB = optionalSurfaceIntersectionB.get();
		
		assertEquals(new OrthonormalBasis33F(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(-1.0F, 0.0F, 0.0F)), surfaceIntersectionB.getOrthonormalBasisG());
		assertEquals(new OrthonormalBasis33F(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(-1.0F, 0.0F, 0.0F)), surfaceIntersectionB.getOrthonormalBasisS());
		assertEquals(new Ray3F(new Point3F(0.0F, 0.0F, -2.0F), new Vector3F(0.0F, 0.0F, 1.0F)), surfaceIntersectionB.getRay());
		assertEquals(sphere, surfaceIntersectionB.getShape());
		assertEquals(new Point3F(0.0F, 0.0F, -1.0F), surfaceIntersectionB.getSurfaceIntersectionPoint());
		assertEquals(new Vector3F(0.0F, 0.0F, -1.0F), surfaceIntersectionB.getSurfaceNormalG());
		assertEquals(new Vector3F(0.0F, 0.0F, -1.0F), surfaceIntersectionB.getSurfaceNormalS());
		assertEquals(1.0F, surfaceIntersectionB.getT());
		assertEquals(new Point2F(0.0F, 1.0F), surfaceIntersectionB.getTextureCoordinates());
		
//		Test intersection C:
		final Optional<SurfaceIntersection3F> optionalSurfaceIntersectionC = sphere.intersection(new Ray3F(new Point3F(-1.0F, -1.0F, -1.0F), Vector3F.directionNormalized(new Point3F(-1.0F, -1.0F, -1.0F), new Point3F())), 0.0F, 3.0F);
		
		assertNotNull(optionalSurfaceIntersectionC);
		
		assertTrue(optionalSurfaceIntersectionC.isPresent());
		
		final SurfaceIntersection3F surfaceIntersectionC = optionalSurfaceIntersectionC.get();
		
		assertEquals(new OrthonormalBasis33F(new Vector3F(-0.57735026F, -0.57735026F, -0.57735026F), new Vector3F(0.70710671F, -0.70710671F, 0.0F), new Vector3F(0.40824828F, 0.40824828F, -0.81649655F)), surfaceIntersectionC.getOrthonormalBasisG());
		assertEquals(new OrthonormalBasis33F(new Vector3F(-0.57735026F, -0.57735026F, -0.57735026F), new Vector3F(0.70710671F, -0.70710671F, 0.0F), new Vector3F(0.40824828F, 0.40824828F, -0.81649655F)), surfaceIntersectionC.getOrthonormalBasisS());
		assertEquals(new Ray3F(new Point3F(-1.0F, -1.0F, -1.0F), Vector3F.directionNormalized(new Point3F(-1.0F, -1.0F, -1.0F), new Point3F())), surfaceIntersectionC.getRay());
		assertEquals(sphere, surfaceIntersectionC.getShape());
		assertEquals(new Point3F(-0.5773502691896258F, -0.5773502691896258F, -0.5773502691896258F), surfaceIntersectionC.getSurfaceIntersectionPoint());
		assertEquals(new Vector3F(-0.5773502691896258F, -0.5773502691896258F, -0.5773502691896258F), surfaceIntersectionC.getSurfaceNormalG());
		assertEquals(new Vector3F(-0.5773502691896258F, -0.5773502691896258F, -0.5773502691896258F), surfaceIntersectionC.getSurfaceNormalS());
		assertEquals(Vector3F.direction(new Point3F(-1.0F, -1.0F, -1.0F), new Point3F(-0.5773502691896258F, -0.5773502691896258F, -0.5773502691896258F)).length() - 0.00000004F, surfaceIntersectionC.getT());
		assertEquals(new Point2F(0.625F, 0.6959132F), surfaceIntersectionC.getTextureCoordinates());
		
//		Test no intersection:
		assertFalse(sphere.intersection(new Ray3F(new Point3F(1.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F)), 0.0F, 1.0F).isPresent());
		
		assertThrows(NullPointerException.class, () -> sphere.intersection(null, 0.0F, 0.0F));
	}
	
//	@Test
//	public void testIntersectionSurfaceIntersector3F() {
//		
//	}
	
	@Test
	public void testIntersectionT() {
		final Sphere3F sphere = new Sphere3F();
		
		assertEquals(Float.NaN, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, +2.0F), new Vector3F(0.0F, 0.0F, -1.0F)), 0.0F, 1.0F));
		assertEquals(Float.NaN, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, +2.0F), new Vector3F(0.0F, 0.0F, +1.0F)), 0.0F, 1.0F));
		assertEquals(Float.NaN, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, -2.0F), new Vector3F(0.0F, 0.0F, -1.0F)), 0.0F, 1.0F));
		assertEquals(Float.NaN, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, -2.0F), new Vector3F(0.0F, 0.0F, +1.0F)), 0.0F, 1.0F));
		
		assertEquals(Float.NaN, sphere.intersectionT(new Ray3F(new Point3F(2.0F, 0.0F, 0.0F), new Vector3F(0.0F, 0.0F, +1.0F)), 0.0F, 2.0F));
		
		assertEquals(1.0F, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, +0.0F), new Vector3F(0.0F, 0.0F, +1.0F)), 0.0F, 2.0F));
		assertEquals(1.0F, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, +0.0F), new Vector3F(0.0F, 0.0F, -1.0F)), 0.0F, 2.0F));
		assertEquals(1.0F, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, +2.0F), new Vector3F(0.0F, 0.0F, -1.0F)), 0.0F, 2.0F));
		assertEquals(1.0F, sphere.intersectionT(new Ray3F(new Point3F(0.0F, 0.0F, -2.0F), new Vector3F(0.0F, 0.0F, +1.0F)), 0.0F, 2.0F));
		
		assertThrows(NullPointerException.class, () -> sphere.intersectionT(null, 0.0F, 0.0F));
	}
	
//	@Test
//	public void testIntersects() {
//		
//	}
	
	@Test
	public void testSamplePoint2F() {
		final Sphere3F sphere = new Sphere3F();
		
		for(int i = 0; i < 1000; i++) {
			final Point2F sample = Point2F.sampleRandom();
			
			final Optional<SurfaceSample3F> optionalSurfaceSample = sphere.sample(sample);
			
			assertTrue(optionalSurfaceSample.isPresent());
			
			final SurfaceSample3F surfaceSample = optionalSurfaceSample.get();
			
			final Point3F point = surfaceSample.getPoint();
			
			final Vector3F surfaceNormal = surfaceSample.getSurfaceNormal();
			
			final float probabilityDensityFunctionValue = surfaceSample.getProbabilityDensityFunctionValue();
			
			assertNotNull(point);
			assertNotNull(surfaceNormal);
			
			assertTrue(Point3F.distanceSquared(new Point3F(), point) <= 1.0000002F);
			assertTrue(surfaceNormal.isUnitVector());
			
			assertEquals(1.0F / ((float)(Math.PI) * 4.0F), probabilityDensityFunctionValue);
		}
		
		assertThrows(NullPointerException.class, () -> sphere.sample(null));
	}
	
	@Test
	public void testToString() {
		final Sphere3F sphere = new Sphere3F();
		
		assertEquals("new Sphere3F()", sphere.toString());
	}
	
	@Test
	public void testWrite() {
		final Sphere3F a = new Sphere3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Sphere3F b = new Sphere3FReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}