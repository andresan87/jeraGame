﻿package br.com.jera.graphic;

public class Math {

	public static class Vector4 {
		public Vector4() {
			x = y = z = w = 0;
		}

		public Vector4(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
		}

		public Vector4(Vector4 v) {
			x = v.x;
			y = v.y;
			z = v.z;
			w = v.w;
		}

		public float x, y, z, w;
	}

	public static class Vector3 {
		public Vector3() {
			x = y = z = 0;
		}

		public Vector3(Vector3 v) {
			x = v.x;
			y = v.y;
			z = v.z;
		}

		public Vector3(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Vector3(Vector2 v, float z) {
			this.x = v.x;
			this.y = v.y;
			this.z = z;
		}

		public float x, y, z;
	}

	public static class Vector2 {
		public Vector2() {
			x = y = 0;
		}

		public Vector2(Vector2 v) {
			x = v.x;
			y = v.y;
		}

		public Vector2(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public Vector2 add(Vector2 v) {
			return new Vector2(x+v.x, y+v.y);
		}

		public Vector2 sub(Vector2 v) {
			return new Vector2(x-v.x, y-v.y);
		}

		public Vector2 multiply(float v) {
			return new Vector2(x*v, y*v);
		}

		public Vector2 multiply(Vector2 v) {
			return new Vector2(x*v.x, y*v.y);
		}

		public float x, y;
	}

	public enum PRIMITIVE_TYPE {
		TRIANGLE_LIST, TRIANGLE_STRIP, TRIANGLE_FAN
		// TODO lines, points...
	}

	public static int computeNumTriangles(PRIMITIVE_TYPE type, int vertexCount) {
		switch(type) {
		case TRIANGLE_LIST:
			return vertexCount/3;
		case TRIANGLE_STRIP:
		case TRIANGLE_FAN:
		default:
			return vertexCount-2;
		// TODO lines, points...
		}
	}
	
	public static Vector2 computeSpriteOriginOffset(Vector2 bitmapSize, Vector2 normalizedOrigin) {
		return new Vector2(-bitmapSize.x*normalizedOrigin.x, -bitmapSize.y*normalizedOrigin.y);
	}

	public static class Vertex {
		public Vertex(Vector3 pos, Vector4 color) {
			this.pos = pos;
			this.color = color;
		}

		public Vertex(Vector3 pos, Vector2 texCoord, Vector4 color) {
			this.pos = pos;
			this.texCoord = texCoord;
			this.color = color;
		}

		public Vertex(Vector3 pos, Vector2 texCoord) {
			this.pos = pos;
			this.texCoord = texCoord;
		}

		public Vector3 pos;
		public Vector4 color;
		public Vector2 texCoord;
	}

	public static class GridCutter {
		
		public Rectangle2D[] rects;
		
		public GridCutter(int columns, int rows) {
			rects = new Rectangle2D[rows*columns];
			
			float fRows = (float)rows;
			float fColumns = (float)columns;
			
			int index = 0;
			for (int y=0; y<rows; y++) {
				for (int x=0; x<columns; x++) {
					rects[index] = new Rectangle2D();
					rects[index].pos.x = 1/fColumns * (float)x;
					rects[index].pos.y = 1/fRows * (float)y;
					rects[index].size.x = 1/fColumns;
					rects[index].size.y = 1/fRows;

					index++;
				}
			}
		}
	}

	public static class Rectangle2D {
		
		public Vector2 pos = new Vector2();
		public Vector2 size = new Vector2();
	}
}